package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import GameObject.Clouds;
import GameObject.Dino;
import GameObject.EnemyManager;
import GameObject.Ground;

public class GameScreen extends JPanel implements Runnable, KeyListener {

    public static final int GAME_STARTING_STATE = 0;
    public static final int GAME_PLAYING_STATE = 1;
    public static final int GAME_OVER_STATE = 2;
    public static final float GRAVITY = 0.1f;
    public static final float GROUND = 110;

    private Dino dino;
    private Ground ground;
    private Clouds cloud;
    private EnemyManager enemyManager;

    private int gameState = GAME_STARTING_STATE;

    private Thread thread;

    // constructor
    public GameScreen() {
        thread = new Thread(this);
        dino = new Dino();
        dino.setX(50);
        ground = new Ground(this);
        cloud = new Clouds();
        enemyManager = new EnemyManager();
    }

    public void startGame() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                update();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        cloud.update();
        ground.update();
        dino.update();
        enemyManager.update();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        switch (gameState) {
            case GAME_STARTING_STATE:
                dino.draw(g);
                break;
            case GAME_PLAYING_STATE:
                cloud.draw(g);
                ground.draw(g);
                dino.draw(g);
                enemyManager.draw(g);
                break;
            case GAME_OVER_STATE:
        }
        // cloud.draw(g);
        // ground.draw(g);
        // dino.draw(g);
        // enemyManager.draw(g);
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (gameState == GAME_STARTING_STATE) {
                    gameState = GAME_PLAYING_STATE;
                }
                break;
        }
    }

    // getter & setter
    public static float getGravity() {
        return GRAVITY;
    }

    public static float getGround() {
        return GROUND;
    }

}
