package functions;

import java.awt.*;

public class Function5 extends Function {
    public Function5() {
        super(Color.MAGENTA);
    }

    @Override
    public double compute(double x) {
        return 1.0 / (Math.pow(x, 2) + 1);
    }
}
