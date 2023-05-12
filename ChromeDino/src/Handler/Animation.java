package Handler;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<BufferedImage> frames;

    public Animation() {
        frames = new ArrayList<BufferedImage>();
    }

    public void update() {

    }

    public void AddFrame(BufferedImage frame) {
        frames.add(frame);
    }

    public BufferedImage getFrame() {
        if (frames.size() > 0) {
            return frames.get(0);
        }
        return null;
    }
}
