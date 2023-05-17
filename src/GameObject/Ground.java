package GameObject;

import static UserInterface.GameScreen.GROUND;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Handler.Resource;

public class Ground {

    private List<ImageGround> listImage;
    private BufferedImage imageGround1 = Resource.getResourceImage("data/ground1.png");
    private BufferedImage imageGround2 = Resource.getResourceImage("data/ground2.png");
    private BufferedImage imageGround3 = Resource.getResourceImage("data/ground3.png");
    private Random random;

    public Ground() {
        random = new Random();
        listImage = new ArrayList<>();
        int numberOfGrounds = 600 / getImageGround1().getWidth();

        for (int i = 0; i <= numberOfGrounds + 1; i++) {
            ImageGround imageGround = new ImageGround();
            imageGround.xPosition = (i * getImageGround1().getWidth());
            imageGround.image = getRandomGround();
            listImage.add(imageGround);
        }
    }

    public void update() {
        for (ImageGround imageGround : listImage) {
            imageGround.xPosition -= 2;
        }
        // Delete the Ground image that passed the left side of the screen and add it to
        // the Right side of the screen
        ImageGround firstElement = listImage.get(0);
        if (firstElement.xPosition + getImageGround1().getWidth() < 0) {
            firstElement.xPosition = listImage.get(listImage.size() - 1).xPosition + getImageGround1().getWidth();
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
                return getImageGround1();
            case 1:
                return getImageGround2();
            default:
                return getImageGround3();

        }
    }

    private class ImageGround {
        int xPosition;
        BufferedImage image;
    }

    public BufferedImage getImageGround1(){
        return imageGround1;
    }

    public BufferedImage getImageGround2(){
        return imageGround2;
    }

    public BufferedImage getImageGround3(){
        return imageGround3;
    }
}
