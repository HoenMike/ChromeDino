package UserInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameObject.Clouds;
import GameObject.Dino;
import GameObject.EnemiesManager;
import GameObject.Ground;
import Handler.Resource;
import Handler.ScoringSystem;

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_STATE = 0;
	private static final int PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;

	private Ground ground;
	private Dino dino;
	private EnemiesManager enemiesManager;
	private Clouds clouds;

	private boolean isKeyPressed;

	private int gameState = START_STATE;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	private long lastScoreUpdateTime;
	private final long SCORE_UPDATE_INTERVAL = 200; // 0.2 second

	private ScoringSystem scoringSystem;

	public GameScreen() {
		dino = new Dino();
		ground = new Ground(GameWindow.SCREEN_WIDTH, dino);
		dino.setDinoSpeedX(8);
		replayButtonImage = Resource.getResourceImage("data/replayButton.png");
		gameOverButtonImage = Resource.getResourceImage("data/gameOverText.png");
		scoringSystem = new ScoringSystem();
		enemiesManager = new EnemiesManager(dino, scoringSystem);
		clouds = new Clouds(dino);
	}

	public void startGame() {
		Thread thread;
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (gameState == PLAYING_STATE) {
			clouds.update();
			ground.update();
			dino.update();
			enemiesManager.update();
			if (enemiesManager.isCollide()) {
				dino.playDeadSound();
				gameState = GAME_OVER_STATE;
				dino.dead(true);
			}

			long currentTime = System.currentTimeMillis();
			if (currentTime - lastScoreUpdateTime >= SCORE_UPDATE_INTERVAL) {
				scoringSystem.increaseScore(1);
				lastScoreUpdateTime = currentTime;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
			case START_STATE:
				dino.draw(g);
				break;
			case PLAYING_STATE:
			case GAME_OVER_STATE:
				clouds.draw(g);
				ground.draw(g);
				enemiesManager.draw(g);
				dino.draw(g);
				g.setColor(Color.BLACK);
				if (scoringSystem.getHighScore() > 0) {
					g.drawString("HI: " + String.format("%05d", scoringSystem.getHighScore()), getWidth() - 60, 20);
					g.drawString(String.format("%05d", scoringSystem.getScore()), getWidth() - 42, 40);
				} else {
					g.drawString(String.format("%05d", scoringSystem.getScore()), getWidth() - 42, 20);
				}

				if (gameState == GAME_OVER_STATE) {
					g.drawImage(gameOverButtonImage, 200, 30, null);
					g.drawImage(replayButtonImage, 283, 50, null);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void run() {

		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;

		int msSleep;
		int nanoSleep;

		while (true) {
			gameUpdate();
			repaint();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
				case START_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						gameState = PLAYING_STATE;
					}
					break;
				case PLAYING_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						dino.jump();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						dino.crouch(true);
					}
					break;
				case GAME_OVER_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						gameState = PLAYING_STATE;
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
		if (gameState == PLAYING_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				dino.crouch(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO document why this method is empty
	}

	private void resetGame() {
		enemiesManager.reset();
		dino.dead(false);
		dino.reset();
		scoringSystem.resetScore();
	}
}
