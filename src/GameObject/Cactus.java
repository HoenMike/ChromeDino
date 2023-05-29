/* Name: Group 5
 Member names & IU code:
	Mai Nguyên Hoàng – ITITIU21208
	Nguyễn Minh Duy – ITITIU21186
 Purpose: Chrome Dino game for OOP Lab Project
*/
package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus extends GameObj {

	public static final int SPAWN_POSITION = 125;

	private int cactusXPosition;
	private int cactusWidth;
	private int cactusHeight;

	public Cactus(Dino dino, int cactusXPosition, int cactusWidth, int cactusHeight, BufferedImage image) {
		setDino(dino);
		setCactusXPosition(cactusXPosition);
		setCactusWidth(cactusWidth);
		setCactusHeight(cactusHeight);
		setImage(image);
	}

	// Updates the cactus position based on dino's speed
	public void update() {
		setCactusXPosition(getCactusXPosition() - (int) getDino().getDinoSpeed());
	}

	// Draws the cactus on the screen
	public void draw(Graphics g) {
		g.drawImage(getImage(), getCactusXPosition(), SPAWN_POSITION - getImage().getHeight(), null);
	}

	// Returns the collision shape of the cactus
	public Rectangle getCollision() {
		Rectangle cactusCollisionShape = new Rectangle();
		cactusCollisionShape.x = getCactusXPosition() + (getImage().getWidth() - getCactusWidth()) / 2;
		cactusCollisionShape.y = SPAWN_POSITION - getImage().getHeight()
				+ (getImage().getHeight() - getCactusHeight()) / 2;
		cactusCollisionShape.width = getCactusWidth();
		cactusCollisionShape.height = getCactusHeight();
		return cactusCollisionShape;
	}

	// Checks if the cactus is out of the screen
	public boolean isOutOfScreen() {
		if (getCactusXPosition() < -getImage().getWidth()) {
			return true;
		}
		return false;
	}

	// Getters and setters

	private int getCactusXPosition() {
		return cactusXPosition;
	}

	private void setCactusXPosition(int cactusXPosition) {
		this.cactusXPosition = cactusXPosition;
	}

	private int getCactusWidth() {
		return cactusWidth;
	}

	private void setCactusWidth(int cactusWidth) {
		this.cactusWidth = cactusWidth;
	}

	private int getCactusHeight() {
		return cactusHeight;
	}

	private void setCactusHeight(int cactusHeight) {
		this.cactusHeight = cactusHeight;
	}
}