package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Handler.Resource;

public class Ground extends GameObj {

	private static final int GROUND_Y_POS = 103;

	private List<ImageGround> listGround;
	private BufferedImage ground1;
	private BufferedImage ground2;
	private BufferedImage ground3;


	public Ground(int width, Dino dino) {
		setDino(dino);
		setGround1(Resource.getResourceImage("data/ground1.png"));
		setGround2(Resource.getResourceImage("data/ground2.png"));
		setGround3(Resource.getResourceImage("data/ground3.png"));
		int numberOfImageGround = width / getGround1().getWidth() + 2;
		setListGrounds(new ArrayList<>());
		for (int i = 0; i < numberOfImageGround; i++) {
			ImageGround imageGround = new ImageGround();
			imageGround.setXPosition(i * getGround1().getWidth());
			setImageGround(imageGround);
			getListGrounds().add(imageGround);
		}
	}

	public void update() {
		Iterator<ImageGround> itr = getListGrounds().iterator();
		ImageGround firstGround = itr.next();
		firstGround.setXPosition(firstGround.getXPosition() - getDino().getDinoSpeedX());
		float previousPosX = firstGround.getXPosition();
		while (itr.hasNext()) {
			ImageGround element = itr.next();
			element.setXPosition(previousPosX + getGround1().getWidth());
			previousPosX = element.getXPosition();
		}
		if (firstGround.getXPosition() < -getGround1().getWidth()) {
			getListGrounds().remove(firstGround);
			firstGround.setXPosition(previousPosX + getGround1().getWidth());
			setImageGround(firstGround);
			getListGrounds().add(firstGround);
		}
	}

	public void draw(Graphics g) {
		for (ImageGround imgGround : getListGrounds()) {
			g.drawImage(imgGround.getImage(), (int) imgGround.getXPosition(), GROUND_Y_POS, null);
		}
	}

	private void setImageGround(ImageGround imgGround) {
		int typeGround = getTypeOfGround();
		if (typeGround == 1) {
			imgGround.setImage(getGround1());
		} else if (typeGround == 3) {
			imgGround.setImage(getGround3());
		} else {
			imgGround.setImage(getGround2());
		}
	}

	private int getTypeOfGround() {
		Random rand = new Random();
		int type = rand.nextInt(10);
		if (type == 1) {
			return 1;
		} else if (type == 9) {
			return 2;
		} else {
			return 3;
		}
	}

	private class ImageGround {
		private float xPosition;
		private BufferedImage image;

		private float getXPosition() {
			return xPosition;
		}

		private void setXPosition(float xPosition) {
			this.xPosition = xPosition;
		}

		private BufferedImage getImage() {
			return image;
		}

		private void setImage(BufferedImage image) {
			this.image = image;
		}
	}

	public Rectangle getCollision() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCollision'");
	}

	public boolean isOutOfScreen() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isOutOfScreen'");
	}

	// Getters and setters
	
	private List<ImageGround> getListGrounds() {
		return listGround;
	}

	private void setListGrounds(List<ImageGround> listGround) {
		this.listGround = listGround;
	}

	private BufferedImage getGround1() {
		return ground1;
	}

	private void setGround1(BufferedImage ground1) {
		this.ground1 = ground1;
	}

	private BufferedImage getGround2() {
		return ground2;
	}

	private void setGround2(BufferedImage ground2) {
		this.ground2 = ground2;
	}

	private BufferedImage getGround3() {
		return ground3;
	}

	private void setGround3(BufferedImage ground3) {
		this.ground3 = ground3;
	}
}