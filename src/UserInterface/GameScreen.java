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
	private static final long SCORE_UPDATE_INTERVAL = 200; // 0.2 second

	private Dino dino;
	private Ground ground;
	private ScoringSystem scoringSystem;
	private EnemiesManager enemiesManager;
	private Clouds clouds;
	
	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;
	
	private int gameState = START_STATE;
	private boolean isKeyPressed;
	private long lastScoreUpdateTime;

	public GameScreen() {
		setDino(new Dino());
		setGround(new Ground(GameWindow.SCREEN_WIDTH, getDino()));
		getDino().setDinoSpeedX(7f);
		setReplayButtonImage(Resource.getResourceImage("data/replayButton.png"));
		setGameOverButtonImage(Resource.getResourceImage("data/gameOverText.png"));
		setScoringSystem(new ScoringSystem());
		setEnemiesManager(new EnemiesManager(getDino(), getScoringSystem()));
		setClouds(new Clouds(getDino()));
	}

	public void startGame() {
		Thread thread;
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (getGameState() == PLAYING_STATE) {
			getClouds().update();
			getGround().update();
			getDino().update();
			getEnemiesManager().update();
			if (getEnemiesManager().isCollide()) {
				getDino().playDeadSound();
				setGameState(GAME_OVER_STATE);
				getDino().dead(true);
			}
			long currentTime = System.currentTimeMillis();
			if (currentTime - getLastScoreUpdateTime() >= SCORE_UPDATE_INTERVAL) {
				getScoringSystem().increaseScore(1);
				setLastScoreUpdateTime(currentTime);
				if (getDino().getDinoSpeedX() <= 21) {
					getDino().setDinoSpeedX(getDino().getDinoSpeedX() + 0.01f);
				}
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (getGameState()) {
			case START_STATE:
				getDino().draw(g);
				break;
			case PLAYING_STATE:
			case GAME_OVER_STATE:
				getClouds().draw(g);
				getGround().draw(g);
				getEnemiesManager().draw(g);
				getDino().draw(g);
				g.setColor(Color.BLACK);
				if (getScoringSystem().getHighScore() > 0) {
					g.drawString("HI: " + String.format("%05d", getScoringSystem().getHighScore()), getWidth() - 60, 20);
					g.drawString(String.format("%05d", getScoringSystem().getScore()), getWidth() - 42, 40);
				} else {
					g.drawString(String.format("%05d", getScoringSystem().getScore()), getWidth() - 42, 20);
				}

				if (getGameState() == GAME_OVER_STATE) {
					g.drawImage(getGameOverButtonImage(), 200, 30, null);
					g.drawImage(getReplayButtonImage(), 283, 50, null);
				}
				break;
			default:
				break;
		}
	}

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

	public void keyPressed(KeyEvent e) {
		if (!getIsKeyPressed()) {
			setIsKeyPressed(true);
			switch (getGameState()) {
				case START_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						setGameState(PLAYING_STATE);
					}
					break;
				case PLAYING_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						getDino().jump();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						getDino().crouch(true);
					}
					break;
				case GAME_OVER_STATE:
					if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						setGameState(PLAYING_STATE);
						resetGame();
					}
					break;
				default:
					break;

			}
		}

		// Check for ESC key press
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (getGameState() != START_STATE) {
				setGameState(START_STATE);
				resetGame();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		setIsKeyPressed(false);
		if (getGameState() == PLAYING_STATE &&  (e.getKeyCode() == KeyEvent.VK_DOWN)) {
			getDino().crouch(false);
			
		}
	}

	public void keyTyped(KeyEvent e) {
 	}

	private void resetGame() {
		getEnemiesManager().reset();
		getDino().dead(false);
		getDino().reset();
		getScoringSystem().resetScore();
	}

	// Getters and setters

	private Dino getDino() {
		return dino;
	}

	private void setDino(Dino dino) {
		this.dino = dino;
	}

	private Ground getGround() {
		return ground;
	}

	private void setGround(Ground ground) {
		this.ground = ground;
	}

	private ScoringSystem getScoringSystem() {
		return scoringSystem;
	}

	private void setScoringSystem(ScoringSystem scoringSystem) {
		this.scoringSystem = scoringSystem;
	}

	private EnemiesManager getEnemiesManager() {
		return enemiesManager;
	}

	private void setEnemiesManager(EnemiesManager enemiesManager) {
		this.enemiesManager = enemiesManager;
	}

	private Clouds getClouds() {
		return clouds;
	}

	private void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	private BufferedImage getReplayButtonImage() {
		return replayButtonImage;
	}

	private void setReplayButtonImage(BufferedImage replayButtonImage) {
		this.replayButtonImage = replayButtonImage;
	}

	private BufferedImage getGameOverButtonImage() {
		return gameOverButtonImage;
	}

	private void setGameOverButtonImage(BufferedImage gameOverButtonImage) {
		this.gameOverButtonImage = gameOverButtonImage;
	}

	private int getGameState() {
		return gameState;
	}

	private void setGameState(int gameState) {
		this.gameState = gameState;
	}

	private boolean getIsKeyPressed() {
		return isKeyPressed;
	}

	private void setIsKeyPressed(boolean isKeyPressed) {
		this.isKeyPressed = isKeyPressed;
	}

	private long getLastScoreUpdateTime() {
		return lastScoreUpdateTime;
	}

	private void setLastScoreUpdateTime(long lastScoreUpdateTime) {
		this.lastScoreUpdateTime = lastScoreUpdateTime;
	}
}
