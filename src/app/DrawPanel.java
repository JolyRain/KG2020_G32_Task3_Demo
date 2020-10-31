package app;

import functionDrawers.FunctionDrawable;
import functionDrawers.FunctionDrawer;
import functions.Function;
import lineDrawers.BresenhamLineDrawer;
import lineDrawers.LineDrawer;
import lineDrawers.WuLineDrawer;
import pixelDrawers.BufferedImagePixelDrawer;
import pixelDrawers.PixelDrawer;
import utils.Line;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;
import world.CoordinateSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import static defaults.Defaults.*;


public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ScreenConverter screenConverter = new ScreenConverter(
            CORNER_X, CORNER_Y, REAL_SIZE, REAL_SIZE, getWidth(), getHeight());
    private ScreenPoint lastPosition = null;
    private PixelDrawer pixelDrawer = null;
    private LineDrawer lineDrawer = null;
    private CoordinateSystem coordinateSystem = new CoordinateSystem(screenConverter);


    private List<Function> functions = new LinkedList<>();
    private FunctionDrawer funcDrawer = new FunctionDrawer();

    DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    void addFunction(Function function) {
        functions.add(function);
        repaint();
    }

    void removeFunction(Function function) {
        functions.removeIf(currentFunction -> currentFunction.equals(function));
        repaint();
    }

    private void drawFunctions(FunctionDrawable functionDrawer, LineDrawer lineDrawer) {
        for (Function function : functions) {
            functionDrawer.drawFunction(function, screenConverter, lineDrawer);
        }
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenWidth(getWidth());
        screenConverter.setScreenHeight(getHeight());
        coordinateSystem.setScreenConverter(screenConverter);
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        lineDrawer = new BresenhamLineDrawer(pixelDrawer);
        graphics2D.setColor(LIGHT_GRAY);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        coordinateSystem.draw(lineDrawer, graphics2D);
        drawFunctions(funcDrawer, lineDrawer);
        g.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPosition = new ScreenPoint(e.getX(), e.getY());
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPosition = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
        if (lastPosition != null) {
            ScreenPoint deltaScreen = new ScreenPoint(
                    currentPosition.getX() - lastPosition.getX(),
                    currentPosition.getY() - lastPosition.getY());
            RealPoint deltaReal = screenConverter.screenToReal(deltaScreen);
            RealPoint zeroReal = screenConverter.screenToReal(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY());
            screenConverter.setCornerX(screenConverter.getCornerX() - vector.getX());
            screenConverter.setCornerY(screenConverter.getCornerY() - vector.getY());
            lastPosition = currentPosition;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double zoom = 1;
        double coefficient = clicks < 0 ? COEFFICIENT_INCREASE : COEFFICIENT_DECREASE;
        for (int i = 0; i < Math.abs(clicks); i++) {
            zoom *= coefficient;
        }
        screenConverter.scale(zoom);
        repaint();
    }

    private double round(double num) {
        String stringNumber = String.valueOf(num);
        double multipleOf = 1;
        double result = num;

        if (!stringNumber.endsWith("5")) {
            StringBuilder multiply = new StringBuilder("0.");
            for (int i = stringNumber.indexOf(".") + 1; i < stringNumber.length() - 1; i++) {
                multiply.append("0");
            }
            multiply.append("5");
            multipleOf = Double.parseDouble(multiply.toString());
            result = Math.round(num / multipleOf) * multipleOf;
        }
        System.out.println(num + " round to " + multipleOf + ": " + result);
        return result;
    }
}


