package Objects;

import Stuff.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Kalaban extends BadGuy {

    private BufferedImage enemyImage;
    private int posX;
    private int posY;
    private final Rectangle hitbox;

    private Mario mar;
    private boolean isScoreAdded = false;

    public Kalaban(Mario mar)
    {
        this.mar = mar;
        enemyImage = Resource.getImage("data/mario/chomper.png");
        posX = 200;
        posY = 190;
        hitbox = new Rectangle();
    }

    public void update()
    {
        posX-= 2;
        hitbox.x = posX;
        hitbox.y = posY;
        hitbox.width = enemyImage.getWidth();
        hitbox.height = enemyImage.getHeight();
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(enemyImage, posX, posY, null);
    }

    @Override
    public Rectangle enemyHitbox()
    {
        return hitbox;
    }

    @Override
    public boolean isOutOfScreen()
    {
        return (posX + enemyImage.getWidth() <  0);
    }

    @Override
    public boolean hoppedOverKalaban()
    {
        return  (mar.getX() > posX);
    }

    @Override
    public boolean isScoreAdded()
    {
        return isScoreAdded;
    }

    @Override
    public void scoreAddedYesOrNo(boolean isScoreAdded)
    {
        this.isScoreAdded = isScoreAdded;
    }


    public void setX(int x) { posX = x; }
    public void setY(int y) { posY = y; }
    public void setImage(BufferedImage img) { this.enemyImage = img; }
}
