package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable, KeyListener {

    // for testing
    private float x = 0;
    private float y = 0;
    private float speedY = 0;

    private Thread thread;

    public GameScreen() {
        thread = new Thread(this);
    }

    public void startGame() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                y += 1;
                y += speedY;
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect((int) x, (int) y, 60, 120);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released");
    }
}
