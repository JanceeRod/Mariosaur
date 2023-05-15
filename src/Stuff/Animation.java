package Stuff;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<BufferedImage> frames;
    private int index = 0;
    private int delay;
    private long prevTime;

    public Animation(int deltaTime)
    {
        this.delay = deltaTime;
        frames = new ArrayList<>();
    }

    public void update()
    {
        // update animation base on delay
        if (System.currentTimeMillis() - prevTime > delay)
        {
            index++;
            if (index >= frames.size() - 1)
            {
                index = 0;
            }
            prevTime = System.currentTimeMillis();
        }
    }

    public void addFrame(BufferedImage frame)
    {
        frames.add(frame);
    }

    public BufferedImage getFrame(int frame)
    {
        if (frames.size() > 0)
        {
            return frames.get(frame);
        }
        return null;
    }

    public BufferedImage animationFrame()
    {
        if (frames.size() > 0)
        {
            return frames.get(index);
        }
        return null;
    }
}
