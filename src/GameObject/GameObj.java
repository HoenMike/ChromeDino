package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObj {

	private Dino dino;
	private BufferedImage image;

	public abstract void update();

	public abstract void draw(Graphics g);

	public abstract Rectangle getCollision();

	public abstract boolean isOutOfScreen();

	// Getters and setters
	
	protected BufferedImage getImage() {
		return image;
	}

	protected void setImage(BufferedImage image) {
		this.image = image;
	}

	protected Dino getDino() {
		return dino;
	}

	protected void setDino(Dino dino) {
		this.dino = dino;
	}
}
