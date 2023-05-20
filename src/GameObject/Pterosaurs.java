package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Handler.Animation;
import Handler.Resource;

public class Pterosaurs extends Enemy {

	public static final int GROUND_POSITION = 125;

	private Animation fly;

	private int pterosaursXPosition;
	private int pterosaursWidth;
	private int pterosaursHeight;

	private BufferedImage image;
	private Dino dino;

	private Rectangle pterosaurCollisionShape;

	public Pterosaurs(Dino dino, int pterosaursXPosition, int pterosaursWidth, int pterosaursHeight, BufferedImage image) {
		
		fly = new Animation(90);
		fly.addFrame(Resource.getResourceImage("data/pterosaur1.png"));
		fly.addFrame(Resource.getResourceImage("data/pterosaur2.png"));

		this.dino = dino;
		setPterosaursXPosition(pterosaursXPosition);
		setPterosaursWidth(pterosaursWidth);
		setPterosaursHeight(pterosaursHeight);
		setImage(image);
		pterosaurCollisionShape = new Rectangle();
	}

	// Updates the cactus position based on dino's speed
	public void update() {
		setPterosaursXPosition(getPterosaursXPosition() - (int) dino.getDinoSpeedX());
	}

	// Draws the cactus on the screen
	public void draw(Graphics g) {
		g.drawImage(getImage(), getPterosaursXPosition(), GROUND_POSITION - getImage().getHeight(), null);
	}

	// Returns the collision shape of the cactus
	public Rectangle getCollision() {
		pterosaurCollisionShape = new Rectangle();
		pterosaurCollisionShape.x = getPterosaursXPosition() + (getImage().getWidth() - getPterosaursWidth()) / 2;
		pterosaurCollisionShape.y = GROUND_POSITION - getImage().getHeight()
				+ (getImage().getHeight() - getPterosaursHeight()) / 2;
		pterosaurCollisionShape.width = getPterosaursWidth();
		pterosaurCollisionShape.height = getPterosaursHeight();
		return pterosaurCollisionShape;
	}

	// Checks if the cactus is out of the screen
	@Override
	public boolean isOutOfScreen() {
		if (getPterosaursXPosition() < -getImage().getWidth()) {
			return true;
		}
		return false;
	}

	// Getters and setters

	public int getPterosaursXPosition() {
		return pterosaursXPosition;
	}

	public void setPterosaursXPosition(int pterosaursXPosition) {
		this.pterosaursXPosition = pterosaursXPosition;
	}

	public int getPterosaursWidth() {
		return pterosaursWidth;
	}

	public void setPterosaursWidth(int pterosaursWidth) {
		this.pterosaursWidth = pterosaursWidth;
	}

	public int getPterosaursHeight() {
		return pterosaursHeight;
	}

	public void setPterosaursHeight(int pterosaursHeight) {
		this.pterosaursHeight = pterosaursHeight;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
