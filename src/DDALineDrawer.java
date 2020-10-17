import java.awt.*;

public class DDALineDrawer implements LineDrawer {

    private PixelDrawer pixelDrawer;

    public DDALineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(ScreenPoint point1, ScreenPoint point2) {
        int x1 = point1.getX();
        int y1 = point1.getY();
        int x2 = point2.getX();
        int y2 = point2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            double k = dy / dx;
            if (x1 > x2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            for (int j = x1; j < x2; j++) {
                double i = k * (j - x1) + y1;
                pixelDrawer.drawPixel(j, (int) i, Color.RED);
            }
        } else {
            double kObr = dx / dy;
            if (y1 > y2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;
                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            for (int i = y1; i <= y2; i++) {
                double j = kObr * (i - y1) + x1;
                pixelDrawer.drawPixel((int) j, i, Color.RED);
            }
        }
    }
}

