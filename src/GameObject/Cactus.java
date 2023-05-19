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
		setCactusXPosition(cactusXPosition);
		setCactusWidth(cactusWidth);
		setCactusHeight(cactusHeight);
		this.image = image;
		cactusCollisionShape = new Rectangle();
	}

	public void update() {
		setCactusXPosition(getCactusXPosition() - dino.getDinoSpeedX());
	}

	public void draw(Graphics g) {
		g.drawImage(image, getCactusXPosition(), GROUND_POSITION - image.getHeight(), null);
	}

	public Rectangle getCollision() {
		cactusCollisionShape = new Rectangle();
		cactusCollisionShape.x = (int) getCactusXPosition() + (image.getWidth() - getCactusWidth()) / 2;
		cactusCollisionShape.y = GROUND_POSITION - image.getHeight() + (image.getHeight() - getCactusHeight()) / 2;
		cactusCollisionShape.width = getCactusWidth();
		cactusCollisionShape.height = getCactusHeight();
		return cactusCollisionShape;
	}

	@Override
	public boolean isOutOfScreen() {
		if (getCactusXPosition() < -image.getWidth()) {
			return true;
		}
		return false;
	}

	public int getCactusXPosition() {
		return cactusXPosition;
	}

	public void setCactusXPosition(float cactusXPosition) {
		this.cactusXPosition = (int) cactusXPosition;
	}

	public int getCactusWidth() {
		return cactusWidth;
	}

	public void setCactusWidth(int cactusWidth) {
		this.cactusWidth = cactusWidth;
	}

	public int getCactusHeight() {
		return cactusHeight;
	}

	public void setCactusHeight(int cactusHeight) {
		this.cactusHeight = cactusHeight;
	}
}