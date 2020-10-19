import lineDrawers.BresenhamLineDrawer;
import lineDrawers.DDALineDrawer;
import lineDrawers.LineDrawer;
import lineDrawers.WuLineDrawer;
import pixelDrawers.BufferedImagePixelDrawer;
import pixelDrawers.PixelDrawer;
import utils.Line;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;


public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private ScreenConverter screenConverter = new ScreenConverter(
            -2, 2, 4, 4, getWidth(), getHeight());
    private ScreenPoint lastPosition = null;
    private List<Line> allLines = new LinkedList<>();
    private Line currentNewLine = null;
    private PixelDrawer pixelDrawer = null;
    private LineDrawer lineDrawer = null;

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);

    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenWidth(getWidth());
        screenConverter.setScreenHeight(getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        lineDrawer = new WuLineDrawer(pixelDrawer);
//                lineDrawer = new BresenhamLineDrawer(pixelDrawer);
//                lineDrawer = new WuLineDrawer(pixelDrawer);
        Graphics buffGraphics = bufferedImage.createGraphics();
        buffGraphics.setColor(Color.LIGHT_GRAY);
        buffGraphics.fillRect(0, 0, getWidth(), getHeight());
        drawSomething(lineDrawer);
//        drawSomething(lineDrawer);
        drawAxis(lineDrawer);

        for (Line line : allLines) {
            drawLine(lineDrawer, line);
        }
        if (currentNewLine != null) drawLine(lineDrawer, currentNewLine);

        g.drawImage(bufferedImage, 0, 0, null);
        buffGraphics.dispose();

    }

    private void drawLine(LineDrawer lineDrawer, Line line) {
        lineDrawer.drawLine(
                screenConverter.realToScreen(line.getP1()),
                screenConverter.realToScreen(line.getP2()),
                Color.BLACK);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) lastPosition = new ScreenPoint(e.getX(), e.getY());
        else if (e.getButton() == MouseEvent.BUTTON1) {
            currentNewLine = new Line(
                    screenConverter.screenToReal(new ScreenPoint(e.getX(), e.getY())),
                    screenConverter.screenToReal(new ScreenPoint(e.getX(), e.getY())),
                    Color.BLACK);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) lastPosition = null;
        else if (e.getButton() == MouseEvent.BUTTON1) {
            allLines.add(currentNewLine);
            currentNewLine = null;
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
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 1.1 : 0.9;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        screenConverter.setRealWidth(screenConverter.getRealWidth() * scale);
        screenConverter.setRealHeight(screenConverter.getRealHeight() * scale);
        repaint();
    }

    private double form(double x) {
        return (Math.pow(x, 3) - Math.pow(x, 2));
    }

    private double form1(double x) {
        return Math.exp(Math.sin(3 * x));
    }


    private double form4(double x) {
        return (Math.log1p(Math.pow(x, 2))) / ((Math.pow(x, 2)) + 2);
    }


    private double form5(double x) {
        return 1.0 / (Math.pow(x, 2) + 1);
    }


    private double form6(double x) {
        return Math.abs(Math.pow(x, 4) - Math.pow(x, 3) + Math.pow(x, 2) - x);
    }

    private double form7(double x) {
        return Math.pow(x, 4) - Math.abs(Math.pow(x, 3));
    }

    private double form8(double x) {
    return Math.exp((Math.sin(x) + Math.cos(x)) / (Math.pow(x, 2) + 1));
    }



    private final double STEP = 0.01;

    private double form3(double x) {  //не работает
        return Math.pow(x, (1.0 / 3.0) * Math.sin(x)) ;
    }

    private void drawSomething(LineDrawer lineDrawer) {
//        graphics.setColor(Color.RED);
        for (double x = -getWidth(); x < 1; x += STEP) {
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(x, form3(x)));
            ScreenPoint point2 = screenConverter.realToScreen(new RealPoint(x + STEP, form3(x + STEP)));

            lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(),point2.getY(), Color.RED);
        }

    }

    private void drawAxis(LineDrawer lineDrawer) {
        lineDrawer.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), Color.BLACK);
        lineDrawer.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, Color.BLACK);
    }

}


