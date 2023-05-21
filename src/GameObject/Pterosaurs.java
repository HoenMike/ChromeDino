package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Pterosaurs extends GameObj {

    private int pterosaursXPosition;
    private int pterosaursYPosition;
    private int pterosaursWidth;
    private int pterosaursHeight;

    public Pterosaurs(
            Dino dino,
            int pterosaursXPosition,
            int pterosaursYPosition,
            int pterosaursWidth,
            int pterosaursHeight,
            BufferedImage image) {
        setDino(dino);
        setPterosaursXPosition(pterosaursXPosition);
        setPterosaursYPosition(pterosaursYPosition);
        setPterosaursWidth(pterosaursWidth);
        setPterosaursHeight(pterosaursHeight);
        setImage(image);
    }

    // Updates the pterosaurs position based on dino's speed
    public void update() {
        setPterosaursXPosition(getPterosaursXPosition() - (int) getDino().getDinoSpeedX());
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
        Rectangle pterosaursCollisionShape = new Rectangle();
        pterosaursCollisionShape.x = getPterosaursXPosition() + 7 + (getImage().getWidth() - getPterosaursWidth()) / 2;
        pterosaursCollisionShape.y = getPterosaursYPosition() + 8 - getImage().getHeight()
                + (getImage().getHeight() - getPterosaursHeight()) / 2;
        pterosaursCollisionShape.width = getPterosaursWidth() - 9;
        pterosaursCollisionShape.height = getPterosaursHeight() - 8;
        return pterosaursCollisionShape;
    }

    // Checks if the pterosaurs is out of the screen
    public boolean isOutOfScreen() {
        if (getPterosaursXPosition() < -getImage().getWidth()) {
            return true;
        }
        return false;
    }

    // Getters and setters

    private int getPterosaursXPosition() {
        return pterosaursXPosition;
    }

    private void setPterosaursXPosition(int pterosaursXPosition) {
        this.pterosaursXPosition = pterosaursXPosition;
    }

    private int getPterosaursYPosition() {
        return pterosaursYPosition;
    }

    private void setPterosaursYPosition(int pterosaursYPosition) {
        this.pterosaursYPosition = pterosaursYPosition;
    }

    private int getPterosaursWidth() {
        return pterosaursWidth;
    }

    private void setPterosaursWidth(int pterosaursWidth) {
        this.pterosaursWidth = pterosaursWidth;
    }

    private int getPterosaursHeight() {
        return pterosaursHeight;
    }

    private void setPterosaursHeight(int pterosaursHeight) {
        this.pterosaursHeight = pterosaursHeight;
    }
}
