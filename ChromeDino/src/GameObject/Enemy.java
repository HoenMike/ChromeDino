package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
    public abstract Rectangle getCollision();

    public abstract void draw(Graphics g);

    public abstract void update();

    public abstract boolean isOutOfScreen();
}
