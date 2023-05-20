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

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_STATE = 0;
	private static final int PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;

	private Ground ground;
	private Dino dino;
	private EnemiesManager enemiesManager;
	private Clouds clouds;
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = START_STATE;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	private long lastScoreUpdateTime;
	private final long SCORE_UPDATE_INTERVAL = 200; // 0.2 second

	public GameScreen() {
		dino = new Dino();
		ground = new Ground(GameWindow.SCREEN_WIDTH, dino);
		dino.setDinoSpeedX(6);
		replayButtonImage = Resource.getResourceImage("data/replayButton.png");
		gameOverButtonImage = Resource.getResourceImage("data/gameOverText.png");
		enemiesManager = new EnemiesManager(dino);
		clouds = new Clouds(GameWindow.SCREEN_WIDTH, dino);
	}

	public void startGame() {
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
				dino.upScore(1);
				lastScoreUpdateTime = currentTime;
			}
		}
	}

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
				if (dino.getHighScore() > 0) {
					g.drawString("HI: " + String.format("%05d", dino.getHighScore()), getWidth() - 60, 20);
					g.drawString(String.format("%05d", dino.getScore()), getWidth() - 42, 40);
				} else {
					g.drawString(String.format("%05d", dino.getScore()), getWidth() - 42, 20);
				}

				if (gameState == GAME_OVER_STATE) {
					g.drawImage(gameOverButtonImage, 200, 30, null);
					g.drawImage(replayButtonImage, 283, 50, null);
				}
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
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						gameState = PLAYING_STATE;
					}
					break;
				case PLAYING_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						dino.jump();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						dino.crouch(true);
					}
					break;
				case GAME_OVER_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						gameState = PLAYING_STATE;
						resetGame();
					}
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

	}

	private void resetGame() {
		enemiesManager.reset();
		dino.dead(false);
		dino.reset();
	}
}
