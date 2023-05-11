package UserInterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

    private GameScreen gameScreen;

    public GameWindow() {
        super("Chrome Dino");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameScreen = new GameScreen();
        add(gameScreen);
        addKeyListener(gameScreen);
    }

    public void startGame() {
        gameScreen.startGame();
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        gameWindow.startGame();
    }
}
