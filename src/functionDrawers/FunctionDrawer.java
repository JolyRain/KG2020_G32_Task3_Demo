package functionDrawers;

import functions.Function;
import lineDrawers.LineDrawer;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;

import java.awt.*;

public class FunctionDrawer implements FunctionDrawable {

    private Color color = Color.RED;

    public FunctionDrawer() {
    }

    @Override
    public void drawFunction(Function function, ScreenConverter screenConverter, LineDrawer lineDrawer) {
        double step = screenConverter.getRealWidth() / screenConverter.getScreenWidth();
        for (double x1 = screenConverter.getCornerX(); x1 < screenConverter.getRealWidth() + screenConverter.getCornerX(); x1 += step) {
            RealPoint realPoint1 = new RealPoint(x1, function.compute(x1));
            RealPoint realPoint2 = new RealPoint(x1 + step, function.compute(x1 + step));
            ScreenPoint point1, point2;

            if (!isOutOfBounds(screenConverter, realPoint1, realPoint2)) {
                if (isBorderCrossing(screenConverter, realPoint1, realPoint2)) {
                    point1 = getBorderCrossingPoint(screenConverter, realPoint1, realPoint2);
                    point2 = screenConverter.realToScreen(realPoint2);
                } else if (isBorderCrossing(screenConverter, realPoint2, realPoint1)) {
                    point1 = screenConverter.realToScreen(realPoint1);
                    point2 = getBorderCrossingPoint(screenConverter, realPoint2, realPoint1);
                } else {
                    point1 = screenConverter.realToScreen(realPoint1);
                    point2 = screenConverter.realToScreen(realPoint2);
                }
                lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), function.getColor());
            }
        }
    }

    private ScreenPoint getBorderCrossingPoint(ScreenConverter screenConverter, RealPoint outsidePoint, RealPoint insidePoint) {
        double x1 = insidePoint.getX();
        double y1 = insidePoint.getY();
        double x2 = outsidePoint.getX();
        double y2 = outsidePoint.getY();
        double y = insidePoint.getY() < outsidePoint.getY() ?
                screenConverter.getCornerY() :
                screenConverter.getCornerY() - screenConverter.getRealHeight();
        double x = (((y - y1) * (x2 - x1)) / (y2 - y1)) + x1;

        RealPoint borderRealPoint = new RealPoint(x, y);

        return screenConverter.realToScreen(borderRealPoint);
    }

    private boolean isBorderCrossing(ScreenConverter screenConverter, RealPoint outsidePoint, RealPoint insidePoint) {
        return (Math.abs(outsidePoint.getY()) > Math.abs(screenConverter.getCornerY()) + screenConverter.getRealHeight()) &&
                Math.abs(insidePoint.getY()) < Math.abs(screenConverter.getCornerY())  + screenConverter.getRealHeight();
    }

    private boolean isOutOfBounds(ScreenConverter screenConverter, RealPoint outsidePoint, RealPoint insidePoint) {
        return (Math.abs(outsidePoint.getY()) > Math.abs(screenConverter.getCornerY()) + (screenConverter.getRealHeight()) &&
                Math.abs(insidePoint.getY()) > Math.abs(screenConverter.getCornerY()) + (screenConverter.getRealHeight()));
    }

    @Override
    public void drawFunction(Function function, ScreenConverter screenConverter, LineDrawer lineDrawer, Color color) {
        this.color = color;
        drawFunction(function, screenConverter, lineDrawer);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
