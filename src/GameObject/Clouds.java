package GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Handler.Resource;
import UserInterface.GameWindow;
 
public class Clouds extends GameObj {

	private List<ImageCloud> listCloud;
	private BufferedImage cloud;

	public Clouds(Dino dino) {
		setDino(dino);
		setCloud(Resource.getResourceImage("data/cloud.png"));
		setListCloud(new ArrayList<>());

		ImageCloud imageCloud = new ImageCloud();
		imageCloud.setPosX(0);
		imageCloud.setPosY(30);
		getListCloud().add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(150);
		imageCloud.setPosY(40);
		getListCloud().add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(300);
		imageCloud.setPosY(50);
		getListCloud().add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(450);
		imageCloud.setPosY(20);
		getListCloud().add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.setPosX(600);
		imageCloud.setPosY(60);
		getListCloud().add(imageCloud);
	}

	// Updates the positions of clouds based on dino's speed
	public void update() {
		Iterator<ImageCloud> itr = getListCloud().iterator();
		ImageCloud firstElement = itr.next();
		firstElement.setPosX(firstElement.getPosX() - getDino().getDinoSpeedX() / 8);
		while (itr.hasNext()) {
			ImageCloud element = itr.next();
			element.setPosX(element.getPosX() - getDino().getDinoSpeedX() / 8);
		}
		if (firstElement.getPosX() < -getCloud().getWidth()) {
			getListCloud().remove(firstElement);
			firstElement.setPosX(GameWindow.SCREEN_WIDTH);
			getListCloud().add(firstElement);
		}
	}

	// Draws the clouds on the screen
	public void draw(Graphics g) {
		for (ImageCloud imgLand : getListCloud()) {
			g.drawImage(getCloud(), (int) imgLand.getPosX(), imgLand.getPosY(), null);
		}
	}

	// Represents a single cloud image with its position
	private class ImageCloud {
		private float posX;
		private int posY;

		private float getPosX() {
			return posX;
		}

		private void setPosX(float posX) {
			this.posX = posX;
		}

		private int getPosY() {
			return posY;
		}

		private void setPosY(int posY) {
			this.posY = posY;
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
	
	private List<ImageCloud> getListCloud() {
		return listCloud;
	}

	private void setListCloud(List<ImageCloud> listCloud) {
		this.listCloud = listCloud;
	}

	private BufferedImage getCloud() {
		return cloud;
	}

	private void setCloud(BufferedImage cloud) {
		this.cloud = cloud;
	}
}
