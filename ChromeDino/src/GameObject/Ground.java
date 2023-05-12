package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import static UserInterface.GameScreen.GROUND;

import Handler.Resource;
import UserInterface.GameScreen;

public class Ground {

    private List<ImageGround> listImage;
    private BufferedImage imageGround1, imageGround2, imageGround3;
    private Random random;

    public Ground(GameScreen game) {
        random = new Random();
        imageGround1 = Resource.getResourceImage("data/ground1.png");
        imageGround2 = Resource.getResourceImage("data/ground2.png");
        imageGround3 = Resource.getResourceImage("data/ground3.png");
        listImage = new ArrayList<ImageGround>();
        int numberOfGrounds = 600 / imageGround1.getWidth();

        for (int i = 0; i <= numberOfGrounds + 1; i++) {
            ImageGround imageGround = new ImageGround();
            imageGround.xPosition = (int) (i * imageGround1.getWidth());
            imageGround.image = getRandomGround();
            listImage.add(imageGround);
        }
    }

    public void update() {
        for (ImageGround imageGround : listImage) {
            imageGround.xPosition--;
        }
        // Delete the Ground image that passed the left side of the screen and add it to
        // the Right side of the screen
        ImageGround firstElement = listImage.get(0);
        if (firstElement.xPosition + imageGround1.getWidth() < 0) {
            firstElement.xPosition = listImage.get(listImage.size() - 1).xPosition + imageGround1.getWidth();
            listImage.add(listImage.get(0));
            listImage.remove(0);
        }
    }

    public void draw(Graphics g) {
        for (ImageGround imageGround : listImage) {
            g.drawImage(imageGround.image, imageGround.xPosition, (int) GROUND - 20, null);
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
