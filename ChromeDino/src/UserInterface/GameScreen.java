package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameObject.Clouds;
import GameObject.Dino;
import GameObject.EnemyManager;
import GameObject.Ground;
import Handler.Resource;

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
    private int score;

    private int gameState = GAME_STARTING_STATE;

    private BufferedImage imageGameOverText;

    private Thread thread;

    // constructor
    public GameScreen() {
        thread = new Thread(this);
        dino = new Dino();
        dino.setX(50);
        ground = new Ground(this);
        cloud = new Clouds();
        enemyManager = new EnemyManager(dino);
        imageGameOverText = Resource.getResourceImage("data/gameOverText.png");
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
        switch (gameState) {
            case GAME_PLAYING_STATE:
                cloud.update();
                ground.update();
                dino.update();
                enemyManager.update();
                if (!dino.getAlive()) {
                    gameState = GAME_OVER_STATE;
                }
        }

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
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
                g.drawString(String.valueOf(score), 300, 20);
                break;
            case GAME_OVER_STATE:
                cloud.draw(g);
                ground.draw(g);
                dino.draw(g);
                enemyManager.draw(g);
                g.drawImage(imageGameOverText, 100, 50, null);
                break;
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
        if (gameState == GAME_PLAYING_STATE) {
            dino.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (gameState == GAME_STARTING_STATE) {
                    gameState = GAME_PLAYING_STATE;
                } else if (gameState == GAME_OVER_STATE) {
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
