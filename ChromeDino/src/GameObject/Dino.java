package GameObject;

import static UserInterface.GameScreen.GRAVITY;
import static UserInterface.GameScreen.GROUND;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Handler.Animation;
import Handler.Resource;

public class Dino {
    public static final float JUMP_FORCE = -4;

    private float x = 50;
    private float y = 67;
    private float speedY = 0;

    private Animation dinoRun;
    private Rectangle rect;
    private boolean isAlive = true;

    // constrictor
    public Dino() {
        dinoRun = new Animation(200);
        dinoRun.AddFrame(Resource.getResourceImage("ChromeDino/data/dinoRun1.png"));
        dinoRun.AddFrame(Resource.getResourceImage("ChromeDino/data/dinoRun2.png"));
        rect = new Rectangle();
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
        rect.x = (int) x + 3;
        rect.y = (int) y;
        rect.width = dinoRun.getFrame().getWidth() - 10;
        rect.height = dinoRun.getFrame().getHeight() - 10;
    }

    public Rectangle getCollisionShape() {
        return rect;
    }

    public void jump() {
        setSpeedY(JUMP_FORCE);
        y += getSpeedY();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(dinoRun.getFrame(), (int) x, (int) y, null);
        g.drawRect((int) x + 3, (int) y, dinoRun.getFrame().getWidth() - 10, dinoRun.getFrame().getHeight() - 10);

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

    public void setAlive(boolean state) {
        isAlive = state;
    }

    public boolean getAlive() {
        return isAlive;
    }

    public Animation getDinoRun() {
        return dinoRun;
    }

    public void setDinoRun(Animation dinoRun) {
        this.dinoRun = dinoRun;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public boolean isAlive() {
        return isAlive;
    }

}
