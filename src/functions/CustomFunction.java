package functions;

import java.awt.*;

public class CustomFunction extends Function {

    private String formula = "";

    public CustomFunction(String formula) {
        super(Color.RED);
        this.formula = formula;
    }

    public CustomFunction() {
        super(Color.RED);
    }

    private double interpret() {
        return 0;
    }

    @Override
    public double compute(double x) {
        return 0;
    }

    public double form2(double x) {
        return (Math.pow(x, 3) - Math.pow(x, 2));
    }

    public double form1(double x) {
        return Math.exp(Math.sin(3 * x));
    }

    public double form4(double x) {
        return (Math.log1p(Math.pow(x, 2))) / ((Math.pow(x, 2)) + 2);
    }

    public double form5(double x) {
        return 1.0 / (Math.pow(x, 2) + 1);
    }

    public double form6(double x) {
        return Math.abs(Math.pow(x, 4) - Math.pow(x, 3) + Math.pow(x, 2) - x);
    }

    public double form7(double x) {
        return Math.pow(x, 4) - Math.abs(Math.pow(x, 3));
    }

    public double form8(double x) {
        return Math.exp((Math.sin(x) + Math.cos(x)) / (Math.pow(x, 2) + 1));
    }

    public double form3(double x) {  //не работает
        return Math.pow(x, (1.0 / 3.0) * Math.sin(x));
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
