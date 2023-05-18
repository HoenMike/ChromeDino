package UserInterface;

import java.awt.Color;
import java.awt.FontMetrics;
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
    private static final long serialVersionUID = 1L;

    private enum GameState {
        STARTING, PLAYING, GAME_OVER
    }

    private static final float GRAVITY = 0.1f;
    private static final float GROUND = 110;

    private Dino dino;
    private Ground ground;
    private Clouds clouds;
    private EnemyManager enemyManager;

    private int score;
    private int highScore;

    private GameState gameState;
    private boolean isKeyPressed;

    private BufferedImage imageGameOverText;
    private BufferedImage imageReplayButton;

    private Thread thread;

    public GameScreen() {
        initializeObjects();
        loadResources();
    }

    private void initializeObjects() {
        dino = new Dino();
        dino.setX(4);
        ground = new Ground();
        clouds = new Clouds();
        enemyManager = new EnemyManager(dino, this);
        gameState = GameState.STARTING;
    }

    private void loadResources() {
        imageGameOverText = Resource.getResourceImage("data/gameOverText.png");
        imageReplayButton = Resource.getResourceImage("data/replayButton.png");
    }

    public void startGame() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        int fps = 100;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = 0;
        long elapsed;

        int msSleep;
        int nanoSleep;

        long endProcessGame;
        long lag = 0;

        while (true) {
            update();
            repaint();
            endProcessGame = System.nanoTime();
            elapsed = (lastTime + msPerFrame - endProcessGame);
            msSleep = (int) (elapsed / 1000000);
            nanoSleep = (int) (elapsed % 1000000);
            if (msSleep <= lag) {
                lastTime = System.nanoTime();
                continue;
            }
            try {
                Thread.sleep(msSleep, nanoSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastTime = endProcessGame;
        }
    }

    public void update() {
        switch (gameState) {
            case PLAYING:
                clouds.update();
                ground.update();
                dino.update();
                enemyManager.update();
                if (score > highScore) {
                    highScore = score;
                }
                if (!dino.isAlive()) {
                    gameState = GameState.GAME_OVER;
                }
                break;
            default:
                break;
        }
    }

    public void plusScore(int score) {
        this.score += score;
    }

    public void setScore(int score) {
        this.score = score;
    };

    private void drawScore(Graphics g) {
        String highScoreText = "High Score: " + String.valueOf(highScore);
        String currentScoreText = "Score: " + String.valueOf(score);
        FontMetrics metrics = g.getFontMetrics();
        int highScoreTextWidth = metrics.stringWidth(highScoreText);
        int currentScoreTextWidth = metrics.stringWidth(currentScoreText);
        int textX = getWidth() - Math.max(highScoreTextWidth, currentScoreTextWidth) - 20;
        int textY = 20;
        g.drawString(highScoreText, textX, textY);
        g.drawString(currentScoreText, textX, textY + metrics.getHeight());
    }

    private void drawGamePlayingState(Graphics g) {
        clouds.draw(g);
        ground.draw(g);
        dino.draw(g);
        enemyManager.draw(g);
        drawScore(g);
    }

    private void drawGameOverState(Graphics g) {
        clouds.draw(g);
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
        int replayButtonY = getHeight() / 2 + imageGameOverText.getHeight() / 2;
        g.drawImage(imageReplayButton, replayButtonX, replayButtonY, null);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        switch (gameState) {
            case STARTING:
                dino.draw(g);
                break;
            case PLAYING:
                drawGamePlayingState(g);
                break;
            case GAME_OVER:
                drawGameOverState(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO document why this method is empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
                case STARTING:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameState = GameState.PLAYING;
                    }
                    break;
                case PLAYING:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        dino.jump();
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        dino.down(true);
                    }
                    break;
                case GAME_OVER:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameState = GameState.PLAYING;
                        resetGame();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (gameState == GameState.STARTING) {
                    gameState = GameState.PLAYING;
                } else if (gameState == GameState.GAME_OVER) {
                    resetGame();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (gameState == GameState.PLAYING) {
                    dino.down(false);
                } else if (gameState == GameState.GAME_OVER) {
                    resetGame();
                }
                break;
            default:
                break;
        }
    }

    private void resetGame() {
        dino.setAlive(true);
        dino.setX(50);
        dino.setY(67);
        setScore(0);
        gameState = GameState.STARTING;
        enemyManager.reset();
    }

    public static float getGravity() {
        return GRAVITY;
    }

    public static float getGround() {
        return GROUND;
    }

    public boolean isKeyPressed() {
        return isKeyPressed;
    }

    public void setKeyPressed(boolean isKeyPressed) {
        this.isKeyPressed = isKeyPressed;
    }

    public int getGameState() {
        return gameState.ordinal();
    }

    public void setGameState(int gameState) {
        this.gameState = GameState.values()[gameState];
    }
}
