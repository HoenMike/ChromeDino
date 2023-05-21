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
		setDeltaTime(deltaTime);
		setPreviousTime(0);
		setList(new ArrayList<>());
	}

	public void updateFrame() {
		if (System.currentTimeMillis() - getPreviousTime() >= getDeltaTime()) {
			addCurrentFrame(1);
			if (getCurrentFrame() >= getList().size()) {
				setCurrentFrame(0);
			}
			setPreviousTime(System.currentTimeMillis());
		}
	}

	public void addFrame(BufferedImage image) {
		getList().add(image);
	}

	private void addCurrentFrame(int index) {
		setCurrentFrame(getCurrentFrame() + index);
	}

	public BufferedImage getFrame() {
		return getList().get(getCurrentFrame());
	}

	// Getters and setters

	private int getCurrentFrame() {
		return currentFrame;
	}

	private void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	private long getDeltaTime() {
		return deltaTime;
	}

	private void setDeltaTime(long deltaTime) {
		this.deltaTime = deltaTime;
	}

	private long getPreviousTime() {
		return previousTime;
	}

	private void setPreviousTime(long previousTime) {
		this.previousTime = previousTime;
	}

	private List<BufferedImage> getList() {
		return list;
	}

	private void setList(List<BufferedImage> list) {
		this.list = list;
	}
}