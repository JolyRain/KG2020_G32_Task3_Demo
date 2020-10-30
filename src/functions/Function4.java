package functions;

import java.awt.*;

public class Function4 extends Function {
    public Function4() {
        super(new Color(0,0,100));
    }

    @Override
    public double compute(double x) {
        return (Math.log1p(Math.pow(x, 2))) / ((Math.pow(x, 2)) + 2);

    }
}
