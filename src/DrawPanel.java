import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {

    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);
    private ScreenConverter screenConverter = new ScreenConverter(-2, 2, 4, 4, getWidth(), getHeight());

    public DrawPanel() {}

    @Override
    public void paint(Graphics g) {
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics buffGraphics = bufferedImage.createGraphics();
        buffGraphics.setColor(Color.WHITE);
        buffGraphics.fillRect(0, 0, getWidth(), getHeight());
        buffGraphics.dispose();
        PixelDrawer pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        LineDrawer lineDrawer = new DDALineDrawer(pixelDrawer);
        lineDrawer.drawLine(screenConverter.realToScreen(xAxis.getPoint1()), screenConverter.realToScreen(xAxis.getPoint2()));
        lineDrawer.drawLine(screenConverter.realToScreen(yAxis.getPoint1()), screenConverter.realToScreen(yAxis.getPoint2()));


        g.drawImage(bufferedImage, 0, 0, null);
    }
}
