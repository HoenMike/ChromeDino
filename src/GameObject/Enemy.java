package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	public abstract void update();

	public abstract void draw(Graphics g);

	public abstract Rectangle getCollision();

	public abstract boolean isOutOfScreen();
}
