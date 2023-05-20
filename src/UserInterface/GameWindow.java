package UserInterface;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class GameWindow extends JFrame {

	public static final int SCREEN_WIDTH = 600;
	private GameScreen gameScreen;

	public GameWindow() {
		super("Chrome Dino");
		setSize(SCREEN_WIDTH, 175);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);

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
		gameScreen.startGame();
	}

	public static void main(String args[]) {
		new GameWindow().startGame();
	}
}
