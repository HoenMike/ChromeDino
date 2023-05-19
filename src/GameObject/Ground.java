package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Handler.Resource;

public class Ground {

	private static final int GROUND_Y_POS = 103;

	private List<ImageGround> listGround;
	private BufferedImage ground1;
	private BufferedImage ground2;
	private BufferedImage ground3;

	private Dino dino;

	public Ground(int width, Dino dino) {
		this.dino = dino;
		ground1 = Resource.getResourceImage("data/ground1.png");
		ground2 = Resource.getResourceImage("data/ground2.png");
		ground3 = Resource.getResourceImage("data/ground3.png");
		int numberOfImageGround = width / ground1.getWidth() + 2;
		listGround = new ArrayList<ImageGround>();
		for (int i = 0; i < numberOfImageGround; i++) {
			ImageGround imageGround = new ImageGround();
			imageGround.setXPosition(i * ground1.getWidth());
			setImageGround(imageGround);
			listGround.add(imageGround);
		}
	}

	public void update() {
		Iterator<ImageGround> itr = listGround.iterator();
		ImageGround firstGround = itr.next();
		firstGround.setXPosition(firstGround.getXPosition() - dino.getDinoSpeedX());
		float previousPosX = firstGround.getXPosition();
		while (itr.hasNext()) {
			ImageGround element = itr.next();
			element.setXPosition(previousPosX + ground1.getWidth());
			previousPosX = element.getXPosition();
		}
		if (firstGround.getXPosition() < -ground1.getWidth()) {
			listGround.remove(firstGround);
			firstGround.setXPosition(previousPosX + ground1.getWidth());
			setImageGround(firstGround);
			listGround.add(firstGround);
		}
	}

	public void draw(Graphics g) {
		for (ImageGround imgGround : listGround) {
			g.drawImage(imgGround.getImage(), (int) imgGround.getXPosition(), GROUND_Y_POS, null);
		}
	}

	private void setImageGround(ImageGround imgGround) {
		int typeGround = getTypeOfGround();
		if (typeGround == 1) {
			imgGround.setImage(ground1);
		} else if (typeGround == 3) {
			imgGround.setImage(ground3);
		} else {
			imgGround.setImage(ground2);
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

		public float getXPosition() {
			return xPosition;
		}

		public void setXPosition(float xPosition) {
			this.xPosition = xPosition;
		}

		public BufferedImage getImage() {
			return image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}
	}
}
