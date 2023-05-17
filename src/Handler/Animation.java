package Handler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<BufferedImage> frames;
    private int frameIndex = 0;
    private long deltaTime;
    private long previousTime = 0;

    // constructor
    public Animation(long deltaTime) {
        this.deltaTime = deltaTime;
        frames = new ArrayList<>();
    }

    public void update() {

        int frameIndex1 = getFrameIndex();
        // update frames every deltaTime (in milliseconds)
        if (System.currentTimeMillis() - getPreviousTime() >= getDeltaTime()) {
            frameIndex1++;
            if (frameIndex1 >= frames.size()) {
                setFrameIndex(0);
            }
            setPreviousTime(System.currentTimeMillis());
        }
    }

    public void addFrame(BufferedImage frame) {
        frames.add(frame);
    }

    public BufferedImage getFrame() {
        return frames.get(getFrameIndex());
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public long getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(long previousTime) {
        this.previousTime = previousTime;
    }
}
