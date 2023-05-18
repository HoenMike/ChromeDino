package GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import Handler.Animation;
import Handler.Resource;

public class Dino {
    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DOWN_RUN = 2;
    private static final int DEATH = 3;
    public static final float JUMP_FORCE = -4;

    private float x = 50;
    private float y = 67;
    private float speedY = 0;
    private int state = NORMAL_RUN;

    private Animation dinoRun;
    private BufferedImage dinoJump;
    private Animation dinoDown;
    private BufferedImage dinoDeath;

    private Rectangle rect;
    private boolean isAlive = true;

    private Clip jumpSound;
    private Clip deathSound;
    private Clip scoreUpSound;

    // constrictor
    public Dino() {

        rect = new Rectangle();

        dinoRun = new Animation(90);
        dinoRun.addFrame(Resource.getResourceImage("data/dinoRun1.png"));
        dinoRun.addFrame(Resource.getResourceImage("data/dinoRun2.png"));

        dinoJump = Resource.getResourceImage("data/dinoIdle.png");

        dinoDown = new Animation(90);
        dinoDown.addFrame(Resource.getResourceImage("data/dino5.png"));
        dinoDown.addFrame(Resource.getResourceImage("data/dino6.png"));

        dinoDeath = Resource.getResourceImage("data/dinoDeath.png");
    }

    public void update() {
        dinoRun.update();
        dinoDown.update();
        // for jumping
        if (y >= UserInterface.GameScreen.getGround() - dinoRun.getFrame().getHeight()) {
            setSpeedY(0);
            setY(UserInterface.GameScreen.getGround() - dinoRun.getFrame().getHeight());
            if (state != DOWN_RUN) {
                state = NORMAL_RUN;
            }
        } else {
            setSpeedY(speedY += UserInterface.GameScreen.getGravity());
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
        if (y <= 67 && y >= 60) {
            setSpeedY(JUMP_FORCE);
            y += getSpeedY();
        }
    }

    public void down(boolean isDown) {
        if (state == JUMPING) {
            return;
        }
        if (isDown) {
            state = DOWN_RUN;
        } else {
            state = NORMAL_RUN;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        switch (state) {
            case NORMAL_RUN:
                g.drawImage(dinoRun.getFrame(), (int) x, (int) y, null);
                break;
            case JUMPING:
                g.drawImage(dinoJump, (int) x, (int) y, null);
                break;
            case DOWN_RUN:
                g.drawImage(dinoDown.getFrame(), (int) x, (int) (y + 20), null);
                break;
            case DEATH:
                g.drawImage(dinoDeath, (int) x, (int) y, null);
                break;
            default:
                break;
        }

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
