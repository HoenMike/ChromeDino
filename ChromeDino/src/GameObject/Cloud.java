package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Handler.Resource;

public class Cloud {
    private BufferedImage cloudImage;

    public Cloud() {
        cloudImage = Resource.getResourceImage("cloud.jpg");
    }

    public void draw(Graphics g) {
        g.drawImage(cloudImage, 100, 50, null);
    }
}
