package functionDrawers;

import functions.Function;
import lineDrawers.LineDrawer;
import utils.RealPoint;
import utils.ScreenConverter;
import utils.ScreenPoint;

import java.awt.*;

public class FunctionDrawer implements FunctionDrawable {
    private double step = 0.001;

    private Color color = Color.RED;

    public FunctionDrawer(double step, Color color) {
        this.step = step;
        this.color = color;
    }

    public FunctionDrawer() {
    }

    @Override
    public void drawFunction(Function function, ScreenConverter screenConverter, LineDrawer lineDrawer) {
        for (double x1 = screenConverter.getCornerX(); x1 < screenConverter.getRealWidth() + screenConverter.getCornerX(); x1 += step) {
            double x2 = x1 + step;
            double y1 = function.compute(x1);
            double y2 = function.compute(x2);
            if (isOutOfBounds(screenConverter, y1)) continue; //optimization
            ScreenPoint point1 = screenConverter.realToScreen(new RealPoint(x1, y1));
            ScreenPoint point2 = screenConverter.realToScreen(new RealPoint(x2, y2));
            lineDrawer.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), function.getColor());
        }
    }

    private boolean isOutOfBounds(ScreenConverter screenConverter, double y) {
        return screenConverter.getCornerY() - Math.abs(y) < Double.MIN_VALUE &&
                Math.abs(screenConverter.getCornerY()) + screenConverter.getRealHeight() - Math.abs(y) < Double.MIN_VALUE;
    }

    @Override
    public void drawFunction(Function function, ScreenConverter screenConverter, LineDrawer lineDrawer, double step, Color color) {
        this.step = step;
        this.color = color;
        drawFunction(function, screenConverter, lineDrawer);
    }


    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
