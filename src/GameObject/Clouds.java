package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Handler.Resource;
import UserInterface.GameWindow;

public class Clouds {
	private List<ImageCloud> listCloud;
	private BufferedImage cloud;

	private Dino dino;

	public Clouds(Dino dino) {
		this.dino = dino;
		cloud = Resource.getResourceImage("data/cloud.png");
		listCloud = new ArrayList<>();

		ImageCloud imageCloud = new ImageCloud();
		imageCloud.setPosX(0);
		imageCloud.setPosY(30);
		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(150);
		imageCloud.setPosY(40);
		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(300);
		imageCloud.setPosY(50);
		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(450);
		imageCloud.setPosY(20);
		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(600);
		imageCloud.setPosY(60);
		listCloud.add(imageCloud);
	}

	// Updates the positions of clouds based on dino's speed
	public void update() {
		Iterator<ImageCloud> itr = listCloud.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.setPosX(firstElement.getPosX() - dino.getDinoSpeed() / 8);
		while (itr.hasNext()) {
			ImageCloud element = itr.next();
			element.setPosX(element.getPosX() - dino.getDinoSpeed() / 8);
		}
		if (firstElement.getPosX() < -cloud.getWidth()) {
			listCloud.remove(firstElement);
			firstElement.setPosX(GameWindow.SCREEN_WIDTH);
			listCloud.add(firstElement);
		}
	}

	// Draws the clouds on the screen
	public void draw(Graphics g) {
		for (ImageCloud imgLand : listCloud) {
			g.drawImage(cloud, (int) imgLand.getPosX(), imgLand.getPosY(), null);
		}
	}

	// Represents a single cloud image with its position
	private class ImageCloud {
		private float posX;
		private int posY;

		public float getPosX() {
			return posX;
		}

		public void setPosX(float posX) {
			this.posX = posX;
		}

		public int getPosY() {
			return posY;
		}

		public void setPosY(int posY) {
			this.posY = posY;
		}
	}
}
