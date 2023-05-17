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
    // Game states
    public static final int STARTING_STATE = 0;
    public static final int PLAYING_STATE = 1;
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
    private int highScore;

    // Game state
    private int gameState = STARTING_STATE;
    private boolean isKeyPressed;

    // Game images
    private BufferedImage imageGameOverText;
    private BufferedImage imageReplayButton;

    // Thread for the game loop
    private Thread thread;

    // Constructor
    public GameScreen() {
        dino = new Dino();
        dino.setX(4);
        ground = new Ground();
        cloud = new Clouds();
        enemyManager = new EnemyManager(dino, this);
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
        switch (getGameState()) {
            case PLAYING_STATE:
                cloud.update();
                ground.update();
                dino.update();
                enemyManager.update();
                if (score > highScore) {
                    highScore = score;
                }
                if (!dino.getAlive()) {
                    setGameState(GAME_OVER_STATE);
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
        int replayButtonY = getHeight() / 2 + imageGameOverText.getHeight() / 2;
        g.drawImage(imageReplayButton, replayButtonX, replayButtonY, null);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        switch (getGameState()) {
            case STARTING_STATE:
                dino.draw(g);
                break;
            case PLAYING_STATE:
                drawGamePlayingState(g);
                break;
            case GAME_OVER_STATE:
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
        if (!getIsKeyPressed()) {
			setIsKeyPressed(true);
			switch (getGameState()) {
			case STARTING_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					setGameState(PLAYING_STATE);
				}
				break;
			case PLAYING_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					dino.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					dino.down(true);
				}
				break;
			case GAME_OVER_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					setGameState(PLAYING_STATE);
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
        setIsKeyPressed(false);
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                if (getGameState() == STARTING_STATE) {
                    setGameState(PLAYING_STATE);
                } else if (getGameState() == GAME_OVER_STATE) {
                    resetGame();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (getGameState() == PLAYING_STATE) {
				    dino.down(false);
                } else if (getGameState() == GAME_OVER_STATE) {
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
        setGameState(STARTING_STATE);
        enemyManager.reset();
    }

    // getter & setter
    public static float getGravity() {
        return GRAVITY;
    }

    public static float getGround() {
        return GROUND;
    }

    public boolean getIsKeyPressed() {
        return isKeyPressed;
    }

    public void setIsKeyPressed(boolean isKeyPressed) {
        this.isKeyPressed = isKeyPressed;
    }

    public int getGameState() {
        return gameState;
    }

    private void setGameState(int gameState) {
        this.gameState = gameState;
    }
}
