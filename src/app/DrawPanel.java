package app;

import functionDrawers.FunctionDrawable;
import functionDrawers.FunctionDrawer;
import functions.Function;
import lineDrawers.LineDrawer;
import lineDrawers.WuLineDrawer;
import pixelDrawers.BufferedImagePixelDrawer;
import pixelDrawers.GraphicsPixelDrawer;
import pixelDrawers.PixelDrawer;
import utils.Line;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;

import javax.swing.*;
import java.awt.*;
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


    private void drawGrid(LineDrawer lineDrawer) {

        double stepX = screenConverter.getRealWidth() / 10;
        double stepY = screenConverter.getRealHeight() / 10;

        for (double x = -screenConverter.getRealWidth(); x <= screenConverter.getRealWidth(); x += stepX) {
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(x, screenConverter.getRealHeight()));
            ScreenPoint point2 = screenConverter.realToScreen(new RealPoint(x, -screenConverter.getRealHeight()));
            lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), Color.GRAY);
        }
        for (double y = -screenConverter.getRealHeight(); y <= screenConverter.getRealHeight(); y += stepY) {
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(-screenConverter.getRealWidth(), y));
            ScreenPoint point2 = screenConverter.realToScreen(new RealPoint(screenConverter.getRealWidth(), y));
            lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), Color.GRAY);
        }
    }

    private void drawSignatures(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        double stepX = screenConverter.getRealWidth() / 10;
        double stepY = screenConverter.getRealHeight() / 10;

        graphics2D.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        for (double x = -screenConverter.getRealWidth(); x <= screenConverter.getRealWidth(); x += stepX) {
            if (x == 0) continue;
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(x, 0));
            graphics2D.drawString(getSignature(x), point1.getX(), point1.getY());
        }
        for (double y = -screenConverter.getRealHeight(); y <= screenConverter.getRealHeight(); y += stepY) {
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(0, y));
            graphics2D.drawString(getSignature(y), point1.getX(), point1.getY());
        }
    }

    private String getSignature(double signature) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
//        return Math.abs(Math.abs(signature) - Math.abs((int) signature)) <= 0.1 ? String.valueOf((int) signature) : decimalFormat.format(signature);
        return decimalFormat.format(signature);
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenWidth(getWidth());
        screenConverter.setScreenHeight(getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        lineDrawer = new WuLineDrawer(pixelDrawer);
        graphics2D.setColor(new Color(220, 220, 220));
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        drawGrid(lineDrawer);
        drawSignatures(graphics2D);
        drawAxis(lineDrawer);
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
                Color.BLACK);

    }

    private void drawAxis(LineDrawer lineDrawer) {
        Line xAxis = new Line(-screenConverter.getRealWidth(), 0, screenConverter.getRealWidth(), 0, Color.BLACK);
        Line yAxis = new Line(0, -screenConverter.getRealHeight(), 0, screenConverter.getRealHeight(), Color.BLACK);
        drawLine(lineDrawer, xAxis);
        drawLine(lineDrawer, yAxis);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
//        "x' = x0+(x-x0)*Sx;";
//        "y' = y0+(y-y0)*Sy;";
//        RealPoint zeroReal = screenConverter.screenToReal(new ScreenPoint(0, 0));
//        ScreenPoint zeroScreen = screenConverter.realToScreen(zeroReal);
//        RealPoint realLocation = screenConverter.screenToReal(new ScreenPoint(e.getLocationOnScreen().x, e.getLocationOnScreen().y));
//        double newX = realLocation.getX() + (zeroReal.getX() - realLocation.getX()) * 2;
//        double newY = realLocation.getY() + (zeroReal.getY() - realLocation.getY()) * 2;

        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 1.05 : 0.95;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        screenConverter.setRealWidth(screenConverter.getRealWidth() * scale);
        screenConverter.setRealHeight(screenConverter.getRealHeight() * scale);
        repaint();
    }

    //брать реальную ширину, делить на условные нужные нам 10 линий, получили к примеру 0.7 и округляем до ближашего кратного 5
    //


}


