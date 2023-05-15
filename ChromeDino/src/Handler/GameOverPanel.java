package Handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel {

    private BufferedImage gameOverText;
    private BufferedImage replayButton;

    public GameOverPanel() {
        gameOverText = Resource.getResourceImage("ChromeDino/data/gameOverText.png");
        replayButton = Resource.getResourceImage("ChromeDino/data/replayButton.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int imageX = (getWidth() - gameOverText.getWidth()) / 2;
        int imageY = (getHeight() - gameOverText.getHeight()) / 2;
        g.drawImage(gameOverText, imageX, imageY, null);

        int buttonX = (getWidth() - replayButton.getWidth()) / 2;
        int buttonY = (getHeight() + gameOverText.getHeight()) / 2;
        g.drawImage(replayButton, buttonX, buttonY, null);
    }

}
