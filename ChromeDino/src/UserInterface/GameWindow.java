package UserInterface;

import java.awt.Graphics;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow() {
        super("Chrome Dino");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GameWindow().setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
