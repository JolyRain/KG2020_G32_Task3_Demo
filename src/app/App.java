package app;

import defaults.Defaults;
import functions.Function;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static defaults.Defaults.*;

public class App {
    private JFrame frame;
    private DrawPanel drawPanel;
    private JPanel leftPanel;

    public App() {
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Functions");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setFocusable(true);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        drawPanel = new DrawPanel();
        createLeftPanel();
        leftPanel.setBounds(0, 0, frame.getWidth() / 5, frame.getHeight());
        drawPanel.setLayout(null);
        drawPanel.setBounds(frame.getWidth() / 5, 0, frame.getWidth() -  frame.getWidth() / 5, frame.getHeight());
        frame.add(drawPanel);
        frame.add(leftPanel);
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setFont(FONT_LABEL);
        for (Map.Entry<String, Function> entry : FUNCTIONS_MAP.entrySet()) {
            JCheckBox function = new JCheckBox(entry.getKey());
            function.setFont(FONT_LABEL);
            function.addActionListener(e -> {
                if (function.isSelected()) {
                    drawPanel.addFunction(entry.getValue());
                } else drawPanel.removeFunction(entry.getValue());
            });
            leftPanel.add(function);
        }
    }



}
