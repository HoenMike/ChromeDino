package UserInterface;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 175;
	private GameScreen gameScreen;

	public GameWindow() {
		super("Chrome Dino");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setGameScreen(new GameScreen());
		addKeyListener(getGameScreen());
		add(getGameScreen());

		centerWindowOnScreen();
	}

	private void centerWindowOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		int windowWidth = getWidth();
		int windowHeight = getHeight();
		int x = (screenWidth - windowWidth) / 2;
		int y = (screenHeight - windowHeight) / 2;
		setLocation(x, y);
	}

	public void startGame() {
		setVisible(true);
		getGameScreen().startGame();
	}

	public static void main(String[] args) {
		new GameWindow().startGame();
	}

	// Getters and setters
	
	private GameScreen getGameScreen() {
		return gameScreen;
	}

	private void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
}
