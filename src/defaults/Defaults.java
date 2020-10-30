package defaults;

import functions.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Defaults {

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 800;
    public static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    public static final Color LIGHT_GRAY = new Color(220, 220, 220);
    public static final Color VIOLET = new Color(100, 10, 255);
    public static final Color BLUE = new Color(0, 100, 255);


    public static final Map<String, Function> FUNCTIONS_MAP = new HashMap<>();

    static {
        FUNCTIONS_MAP.put("y = e^sin(x*3)", new Function1());
        FUNCTIONS_MAP.put("y = x^3 - x^2", new Function2());
        FUNCTIONS_MAP.put("y = x^(1/3) * sin(x)", new Function3());
        FUNCTIONS_MAP.put("y = ln(x^2 + 1) / (x^2 + 2)", new Function4());
        FUNCTIONS_MAP.put("y = (1 / (x^2 + 1))", new Function5());
        FUNCTIONS_MAP.put("y = abs(x^4 - x^3 + x^2 - x)", new Function6());
        FUNCTIONS_MAP.put("y = x^4 - abs(x^3)", new Function7());
        FUNCTIONS_MAP.put("y=e^((sin(x)+cos(x))/(x^2+1))", new Function8());
    }

}
