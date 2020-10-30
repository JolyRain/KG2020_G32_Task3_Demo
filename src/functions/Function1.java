package functions;

import java.awt.*;

public class Function1 extends Function {

    public Function1() {
        super(Color.RED);
    }

    @Override
    public double compute(double x) {
        return Math.exp(Math.sin(3 * x));
    }

}
