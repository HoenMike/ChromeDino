package GameObject;

import java.awt.Color;
import java.awt.Graphics;

import Handler.Animation;
import Handler.Resource;

import static UserInterface.GameScreen.GROUND;
import static UserInterface.GameScreen.GRAVITY;

public class Dino {
    public static final float JUMP_FORCE = -4;

    private float x = 0;
    private float y = 0;
    private float speedY = 0;

    private Animation dinoRun;

    // constrictor
    public Dino() {
        dinoRun = new Animation(200);
        dinoRun.AddFrame(Resource.getResourceImage("data/dinoRun1.png"));
        dinoRun.AddFrame(Resource.getResourceImage("data/dinoRun2.png"));
    }

    public void update() {
        dinoRun.update();
        // for jumping
        if (y >= GROUND - dinoRun.getFrame().getHeight()) {
            setSpeedY(0);
            setY(GROUND - dinoRun.getFrame().getHeight());
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
        g.drawImage(dinoRun.getFrame(), (int) x, (int) y, null);
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

}
