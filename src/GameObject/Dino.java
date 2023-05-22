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

	private float dinoXPosition;
	private float dinoYPosition;
	private float dinoSpeedX;
	private float dinoSpeedY;

	private int state = NORMAL_RUN;

	private Animation normalRunAnimation;
	private Animation crouchRunAnimation;
	private BufferedImage jump;
	private BufferedImage dead;

	private AudioClip jumpSound;
	private AudioClip deadSound;

	private ScoringSystem scoringSystem;

	public Dino() {
		setDinoXPosition(50);
		setDinoYPosition(INITIAL_Y_POSITION);
		// run animation
		setNormalRunAnimation(new Animation(90));
		getNormalRunAnimation().addFrame(Resource.getResourceImage("data/dinoRun1.png"));
		getNormalRunAnimation().addFrame(Resource.getResourceImage("data/DinoRun2.png"));
		// crouch animation
		setCrouchRunAnimation(new Animation(90));
		getCrouchRunAnimation().addFrame(Resource.getResourceImage("data/dinoCrouch1.png"));
		getCrouchRunAnimation().addFrame(Resource.getResourceImage("data/dinoCrouch2.png"));
		// jump animation
		setJump(Resource.getResourceImage("data/dinoJump.png"));
		setDead(Resource.getResourceImage("data/dinoDeath.png"));
		// sound effects
		try {
			setJumpSound(Applet.newAudioClip(new URL("file", "", "data/jump.wav")));
			setDeadSound(Applet.newAudioClip(new URL("file", "", "data/dead.wav")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		setScoringSystem(new ScoringSystem());
	}

	// Draws the Dino on the screen based on its state
	public void draw(Graphics g) {
		switch (getState()) {
			case NORMAL_RUN:
				g.drawImage(getNormalRunAnimation().getFrame(), (int) getDinoXPosition(), (int) getInitialYPosition(), null);
				break;
			case JUMPING:
				g.drawImage(getJump(), (int) getDinoXPosition(), (int) getInitialYPosition(), null);
				break;
			case CROUCH_RUN:
				g.drawImage(getCrouchRunAnimation().getFrame(), (int) getDinoXPosition(), (int) (getInitialYPosition() + 20),
						null);
				break;
			case DEATH:
				g.drawImage(getDead(), (int) getDinoXPosition(), (int) getInitialYPosition(), null);
				break;
			default:
				break;
		}
	}

	// Updates the Dino's position and animation frame
	public void update() {
		getNormalRunAnimation().updateFrame();
		getCrouchRunAnimation().updateFrame();
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

	// Makes the Dino jump if it is on the ground
	public void jump() {
		if (getInitialYPosition() >= INITIAL_Y_POSITION - 15) {
			if (getJumpSound() != null) {
				getJumpSound().play();
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
		Rectangle dinoCollisionShape = new Rectangle();
		if (getState() == CROUCH_RUN) {
			dinoCollisionShape.x = (int) getDinoXPosition() + 5;
			dinoCollisionShape.y = (int) getInitialYPosition() + 20;
			dinoCollisionShape.width = getCrouchRunAnimation().getFrame().getWidth() - 10;
			dinoCollisionShape.height = getCrouchRunAnimation().getFrame().getHeight();
		} else {
			dinoCollisionShape.x = (int) getDinoXPosition() + 5;
			dinoCollisionShape.y = (int) getInitialYPosition();
			dinoCollisionShape.width = getNormalRunAnimation().getFrame().getWidth() - 10;
			dinoCollisionShape.height = getNormalRunAnimation().getFrame().getHeight();
		}
		return dinoCollisionShape;
	}

	// Sets the Dino's state to death or normal run
	public void dead(boolean isDeath) {
		if (isDeath) {
			setState(DEATH);
			getScoringSystem().resetScore();
		} else {
			setState(NORMAL_RUN);
		}
	}

	// Plays the sound effect for the Dino's death
	public void playDeadSound() {
		getDeadSound().play();
	}

	public void reset() {
		setDinoYPosition(INITIAL_Y_POSITION);
		getScoringSystem().resetScore();
		setDinoSpeedX(7f);
	}

	// Getters and setters

	private float getDinoXPosition() {
		return dinoXPosition;
	}

	private void setDinoXPosition(float dinoXPosition) {
		this.dinoXPosition = dinoXPosition;
	}

	private float getInitialYPosition() {
		return dinoYPosition;
	}

	private void setDinoYPosition(float dinoYPosition) {
		this.dinoYPosition = dinoYPosition;
	}

	public float getDinoSpeedX() {
		return dinoSpeedX;
	}

	public void setDinoSpeedX(float dinoSpeed) {
		this.dinoSpeedX = dinoSpeed;
	}

	private float getDinoSpeedY() {
		return dinoSpeedY;
	}

	private void setDinoSpeedY(float dinoSpeedY) {
		this.dinoSpeedY = dinoSpeedY;
	}

	private int getState() {
		return state;
	}

	private void setState(int state) {
		this.state = state;
	}

	private Animation getNormalRunAnimation() {
		return normalRunAnimation;
	}

	private void setNormalRunAnimation(Animation normalRunAnimation) {
		this.normalRunAnimation = normalRunAnimation;
	}

	private Animation getCrouchRunAnimation() {
		return crouchRunAnimation;
	}

	private void setCrouchRunAnimation(Animation crouchRunAnimation) {
		this.crouchRunAnimation = crouchRunAnimation;
	}

	private BufferedImage getJump() {
		return jump;
	}

	private void setJump(BufferedImage jump) {
		this.jump = jump;
	}

	private BufferedImage getDead() {
		return dead;
	}

	private void setDead(BufferedImage dead) {
		this.dead = dead;
	}

	private AudioClip getJumpSound() {
		return jumpSound;
	}

	private void setJumpSound(AudioClip jumpSound) {
		this.jumpSound = jumpSound;
	}

	private AudioClip getDeadSound() {
		return deadSound;
	}

	private void setDeadSound(AudioClip deadSound) {
		this.deadSound = deadSound;
	}

	private ScoringSystem getScoringSystem() {
		return scoringSystem;
	}

	private void setScoringSystem(ScoringSystem scoringSystem) {
		this.scoringSystem = scoringSystem;
	}
}