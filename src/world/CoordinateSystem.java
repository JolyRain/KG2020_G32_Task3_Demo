package world;

import lineDrawers.LineDrawer;
import utils.Line;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;

import java.awt.*;
import java.text.DecimalFormat;

import static defaults.Defaults.*;

public class CoordinateSystem {
    private ScreenConverter screenConverter;
    private double realWidth, realHeight, cornerX, cornerY;
    private double stepX = 1;
    private double stepY = 1;

    private double oldRealWidth;
    private double oldRealHeight;

    public CoordinateSystem(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
        this.cornerX = screenConverter.getCornerX();
        this.cornerY = screenConverter.getCornerY();
        this.realWidth = screenConverter.getRealWidth();
        this.realHeight = screenConverter.getRealHeight();
        oldRealHeight = realHeight;
        oldRealWidth = realWidth;
    }

    public void draw(LineDrawer lineDrawer, Graphics graphics) {
        recountStep();
        drawSmallGrid(lineDrawer);
        drawMainGrid(lineDrawer);
        drawAxis(lineDrawer);
        drawSignatures((Graphics2D) graphics);
    }


    private int index = 1;
    boolean saveCoefficient = false;

    private void recountStep() {
        if (realWidth == oldRealWidth) return;
        if (realWidth > oldRealWidth) {
            if ((((int) (realWidth / oldRealWidth))) == 2) {
                oldRealWidth = realWidth;
                oldRealHeight = realHeight;
                stepX *= COEFFICIENTS.get(index);
                stepY *= COEFFICIENTS.get(index);
                index++;
            }
        } else {
            if ((((int) (oldRealWidth / realWidth))) == 2) {
                oldRealWidth = realWidth;
                oldRealHeight = realHeight;
                stepX /= COEFFICIENTS.get(index);
                stepY /= COEFFICIENTS.get(index);
                index++;
            }
        }
        if (index == COEFFICIENTS.size()) index = 0;
    }

    private void recountStep(double coefficient) {

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
        drawGrid(lineDrawer, stepX / SMALL_GRID_DIVIDER, stepY / SMALL_GRID_DIVIDER, SMALL_GRID_COLOR);
    }

    private void drawMainGrid(LineDrawer lineDrawer) {
        drawGrid(lineDrawer, stepX, stepY, MAIN_GRID_COLOR);
    }

    private void drawSignatures(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(FONT_SIGNATURES);
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
                Color.BLACK);
        Line yAxis = new Line(
                0, -screenConverter.getRealHeight() + screenConverter.getCornerY(),
                0, screenConverter.getRealHeight() + screenConverter.getCornerY(),
                Color.BLACK);
        lineDrawer.drawLine(
                screenConverter.realToScreen(xAxis.getP1()),
                screenConverter.realToScreen(xAxis.getP2()),
                xAxis.getColor());
        lineDrawer.drawLine(
                screenConverter.realToScreen(yAxis.getP1()),
                screenConverter.realToScreen(yAxis.getP2()),
                yAxis.getColor());

    }


    private String getSignature(double signature) {
        DecimalFormat decimalFormat = new DecimalFormat();
        if (Math.abs(signature) <= LOWER_LIMIT_NUMBER || Math.abs(signature) >= UPPER_LIMIT_NUMBER) {
            return String.format("%.0e", signature);
        }
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
