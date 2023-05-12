package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import GameObject.Dino;
import GameObject.Ground;
import GameObject.Clouds;

public class GameScreen extends JPanel implements Runnable, KeyListener {

    public static final float GRAVITY = 0.1f;
    public static final float GROUND = 110;

    private Dino dino;
    private Ground ground;
    private Clouds cloud;

    private Thread thread;

    // constructor
    public GameScreen() {
        thread = new Thread(this);
        dino = new Dino();
        dino.setX(50);
        ground = new Ground(this);
        cloud = new Clouds();
    }

    public void startGame() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                cloud.update();
                ground.update();
                dino.update();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        // g.drawLine(0, (int) GROUND, getWidth(), (int) GROUND);
        cloud.draw(g);
        ground.draw(g);
        dino.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        dino.jump();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released");
    }

    // getter & setter
    public static float getGravity() {
        return GRAVITY;
    }

    public static float getGround() {
        return GROUND;
    }

}
