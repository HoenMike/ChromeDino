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
		list = new ArrayList<BufferedImage>();
	}

	public void updateFrame() {
		if (System.currentTimeMillis() - previousTime >= deltaTime) {
			currentFrame++;
			if (currentFrame >= list.size()) {
				currentFrame = 0;
			}
			previousTime = System.currentTimeMillis();
		}
	}

	public void addFrame(BufferedImage image) {
		list.add(image);
	}

	public BufferedImage getFrame() {
		return list.get(currentFrame);
	}

}
