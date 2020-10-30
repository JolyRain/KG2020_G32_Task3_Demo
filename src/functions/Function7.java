package functions;

import defaults.Defaults;

import java.awt.*;

public class Function7 extends Function {
    public Function7() {
        super(Defaults.BLUE);
    }

    @Override
    public double compute(double x) {
        return Math.pow(x, 4) - Math.abs(Math.pow(x, 3));
    }
}
