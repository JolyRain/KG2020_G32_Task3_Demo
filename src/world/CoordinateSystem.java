package world;

import lineDrawers.LineDrawer;
import utils.Line;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class CoordinateSystem {
    private ScreenConverter screenConverter;
    private Font font;
    private double realWidth, realHeight, cornerX, cornerY;
    private double stepX = 1;
    private double stepY = 1;

    private double oldRealWidth;
    private double oldRealHeight;
    private List<Double> coefficients = new LinkedList<>();
    public CoordinateSystem(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
        this.cornerX = screenConverter.getCornerX();
        this.cornerY = screenConverter.getCornerY();
        this.realWidth = screenConverter.getRealWidth();
        this.realHeight = screenConverter.getRealHeight();
        oldRealHeight = realHeight;
        oldRealWidth = realWidth;
        coefficients.add(2.0);
        coefficients.add(2.0);
        coefficients.add(2.5);

    }

    public void draw(LineDrawer lineDrawer, Graphics graphics) {
        font = new Font(Font.DIALOG, Font.BOLD, 15);
        recount();
        drawSmallGrid(lineDrawer);
        drawGrid(lineDrawer);
        drawAxis(lineDrawer);
        drawSignatures((Graphics2D) graphics);
    }

    private int index = 0;

    private void recount() {
        if (realWidth == oldRealWidth) return;
        if (realWidth > oldRealWidth) {
            if ((((int) (realWidth / oldRealWidth))) % 2 == 0) {
                oldRealWidth = realWidth;
                oldRealHeight = realHeight;
                stepX *= coefficients.get(index);
                stepY *= coefficients.get(index);
                System.out.println(">>>>>>>");
                System.out.println(index);
                index++;
                if (index == 3) index = 0;
            }
        } else {
            if ((((int) (oldRealWidth / realWidth))) % 2 == 0) {
                oldRealWidth = realWidth;
                oldRealHeight = realHeight;
                stepX /= coefficients.get(index);
                stepY /= coefficients.get(index);
                System.out.println("<<<<<<<<");
                System.out.println(index);
                index++;
                if (index == 3) index = 0;
            }
        }
        //        System.out.println(screenConverter);
    }

    private void drawGrid(LineDrawer lineDrawer, double stepX, double stepY, Color color) {
        for (double x = 0; x <= realWidth + Math.abs(cornerX); x += stepX) {
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(x, cornerY));
            ScreenPoint point2 = screenConverter.realToScreen(new RealPoint(x, -realHeight + cornerY));
            ScreenPoint mirrorPoint1 = screenConverter.realToScreen(new RealPoint(-x, cornerY));
            ScreenPoint mirrorPoint2 = screenConverter.realToScreen(new RealPoint(-x, -realHeight + cornerY));
            lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), color);
            lineDrawer.drawLine(mirrorPoint1.getX(), mirrorPoint1.getY(), mirrorPoint2.getX(), mirrorPoint2.getY(), color);
        }
        for (double y = 0; y <= realHeight + Math.abs(cornerY); y += stepY) {
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(cornerX, y));
            ScreenPoint point2 = screenConverter.realToScreen(new RealPoint(cornerX + realWidth, y));
            ScreenPoint mirrorPoint1 = screenConverter.realToScreen(new RealPoint(cornerX, -y));
            ScreenPoint mirrorPoint2 = screenConverter.realToScreen(new RealPoint(cornerX + realWidth, -y));
            lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), color);
            lineDrawer.drawLine(mirrorPoint1.getX(), mirrorPoint1.getY(), mirrorPoint2.getX(), mirrorPoint2.getY(), color);
        }
    }

    private void drawSmallGrid(LineDrawer lineDrawer) {
        drawGrid(lineDrawer, stepX / 2, stepY / 2, new Color(200, 200, 200));
    }

    private void drawGrid(LineDrawer lineDrawer) {
        drawGrid(lineDrawer, stepX, stepY, new Color(150, 150, 150));
    }

    private void drawSignatures(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);
        drawZero(graphics2D);
        for (double x = stepX; x <= realWidth + Math.abs(cornerX); x += stepX) {
            ScreenPoint point = screenConverter.realToScreen(new RealPoint(x, 0));
            ScreenPoint mirrorPoint = screenConverter.realToScreen(new RealPoint(-x, 0));
            graphics2D.drawString(getSignature(x), point.getX(), point.getY());
            graphics2D.drawString(getSignature(-x), mirrorPoint.getX(), mirrorPoint.getY());
        }
        for (double y = stepY; y <= realHeight + Math.abs(cornerY); y += stepY) {
            ScreenPoint point = screenConverter.realToScreen(new RealPoint(0, y));
            ScreenPoint mirrorPoint = screenConverter.realToScreen(new RealPoint(0, -y));
            graphics2D.drawString(getSignature(y), point.getX(), point.getY());
            graphics2D.drawString(getSignature(-y), mirrorPoint.getX(), mirrorPoint.getY());
        }
    }
    private void drawZero(Graphics2D graphics2D) {
        ScreenPoint point = screenConverter.realToScreen(new RealPoint(0, 0));
        graphics2D.drawString(String.valueOf(0), point.getX(), point.getY());

    }

    private void drawAxis(LineDrawer lineDrawer) {
        Line xAxis = new Line(
                screenConverter.getCornerX(), 0,
                screenConverter.getRealWidth() + screenConverter.getCornerX(), 0,
                Color.RED);
        Line yAxis = new Line(
                0, -screenConverter.getRealHeight() + screenConverter.getCornerY(),
                0, screenConverter.getRealHeight() + screenConverter.getCornerY(),
                Color.RED);
        lineDrawer.drawLine(screenConverter.realToScreen(xAxis.getP1()), screenConverter.realToScreen(xAxis.getP2()), xAxis.getColor());
        lineDrawer.drawLine(screenConverter.realToScreen(yAxis.getP1()), screenConverter.realToScreen(yAxis.getP2()), yAxis.getColor());

    }


    private String getSignature(double signature) {
        DecimalFormat decimalFormat = new DecimalFormat();
        return decimalFormat.format(signature);
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
        this.cornerX = screenConverter.getCornerX();
        this.cornerY = screenConverter.getCornerY();
        this.realWidth = screenConverter.getRealWidth();
        this.realHeight = screenConverter.getRealHeight();
    }
}
