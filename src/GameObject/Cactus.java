package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus extends Enemy {

	public static final int GROUND_POSITION = 125;

	private int cactusXPosition;
	private int cactusWidth;
	private int cactusHeight;

	private BufferedImage image;
	private Dino dino;

	private Rectangle cactusCollisionShape;

	public Cactus(Dino dino, int cactusXPosition, int cactusWidth, int cactusHeight, BufferedImage image) {
		this.dino = dino;
		this.cactusXPosition = cactusXPosition;
		this.cactusWidth = cactusWidth;
		this.cactusHeight = cactusHeight;
		this.image = image;
		cactusCollisionShape = new Rectangle();
	}

	public void update() {
		cactusXPosition -= dino.getDinoSpeedX();
	}

	public void draw(Graphics g) {
		g.drawImage(image, cactusXPosition, GROUND_POSITION - image.getHeight(), null);
	}

	public Rectangle getCollision() {
		cactusCollisionShape = new Rectangle();
		cactusCollisionShape.x = (int) cactusXPosition + (image.getWidth() - cactusWidth) / 2;
		cactusCollisionShape.y = GROUND_POSITION - image.getHeight() + (image.getHeight() - cactusHeight) / 2;
		cactusCollisionShape.width = cactusWidth;
		cactusCollisionShape.height = cactusHeight;
		return cactusCollisionShape;
	}

	@Override
	public boolean isOutOfScreen() {
		if (cactusXPosition < -image.getWidth()) {
			return true;
		}
		return false;
	}

}
