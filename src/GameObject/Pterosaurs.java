package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
// import java.awt.Color;
import java.awt.image.BufferedImage;

public class Pterosaurs extends Enemy {

    private int pterosaursXPosition;
    private int pterosaursYPosition;
    private int pterosaursWidth;
    private int pterosaursHeight;

    private BufferedImage image;
    private Dino dino;

    private Rectangle pterosaursCollisionShape;

    public Pterosaurs(
            Dino dino,
            int pterosaursXPosition,
            int pterosaursYPosition,
            int pterosaursWidth,
            int pterosaursHeight,
            BufferedImage image) {
        this.dino = dino;
        this.pterosaursXPosition = pterosaursXPosition;
        this.pterosaursYPosition = pterosaursYPosition;
        this.pterosaursWidth = pterosaursWidth;
        this.pterosaursHeight = pterosaursHeight;
        setImage(image);
        pterosaursCollisionShape = new Rectangle();
    }

    // Updates the pterosaurs position based on dino's speed
    public void update() {
        setPterosaursXPosition(getPterosaursXPosition() - (int) dino.getDinoSpeed());
    }

    // Draws the pterosaurs on the screen
    public void draw(Graphics g) {
        g.drawImage(getImage(), getPterosaursXPosition(), getPterosaursYPosition() - getImage().getHeight(), null);
        // g.setColor(Color.BLACK);
        // g.drawRect(pterosaursCollisionShape.x, pterosaursCollisionShape.y,
        // pterosaursCollisionShape.width,
        // pterosaursCollisionShape.height);
    }

    // Returns the collision shape of the pterosaurs
    public Rectangle getCollision() {
        pterosaursCollisionShape = new Rectangle();
        pterosaursCollisionShape.x = getPterosaursXPosition() + (getImage().getWidth() - getPterosaursWidth()) / 2;
        pterosaursCollisionShape.y = getPterosaursYPosition() + 2 - getImage().getHeight()
                + (getImage().getHeight() - getPterosaursHeight()) / 2;
        pterosaursCollisionShape.width = getPterosaursWidth() - 7;
        pterosaursCollisionShape.height = getPterosaursHeight() - 5;
        return pterosaursCollisionShape;
    }

    // Checks if the pterosaurs is out of the screen
    @Override
    public boolean isOutOfScreen() {
        if (getPterosaursXPosition() < -getImage().getWidth()) {
            return true;
        }
        return false;
    }

    // Getters and setters

    public int getPterosaursXPosition() {
        return pterosaursXPosition;
    }

    public void setPterosaursXPosition(int pterosaursXPosition) {
        this.pterosaursXPosition = pterosaursXPosition;
    }

    private int getPterosaursYPosition() {
        return pterosaursYPosition;
    }

    public int getPterosaursWidth() {
        return pterosaursWidth;
    }

    public void setPterosaursWidth(int pterosaursWidth) {
        this.pterosaursWidth = pterosaursWidth;
    }

    public int getPterosaursHeight() {
        return pterosaursHeight;
    }

    public void setPterosaursHeight(int pterosaursHeight) {
        this.pterosaursHeight = pterosaursHeight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
