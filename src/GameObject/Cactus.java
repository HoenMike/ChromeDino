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
		setImage(image);
		cactusCollisionShape = new Rectangle();
	}

	// Updates the cactus position based on dino's speed
	public void update() {
		setCactusXPosition(getCactusXPosition() - (int) dino.getDinoSpeedX());
	}

	// Draws the cactus on the screen
	public void draw(Graphics g) {
		g.drawImage(getImage(), getCactusXPosition(), GROUND_POSITION - getImage().getHeight(), null);
	}

	// Returns the collision shape of the cactus
	public Rectangle getCollision() {
		cactusCollisionShape = new Rectangle();
		cactusCollisionShape.x = getCactusXPosition() + (getImage().getWidth() - getCactusWidth()) / 2;
		cactusCollisionShape.y = GROUND_POSITION - getImage().getHeight()
				+ (getImage().getHeight() - getCactusHeight()) / 2;
		cactusCollisionShape.width = getCactusWidth();
		cactusCollisionShape.height = getCactusHeight();
		return cactusCollisionShape;
	}

	// Checks if the cactus is out of the screen
	@Override
	public boolean isOutOfScreen() {
		if (getCactusXPosition() < -getImage().getWidth()) {
			return true;
		}
		return false;
	}

	// Getters and setters

	public int getCactusXPosition() {
		return cactusXPosition;
	}

	public void setCactusXPosition(int cactusXPosition) {
		this.cactusXPosition = cactusXPosition;
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
