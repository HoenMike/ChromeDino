package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Handler.Resource;

public class Cactus extends Enemy {
    public BufferedImage image;
    private int xPosition, yPosition;
    private Rectangle rect;
    private Dino dino;

    public Cactus(Dino dino) {
        this.dino = dino;
        image = Resource.getResourceImage("data/cactus1.png");
        xPosition = 200;
        yPosition = 65;
        rect = new Rectangle();
    }

    @Override
    public void update() {
        xPosition -= 2;
        rect.x = xPosition;
        rect.y = yPosition;
        rect.width = image.getWidth();
        rect.height = image.getHeight();
    }

    @Override
    public Rectangle getCollisionShape() {
        return rect;
    }

    @Override
    public boolean isOutOfScreen() {
        return (xPosition + image.getWidth() < 0);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, xPosition, yPosition, null);
    }

    @Override
    public boolean isOver() {
        return (dino.getX() > xPosition);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

}
