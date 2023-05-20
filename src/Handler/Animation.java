package Handler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

	private int currentFrame = 0;

	private long deltaTime;
	private long previousTime;

	private List<BufferedImage> list;

	public Animation(int deltaTime) {
		this.deltaTime = deltaTime;
		previousTime = 0;
		list = new ArrayList<>();
	}

	public void updateFrame() {
		if (System.currentTimeMillis() - getPreviousTime() >= getDeltaTime()) {
			addCurrentFrame(1);
			if (getCurrentFrame() >= list.size()) {
				setCurrentFrame(0);
			}
			setPreviousTime(System.currentTimeMillis());
		}
	}

	private void setPreviousTime(long previousTime) {
		this.previousTime = previousTime;
	}

	private void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	private int getCurrentFrame() {
		return currentFrame;
	}

	private void addCurrentFrame(int index) {
		currentFrame += index;
	}

	private long getDeltaTime() {
		return deltaTime;
	}

	private long getPreviousTime() {
		return previousTime;
	}

	public void addFrame(BufferedImage image) {
		list.add(image);
	}

	public BufferedImage getFrame() {
		return list.get(currentFrame);
	}

}