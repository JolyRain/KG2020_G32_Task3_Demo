import javax.swing.*;

public class MainWindow extends JFrame {

    private DrawPanel drawPanel;

    public MainWindow() {
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        drawPanel = new DrawPanel();
        this.add(drawPanel);
    }
}
