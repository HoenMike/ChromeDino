package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Handler.Resource;

public class Clouds {
    private BufferedImage cloudImage;

    private List<Cloud> clouds;

    public Clouds() {
        cloudImage = Resource.getResourceImage("data/cloud.png");
        clouds = new ArrayList<>();

        Cloud cloud1 = new Cloud();
        cloud1.xPosition = 100;
        cloud1.yPosition = 50;
        clouds.add(cloud1);

    }

    public void update() {
        for (Cloud cloud : clouds) {
            cloud.xPosition--;
        }
        Cloud firstCloud = clouds.get(0);
        if (firstCloud.xPosition + cloudImage.getWidth() < 0) {
            firstCloud.xPosition = 600;
            clouds.remove(firstCloud);
            clouds.add(firstCloud);
        }
    }

    public void draw(Graphics g) {
        for (Cloud cloud : clouds) {
            g.drawImage(cloudImage, (int) cloud.xPosition, (int) cloud.yPosition, null);
        }
    }

    private class Cloud {
        float xPosition, yPosition;
    }
}
