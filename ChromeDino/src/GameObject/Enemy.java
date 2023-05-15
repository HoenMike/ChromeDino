package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
    public abstract Rectangle getCollisionShape();

    public abstract void draw(Graphics g);

    public abstract void update();

    public abstract boolean isOutOfScreen();

    public abstract boolean isOver();

    public abstract boolean isScored();

    public abstract void setIsScored(boolean value);
}
