package app;

import functionDrawers.FunctionDrawable;
import functionDrawers.FunctionDrawer;
import functions.Function;
import lineDrawers.BresenhamLineDrawer;
import lineDrawers.LineDrawer;
import pixelDrawers.BufferedImagePixelDrawer;
import pixelDrawers.PixelDrawer;
import utils.Line;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;
import world.CoordinateSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ScreenConverter screenConverter = new ScreenConverter(
            -5, 5, 10, 10, getWidth(), getHeight());
    private ScreenPoint lastPosition = null;
    private List<Line> allLines = new LinkedList<>();
    private Line currentNewLine = null;
    private PixelDrawer pixelDrawer = null;
    private LineDrawer lineDrawer = null;
    private CoordinateSystem coordinateSystem = new CoordinateSystem(screenConverter);


    private List<Function> functions = new LinkedList<>();
    private FunctionDrawer funcDrawer = new FunctionDrawer();

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    public void addFunction(Function function) {
        functions.add(function);
        repaint();
    }

    public void removeFunction(Function function) {
        functions.removeIf(currentFunction -> currentFunction.equals(function));
        repaint();
    }

    private void drawFunctions(FunctionDrawable functionDrawer, LineDrawer lineDrawer) {
        for (Function function : functions) {
            functionDrawer.drawFunction(function, screenConverter, lineDrawer);
        }
    }

    private void debug(ScreenConverter screenConverter) {
        System.out.println(screenConverter);
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


    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenWidth(getWidth());
        screenConverter.setScreenHeight(getHeight());
        coordinateSystem.setScreenConverter(screenConverter);
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        lineDrawer = new BresenhamLineDrawer(pixelDrawer);
        graphics2D.setColor(new Color(220, 220, 220));
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        coordinateSystem.draw(lineDrawer, graphics2D);
//        for (Line line : allLines) {
//            drawLine(lineDrawer, line);
//        }
//        if (currentNewLine != null) drawLine(lineDrawer, currentNewLine);

        drawFunctions(funcDrawer, lineDrawer);
        g.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
    }


    public void setStep(double step) {
        funcDrawer.setStep(step);
        repaint();
    }

    private void drawLine(LineDrawer lineDrawer, Line line) {
        lineDrawer.drawLine(
                screenConverter.realToScreen(line.getP1()),
                screenConverter.realToScreen(line.getP2()),
                line.getColor());
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        double scale = 1;
        if (e.getButton() == MouseEvent.BUTTON1) scale  = 1.1;
        else if (e.getButton() == MouseEvent.BUTTON3) scale = 0.9;
        screenConverter.scale(scale);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) lastPosition = new ScreenPoint(e.getX(), e.getY());
//        else if (e.getButton() == MouseEvent.BUTTON1) {
//            currentNewLine = new Line(
//                    screenConverter.screenToReal(new ScreenPoint(e.getX(), e.getY())),
//                    screenConverter.screenToReal(new ScreenPoint(e.getX(), e.getY())),
//                    Color.BLACK);
//        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) lastPosition = null;
//        else if (e.getButton() == MouseEvent.BUTTON1) {
//            allLines.add(currentNewLine);
//            currentNewLine = null;
//        }
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
        if (currentNewLine != null) {
            currentNewLine.setP2(screenConverter.screenToReal(currentPosition));
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        String s = "Формула масштабирования относительно произвольной точки M(x0,y0,z0):\n" +
                "x' = x0+(x-x0)*Sx;\n" +
                "y' = y0+(y-y0)*Sy;\n" +
                "z' = z0+(z-z0)*Sz;";
        int clicks = e.getWheelRotation();
        double zoom = 1;

        RealPoint center = new RealPoint(0, 0);
        double coefficient = clicks < 0 ? 1.05 : 0.95;
        for (int i = 0; i < Math.abs(clicks); i++) {
            zoom *= coefficient;
        }
//        ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
//        ScreenPoint deltaScreen = new ScreenPoint(
//                currentPosition.getX() - lastPosition.getX(),
//                currentPosition.getY() - lastPosition.getY());
//        RealPoint deltaReal = screenConverter.screenToReal(deltaScreen);
//        RealPoint zeroReal = screenConverter.screenToReal(new ScreenPoint(0, 0));
//        RealPoint vector = new RealPoint(
//                deltaReal.getX() - zeroReal.getX(),
//                deltaReal.getY() - zeroReal.getY());
//        screenConverter.setCornerX(screenConverter.getCornerX() - vector.getX());
//        screenConverter.setCornerY(screenConverter.getCornerY() - vector.getY());
//        lastPosition = currentPosition;
        screenConverter.scale(zoom);
        repaint();
    }

    //брать реальную ширину, делить на условные нужные нам 10 линий, получили к примеру 0.7 и округляем до ближашего кратного 5
    //


}


