package Objects;

import Stuff.Resource;
import UserInterface.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Handler {

    private final List<BadGuy> enemies;
    private final Random randomizer;
    private final BufferedImage enemy1, enemy2;

    private final Mario mar;
    private final GameScreen screen;

    public Handler(Mario mar, GameScreen screen)
    {
        this.screen = screen;
        this.mar = mar;

        enemies = new ArrayList<>();
        enemy1 = Resource.getImage("data/mario/chomper.png");
        enemy2 = Resource.getImage("data/mario/turtle.png");
        randomizer = new Random();
        enemies.add(enemyRandomizer());
    }

    public void update()
    {
        for (BadGuy e: enemies)
        {
            e.update();

            if (e.hoppedOverKalaban() && !e.isScoreAdded())
            {
                screen.addScore(10);
                e.scoreAddedYesOrNo(true);
            }

            if (e.enemyHitbox().intersects(mar.marioBorder()))
            {
                mar.setLife(false);
            }
        }

        BadGuy firstBadGuy = enemies.get(0);
        if (firstBadGuy.isOutOfScreen())
        {
            enemies.remove(firstBadGuy);
            enemies.add(enemyRandomizer());
        }
    }

    public void draw(Graphics g)
    {
        for (BadGuy e: enemies)
        {
            e.draw(g);
        }
    }

    public void resetEnemies()
    {
        enemies.clear();
        enemies.add(enemyRandomizer());
    }

    private Kalaban enemyRandomizer()
    {
        Kalaban kalaban = new Kalaban(mar);
        kalaban.setImage(enemy1);
        kalaban.setX(600);

        if (randomizer.nextBoolean())
        {
            kalaban.setImage(enemy1);
        }
        else
        {
            kalaban.setImage(enemy2);
        }

        return kalaban;
    }

}
