package functions;

import java.awt.*;

public class Function3 extends Function {

    public Function3() {
        super(Color.BLUE);
    }

    @Override
    public double compute(double x) {
        return Math.cbrt(x) * Math.sin(x);
    }
}
