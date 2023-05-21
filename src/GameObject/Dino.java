package GameObject;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import Handler.Animation;
import Handler.Resource;
import Handler.ScoringSystem;

public class Dino extends ScoringSystem {

	public static final int INITIAL_Y_POSITION = 80;
	public static final float GRAVITY = 0.4f;

	private static final int NORMAL_RUN = 0;
	private static final int JUMPING = 1;
	private static final int CROUCH_RUN = 2;
	private static final int DEATH = 3;

	private float dinoYPosition;
	private float dinoXPosition;
	private float dinoSpeedX;
	private float dinoSpeedY;
	private Rectangle dinoCollisionShape;

	private int state = NORMAL_RUN;

	private Animation normalRunAnimation;
	private Animation crouchRunAnimation;
	private BufferedImage jump;
	private BufferedImage dead;

	private AudioClip jumpSound;
	private AudioClip deadSound;

	private ScoringSystem scoringSystem;

	public Dino() {
		dinoXPosition = 50;
		dinoYPosition = INITIAL_Y_POSITION;
		dinoCollisionShape = new Rectangle();
		// run animation
		normalRunAnimation = new Animation(90);
		normalRunAnimation.addFrame(Resource.getResourceImage("data/dinoRun1.png"));
		normalRunAnimation.addFrame(Resource.getResourceImage("data/DinoRun2.png"));
		// crouch animation
		crouchRunAnimation = new Animation(90);
		crouchRunAnimation.addFrame(Resource.getResourceImage("data/dinoCrouch1.png"));
		crouchRunAnimation.addFrame(Resource.getResourceImage("data/dinoCrouch2.png"));
		// jump animation
		jump = Resource.getResourceImage("data/dinoJump.png");
		dead = Resource.getResourceImage("data/dinoDeath.png");
		// sound effects
		try {
			jumpSound = Applet.newAudioClip(new URL("file", "", "data/jump.wav"));
			deadSound = Applet.newAudioClip(new URL("file", "", "data/dead.wav"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		scoringSystem = new ScoringSystem();
	}

	// Draws the Dino on the screen based on its state
	public void draw(Graphics g) {
		switch (getState()) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnimation.getFrame(), (int) getDinoXPosition(), (int) getInitialYPosition(), null);
				break;
			case JUMPING:
				g.drawImage(jump, (int) getDinoXPosition(), (int) getInitialYPosition(), null);
				break;
			case CROUCH_RUN:
				g.drawImage(crouchRunAnimation.getFrame(), (int) getDinoXPosition(), (int) (getInitialYPosition() + 20),
						null);
				break;
			case DEATH:
				g.drawImage(dead, (int) getDinoXPosition(), (int) getInitialYPosition(), null);
				break;
		}
	}

	// Updates the Dino's position and animation frame
	public void update() {
		normalRunAnimation.updateFrame();
		crouchRunAnimation.updateFrame();
		if (getInitialYPosition() >= INITIAL_Y_POSITION) {
			setDinoYPosition(INITIAL_Y_POSITION);
			if (getState() != CROUCH_RUN) {
				setState(NORMAL_RUN);
			}
		} else {
			setDinoSpeedY(getDinoSpeedY() + GRAVITY);
			setDinoYPosition(getInitialYPosition() + getDinoSpeedY());
		}
	}

	public boolean whatScore() {
		if (getScore() <= 50) {
			return false;
		}
		return true;
	}

	// Makes the Dino jump if it is on the ground
	public void jump() {
		if (getInitialYPosition() >= INITIAL_Y_POSITION - 15) {
			if (jumpSound != null) {
				jumpSound.play();
			}
			setDinoSpeedY(-7.5f);
			setDinoYPosition(getInitialYPosition() + getDinoSpeedY());
			setState(JUMPING);
		}
	}

	// Makes the Dino crouch or stand up
	public void crouch(boolean isCrouch) {
		if (getState() == JUMPING) {
			setDinoSpeedY(7.5f);
			setDinoYPosition(getInitialYPosition() + getDinoSpeedY());
			setState(NORMAL_RUN);
		}
		if (isCrouch) {
			setState(CROUCH_RUN);
		} else {
			setState(NORMAL_RUN);
		}
	}

	// Returns the collision shape of the Dino based on its state
	public Rectangle getDinoCollisionShape() {
		dinoCollisionShape = new Rectangle();
		if (getState() == CROUCH_RUN) {
			dinoCollisionShape.x = (int) getDinoXPosition() + 5;
			dinoCollisionShape.y = (int) getInitialYPosition() + 20;
			dinoCollisionShape.width = crouchRunAnimation.getFrame().getWidth() - 10;
			dinoCollisionShape.height = crouchRunAnimation.getFrame().getHeight();
		} else {
			dinoCollisionShape.x = (int) getDinoXPosition() + 5;
			dinoCollisionShape.y = (int) getInitialYPosition();
			dinoCollisionShape.width = normalRunAnimation.getFrame().getWidth() - 10;
			dinoCollisionShape.height = normalRunAnimation.getFrame().getHeight();
		}
		return dinoCollisionShape;
	}

	// Sets the Dino's state to death or normal run
	public void dead(boolean isDeath) {
		if (isDeath) {
			setState(DEATH);
			scoringSystem.resetScore();
		} else {
			setState(NORMAL_RUN);
		}
	}

	// Plays the sound effect for the Dino's death
	public void playDeadSound() {
		deadSound.play();
	}

	public void reset() {
		setDinoYPosition(INITIAL_Y_POSITION);
		scoringSystem.resetScore();
	}

	// Getters and setters
	public float getDinoSpeedX() {
		return dinoSpeedX;
	}

	public void setDinoSpeedX(float dinoSpeedX) {
		this.dinoSpeedX = dinoSpeedX;
	}

	public float getInitialYPosition() {
		return dinoYPosition;
	}

	public void setDinoYPosition(float dinoYPosition) {
		this.dinoYPosition = dinoYPosition;
	}

	public float getDinoXPosition() {
		return dinoXPosition;
	}

	public void setDinoXPosition(float dinoXPosition) {
		this.dinoXPosition = dinoXPosition;
	}

	public float getDinoSpeedY() {
		return dinoSpeedY;
	}

	public void setDinoSpeedY(float dinoSpeedY) {
		this.dinoSpeedY = dinoSpeedY;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
