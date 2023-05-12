package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Handler.Resource;

public class Cactus {
    public BufferedImage image;

    public Cactus() {
        image = Resource.getResourceImage("data/cactus1.png");
    }

    public void draw(Graphics g) {
        g.drawImage(image, 200, 100, null);
    }

}
