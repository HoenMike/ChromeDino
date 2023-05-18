package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;

public class Ground {

    private List<ImageGround> groundImages;
    private BufferedImage imageGround1;
    private BufferedImage imageGround2;
    private BufferedImage imageGround3;
    private Random random;

    public Ground() {
        random = new Random();
        groundImages = new ArrayList<>();
        imageGround1 = Resource.getResourceImage("data/ground1.png");
        imageGround2 = Resource.getResourceImage("data/ground2.png");
        imageGround3 = Resource.getResourceImage("data/ground3.png");

        int numberOfGrounds = 600 / imageGround1.getWidth();

        for (int i = 0; i <= numberOfGrounds + 1; i++) {
            ImageGround imageGround = new ImageGround();
            imageGround.xPosition = i * imageGround1.getWidth();
            imageGround.image = getRandomGround();
            groundImages.add(imageGround);
        }
    }

    public void update() {
        for (ImageGround imageGround : groundImages) {
            imageGround.xPosition -= 2;
        }
        // Delete the Ground image that passed the left side of the screen and add it to
        // the Right side of the screen
        ImageGround firstElement = groundImages.get(0);
        if (firstElement.xPosition + imageGround1.getWidth() < 0) {
            firstElement.xPosition = groundImages.get(groundImages.size() - 1).xPosition + imageGround1.getWidth();
            groundImages.add(groundImages.get(0));
            groundImages.remove(0);
        }
    }

    public void draw(Graphics g) {
        for (ImageGround imageGround : groundImages) {
            g.drawImage(imageGround.image, imageGround.xPosition, (int) UserInterface.GameScreen.getGround() - 20,
                    null);
        }
    }

    private BufferedImage getRandomGround() {
        int i = random.nextInt(10);
        switch (i) {
            case 0:
                return imageGround1;
            case 1:
                return imageGround2;
            default:
                return imageGround3;
        }
    }

    private class ImageGround {
        int xPosition;
        BufferedImage image;
    }
}
