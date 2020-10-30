package functions;

import java.awt.*;

public class Function2 extends Function {
    public Function2() {
        super(new Color(200, 100, 255));
    }

    @Override
    public double compute(double x) {
        return (Math.pow(x, 3) - Math.pow(x, 2));
    }
}
