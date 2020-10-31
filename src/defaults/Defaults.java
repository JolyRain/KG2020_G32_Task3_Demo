package defaults;

import functions.*;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Defaults {

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 800;
    public static final double REAL_SIZE = 10;
    public static final double CORNER_X = -REAL_SIZE / 2;
    public static final double CORNER_Y = REAL_SIZE / 2;
    public static final double COEFFICIENT_INCREASE = 1.05;
    public static final double COEFFICIENT_DECREASE = 0.95;
    public static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    public static final Font FONT_SIGNATURES = new Font(Font.DIALOG, Font.BOLD, 15);
    public static final Color MAIN_GRID_COLOR = new Color(150, 150, 150);
    public static final Color SMALL_GRID_COLOR = new Color(200, 200, 200);
    public static final Color LIGHT_GRAY = new Color(220, 220, 220);
    public static final Color VIOLET = new Color(100, 10, 255);
    public static final Color BLUE = new Color(0, 100, 255);
    public static final double LOWER_LIMIT_NUMBER = 0.001;
    public static final double UPPER_LIMIT_NUMBER = 10000;
    public static final int SMALL_GRID_DIVIDER = 5;
    public static final Map<String, Function> FUNCTIONS_MAP = new HashMap<>();
    public static final List<Double> COEFFICIENTS = new LinkedList<>();

    static {
        FUNCTIONS_MAP.put("y = e^sin(x*3)", new Function1());
        FUNCTIONS_MAP.put("y = x^3 - x^2", new Function2());
        FUNCTIONS_MAP.put("y = x^(1/3) * sin(x)", new Function3());
        FUNCTIONS_MAP.put("y = ln(x^2 + 1) / (x^2 + 2)", new Function4());
        FUNCTIONS_MAP.put("y = (1 / (x^2 + 1))", new Function5());
        FUNCTIONS_MAP.put("y = abs(x^4 - x^3 + x^2 - x)", new Function6());
        FUNCTIONS_MAP.put("y = x^4 - abs(x^3)", new Function7());
        FUNCTIONS_MAP.put("y=e^((sin(x)+cos(x))/(x^2+1))", new Function8());
        COEFFICIENTS.add(2.0);
        COEFFICIENTS.add(2.0);
        COEFFICIENTS.add(2.5);
    }

}
