package Airport;

import javax.swing.*;
import java.awt.*;
import java.lang.module.FindException;

public class Interface extends JFrame
{
    final int WIDTH = 1200, HEIGHT = 700;
    Interface() {
        setTitle("Airport");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        MainPanel mainPanel = new MainPanel(getWidth(), getHeight());
        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Interface();
    }
}
