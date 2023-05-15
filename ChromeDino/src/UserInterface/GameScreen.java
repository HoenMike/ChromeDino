package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameObject.Clouds;
import GameObject.Dino;
import GameObject.EnemyManager;
import GameObject.Ground;
import Handler.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener, MouseListener {
    // Game states
    public static final int GAME_STARTING_STATE = 0;
    public static final int GAME_PLAYING_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    // Constants
    public static final float GRAVITY = 0.1f;
    public static final float GROUND = 110;

    // Game objects
    private Dino dino;
    private Ground ground;
    private Clouds cloud;
    private EnemyManager enemyManager;

    // Scores
    private int score;

    // Game state
    private int gameState = GAME_STARTING_STATE;

    // Game images
    private BufferedImage imageGameOverText;
    private BufferedImage imageReplayButton;

    // Thread for the game loop
    private Thread thread;

    // Constructor
    public GameScreen() {
        thread = new Thread(this);
        dino = new Dino();
        dino.setX(50);
        ground = new Ground(this);
        cloud = new Clouds();
        enemyManager = new EnemyManager(dino, this);
        imageGameOverText = Resource.getResourceImage("ChromeDino/data/gameOverText.png");
        imageReplayButton = Resource.getResourceImage("ChromeDino/data/replayButton.png");
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

    public void plusScore(int score) {
        this.score += score;
    }

    public void setScore(int score) {
        this.score = score;
    };

    private void drawScore(Graphics g) {
        g.drawString("HighScore " + String.valueOf(score), 480, 20);
    }

    private void drawGamePlayingState(Graphics g) {
        cloud.draw(g);
        ground.draw(g);
        dino.draw(g);
        enemyManager.draw(g);
        drawScore(g);
    }

    private void drawGameOverState(Graphics g) {
        cloud.draw(g);
        ground.draw(g);
        dino.draw(g);
        enemyManager.draw(g);
        drawGameOverText(g);
        drawReplayButton(g);
    }

    private void drawGameOverText(Graphics g) {
        int centerX = getWidth() / 2;
        int gameOverTextX = centerX - imageGameOverText.getWidth() / 2;
        int gameOverTextY = getHeight() / 2 - imageGameOverText.getHeight() / 2 - 20;
        g.drawImage(imageGameOverText, gameOverTextX, gameOverTextY, null);
    }

    private void drawReplayButton(Graphics g) {
        int centerX = getWidth() / 2;
        int replayButtonX = centerX - imageReplayButton.getWidth() / 2;
        int replayButtonY = getHeight() / 2 + imageGameOverText.getHeight() / 2 + 10;
        g.drawImage(imageReplayButton, replayButtonX, replayButtonY, null);
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
                drawGamePlayingState(g);
                break;
            case GAME_OVER_STATE:
                drawGameOverState(g);
                break;
        }
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
                    setGameState(GAME_PLAYING_STATE);
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameState == GAME_OVER_STATE) {
            int centerX = getWidth() / 2;
            int replayButtonX = centerX - imageReplayButton.getWidth() / 2;
            int replayButtonY = getHeight() / 2 + imageGameOverText.getHeight() / 2 + 10;
            int replayButtonWidth = imageReplayButton.getWidth();
            int replayButtonHeight = imageReplayButton.getHeight();
            if (e.getX() >= replayButtonX && e.getX() <= replayButtonX + replayButtonWidth
                    && e.getY() >= replayButtonY && e.getY() <= replayButtonY + replayButtonHeight) {
                resetGame();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void resetGame() {
        dino.setAlive(true);
        dino.setX(50);
        dino.setY(67);
        setScore(0);
        setGameState(GAME_STARTING_STATE);
        enemyManager.reset();
    }

    private void setGameState(int gameState) {
        this.gameState = gameState;
    }

    // getter & setter
    public static float getGravity() {
        return GRAVITY;
    }

    public static float getGround() {
        return GROUND;
    }

}
