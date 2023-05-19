package GameObject;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import Handler.Animation;
import Handler.Resource;

public class Dino {

	public static final int LAND_Y_POSITION = 80;
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
		dinoYPosition = LAND_Y_POSITION;
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
		switch (state) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnimation.getFrame(), (int) dinoXPosition, (int) dinoYPosition, null);
				break;
			case JUMPING:
				g.drawImage(jumping, (int) dinoXPosition, (int) dinoYPosition, null);
				break;
			case CROUCH_RUN:
				g.drawImage(crouchRunAnimation.getFrame(), (int) dinoXPosition, (int) (dinoYPosition + 20), null);
				break;
			case DEATH:
				g.drawImage(dead, (int) dinoXPosition, (int) dinoYPosition, null);
				break;
		}
		Rectangle bound = getDinoCollisionShape();
		g.setColor(Color.RED);
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}

	public void update() {
		normalRunAnimation.updateFrame();
		crouchRunAnimation.updateFrame();
		if (dinoYPosition >= LAND_Y_POSITION) {
			dinoYPosition = LAND_Y_POSITION;
			if (state != CROUCH_RUN) {
				state = NORMAL_RUN;
			}
		} else {
			dinoSpeedY += GRAVITY;
			dinoYPosition += dinoSpeedY;
		}
	}

	public void jump() {
		if (dinoYPosition >= LAND_Y_POSITION) {
			if (jumpSound != null) {
				jumpSound.play();
			}
			dinoSpeedY = -7.5f;
			dinoYPosition += dinoSpeedY;
			state = JUMPING;
		}
	}

	public void crouch(boolean isCrouch) {
		if (state == JUMPING) {
			return;
		}
		if (isCrouch) {
			state = CROUCH_RUN;
		} else {
			state = NORMAL_RUN;
		}
	}

	public Rectangle getDinoCollisionShape() {
		dinoCollisionShape = new Rectangle();
		if (state == CROUCH_RUN) {
			dinoCollisionShape.x = (int) dinoXPosition + 5;
			dinoCollisionShape.y = (int) dinoYPosition + 20;
			dinoCollisionShape.width = crouchRunAnimation.getFrame().getWidth() - 10;
			dinoCollisionShape.height = crouchRunAnimation.getFrame().getHeight();
		} else {
			dinoCollisionShape.x = (int) dinoXPosition + 5;
			dinoCollisionShape.y = (int) dinoYPosition;
			dinoCollisionShape.width = normalRunAnimation.getFrame().getWidth() - 10;
			dinoCollisionShape.height = normalRunAnimation.getFrame().getHeight();
		}
		return dinoCollisionShape;
	}

	public void dead(boolean isDeath) {
		if (isDeath) {
			state = DEATH;
			score = 0;
		} else {
			state = NORMAL_RUN;
		}
	}

	public void playDeadSound() {
		deadSound.play();
	}

	public void reset() {
		dinoYPosition = LAND_Y_POSITION;
	}

	public void upScore() {
		score += 20;
		if (score >= highScore) {
			highScore = score;
		}
		if (score % 100 == 0) {
			scoreUpSound.play();
		}
	}

	public int getScore() {
		return score;
	}

	public int getHighScore() {
		return highScore;
	}

	public float getDinoSpeedX() {
		return dinoSpeedX;
	}

	public void setSpeedX(int speedX) {
		this.dinoSpeedX = speedX;
	}

}
