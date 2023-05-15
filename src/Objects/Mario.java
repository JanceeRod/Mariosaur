package Objects;

import Stuff.Animation;
import Stuff.Resource;

import java.awt.*;

import static UserInterface.GameScreen.gravity;
import static UserInterface.GameScreen.ground;

public class Mario {
    private float x = 0;
    private float y = 0;
    private float YDirection = 0;
    private final Animation marioRun;
    private final Rectangle marioHitbox;
    private boolean isAlive = true;

    public Mario()
    {
        marioRun = new Animation(100);
        marioRun.addFrame(Resource.getImage("data/mario/mario1.png"));
        marioRun.addFrame(Resource.getImage("data/mario/mario2.png"));
        marioRun.addFrame(Resource.getImage("data/mario/mario4.png"));
        marioHitbox = new Rectangle();
    }

    public void update()
    {
        marioRun.update();

        if(y >= ground - marioRun.getFrame(0).getHeight())
        {
            YDirection = 0;
            y = ground - marioRun.getFrame(0).getHeight();
        }
        else
        {
            YDirection += gravity;
            y += YDirection;
        }

        marioHitbox.x = (int) x;
        marioHitbox.y = (int) y;
        marioHitbox.width = marioRun.getFrame(0).getWidth();
        marioHitbox.height = marioRun.getFrame(0).getHeight();
    }

    public Rectangle marioBorder()
    {
        return marioHitbox;
    }


    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
//        g.drawRect((int) x, (int) y, dinosaurRun.getFrame().getWidth(), dinosaurRun.getFrame().getHeight());
        g.drawImage(marioRun.animationFrame(), (int) x, (int) y, null);
    }

    public void drawStart(Graphics g)
    {
        g.drawImage(Resource.getImage("data/mario/mario3.png"), (int) x, (int) y, null);
    }

    public void jump()
    {
        YDirection = -5;
        y += YDirection;
    }

    public void setLife(boolean alive)
    {
        isAlive = alive;
    }

    public boolean getLife()
    {
        return isAlive;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() { return y; }

    public void setY(float y) {
        this.y = y;
    }

    public float getYDirection() {
        return YDirection;
    }

    public void setYDirection(float YDirection) {
        this.YDirection = YDirection;
    }
}
