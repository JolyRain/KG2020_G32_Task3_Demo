package functions;

import java.awt.*;

public class Function6 extends Function {
    public Function6() {
        super(new Color(20, 100, 0));
    }

    @Override
    public double compute(double x) {
        return Math.abs(Math.pow(x, 4) - Math.pow(x, 3) + Math.pow(x, 2) - x);
    }
}
