package functionDrawers;

import functions.Function;
import lineDrawers.LineDrawer;
import pixelDrawers.PixelDrawer;
import utils.ScreenConverter;

import java.awt.*;

public interface FunctionDrawable {

    void drawFunction(Function function, ScreenConverter screenConverter, LineDrawer lineDrawer);

    void drawFunction(Function function, ScreenConverter screenConverter, LineDrawer lineDrawer, double step, Color color);

}
