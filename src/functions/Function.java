package functions;

import java.awt.*;

public abstract class Function  {
    private Color color;

    public Color getColor() {
        return color;
    }

    public Function(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

     public abstract double compute(double x);
}
