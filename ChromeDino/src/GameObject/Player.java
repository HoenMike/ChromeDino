package GameObject;

import java.awt.Color;
import java.awt.Graphics;

import static UserInterface.GameScreen.FLOOR;
import static UserInterface.GameScreen.GRAVITY;

public class Player {
    public static final float JUMP_FORCE = -4;

    private float x = 0;
    private float y = 0;
    private float speedY = 0;
    private int playerHeight = 120;
    private int playerWidth = 60;

    public void update() {
        if (y >= FLOOR - getPlayerHeight()) {
            setSpeedY(0);
            setY(FLOOR - getPlayerHeight());
        } else {
            setSpeedY(speedY += GRAVITY);
            setY(y += speedY);
        }
    }

    public void jump() {
        setSpeedY(JUMP_FORCE);
        y += getSpeedY();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect((int) x, (int) y, getPlayerWidth(), getPlayerHeight());
    }

    public static float getJumpForce() {
        return JUMP_FORCE;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(int playerHeight) {
        this.playerHeight = playerHeight;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(int playerWidth) {
        this.playerWidth = playerWidth;
    }

}
