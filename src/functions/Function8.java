package functions;

import defaults.Defaults;


public class Function8 extends Function {
    public Function8() {
        super(Defaults.VIOLET);
    }

    @Override
    public double compute(double x) {
        return Math.exp((Math.sin(x) + Math.cos(x)) / (Math.pow(x, 2) + 1));
    }
}
