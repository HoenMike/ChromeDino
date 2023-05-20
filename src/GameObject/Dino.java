package GameObject;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
// import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import Handler.Animation;
import Handler.Resource;

public class Dino {

	public static final int GROUND_Y_POSITION = 80;
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

	private int score = 0;
	private int highScore = 0;
	private int nextMilestone = 100;

	private int state = NORMAL_RUN;

	private Animation normalRunAnimation;
	private Animation crouchRunAnimation;
	private BufferedImage jumping;
	private BufferedImage dead;

	private AudioClip jumpSound;
	private AudioClip deadSound;
	private AudioClip scoreUpSound;

	public Dino() {
		dinoXPosition = 50;
		dinoYPosition = GROUND_Y_POSITION;
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
		jumping = Resource.getResourceImage("data/dinoJump.png");
		dead = Resource.getResourceImage("data/dinoDeath.png");
		// sound effects
		try {
			jumpSound = Applet.newAudioClip(new URL("file", "", "data/jump.wav"));
			deadSound = Applet.newAudioClip(new URL("file", "", "data/dead.wav"));
			scoreUpSound = Applet.newAudioClip(new URL("file", "", "data/scoreUp.wav"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		switch (getState()) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnimation.getFrame(), (int) getDinoXPosition(), (int) getDinoYPosition(), null);
				break;
			case JUMPING:
				g.drawImage(jumping, (int) getDinoXPosition(), (int) getDinoYPosition(), null);
				break;
			case CROUCH_RUN:
				g.drawImage(crouchRunAnimation.getFrame(), (int) getDinoXPosition(), (int) (getDinoYPosition() + 20),
						null);
				break;
			case DEATH:
				g.drawImage(dead, (int) getDinoXPosition(), (int) getDinoYPosition(), null);
				break;
		}
		// Rectangle bound = getDinoCollisionShape();
		// g.setColor(Color.RED);
		// g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}

	public void update() {
		normalRunAnimation.updateFrame();
		crouchRunAnimation.updateFrame();
		if (getDinoYPosition() >= GROUND_Y_POSITION) {
			setDinoYPosition(GROUND_Y_POSITION);
			if (getState() != CROUCH_RUN) {
				setState(NORMAL_RUN);
			}
		} else {
			setDinoSpeedY(getDinoSpeedY() + GRAVITY);
			setDinoYPosition(getDinoYPosition() + getDinoSpeedY());
		}
	}

	public void jump() {
		if (getDinoYPosition() >= GROUND_Y_POSITION) {
			if (jumpSound != null) {
				jumpSound.play();
			}
			setDinoSpeedY(-7.5f);
			setDinoYPosition(getDinoYPosition() + getDinoSpeedY());
			setState(JUMPING);
		}
	}

	public void crouch(boolean isCrouch) {
		if (getState() == JUMPING) {
			return;
		}
		if (isCrouch) {
			setState(CROUCH_RUN);
		} else {
			setState(NORMAL_RUN);
		}
	}

	public Rectangle getDinoCollisionShape() {
		dinoCollisionShape = new Rectangle();
		if (getState() == CROUCH_RUN) {
			dinoCollisionShape.x = (int) getDinoXPosition() + 5;
			dinoCollisionShape.y = (int) getDinoYPosition() + 20;
			dinoCollisionShape.width = crouchRunAnimation.getFrame().getWidth() - 10;
			dinoCollisionShape.height = crouchRunAnimation.getFrame().getHeight();
		} else {
			dinoCollisionShape.x = (int) getDinoXPosition() + 5;
			dinoCollisionShape.y = (int) getDinoYPosition();
			dinoCollisionShape.width = normalRunAnimation.getFrame().getWidth() - 10;
			dinoCollisionShape.height = normalRunAnimation.getFrame().getHeight();
		}
		return dinoCollisionShape;
	}

	public void dead(boolean isDeath) {
		if (isDeath) {
			setState(DEATH);
			if (getScore() >= getHighScore()) {
				setHighScore(getScore());
			}
			setScore(0);
		} else {
			setState(NORMAL_RUN);
		}
	}

	public void playDeadSound() {
		deadSound.play();
	}

	public void reset() {
		setDinoYPosition(GROUND_Y_POSITION);
		setNextMilestone(100);
	}

	public void upScore(int score) {
		setScore(getScore() + score);
		if (getScore() >= getNextMilestone()) {
			setNextMilestone(getNextMilestone() + 100);
			scoreUpSound.play();
		}
	}

	// getter setter
	private void setNextMilestone(int nextMilestone) {
		this.nextMilestone = nextMilestone;
	}

	private int getNextMilestone() {
		return nextMilestone;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public float getDinoSpeedX() {
		return dinoSpeedX;
	}

	public void setDinoSpeedX(float dinoSpeedX) {
		this.dinoSpeedX = dinoSpeedX;
	}

	public float getDinoYPosition() {
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
