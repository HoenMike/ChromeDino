package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import GameObject.Player;

public class GameScreen extends JPanel implements Runnable, KeyListener {

    public static final float GRAVITY = 0.1f;
    public static final float FLOOR = 300f;

    private Player player;

    private Thread thread;

    // constructor
    public GameScreen() {
        thread = new Thread(this);
        player = new Player();
    }

    public void startGame() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                player.update();
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
        g.drawLine(0, (int) FLOOR, getWidth(), (int) FLOOR);
        player.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        player.jump();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released");
    }

    // getter & setter
    public static float getGravity() {
        return GRAVITY;
    }

    public static float getFloor() {
        return FLOOR;
    }

}
