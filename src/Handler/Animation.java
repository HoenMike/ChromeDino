package Handler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<BufferedImage> frames;
    private int frameIndex = 0;
    private int deltaTime;
    private long previousTime;

    // constructor
    public Animation(int deltaTime) {
        this.deltaTime = deltaTime;
        frames = new ArrayList<BufferedImage>();
    }

    public void update() {
        // update frames every deltaTime (in milliseconds)
        if (System.currentTimeMillis() - previousTime > deltaTime) {
            frameIndex++;
            if (frameIndex >= frames.size()) {
                frameIndex = 0;
            }
            previousTime = System.currentTimeMillis();
        }
    }

    public void AddFrame(BufferedImage frame) {
        frames.add(frame);
    }

    public BufferedImage getFrame() {
        if (frames.size() > 0) {
            return frames.get(frameIndex);
        }
        return null;
    }
}
