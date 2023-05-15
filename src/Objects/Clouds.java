package Objects;

import Stuff.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Clouds {

    private final BufferedImage cloud;
    private final List<moveCloud> cloudDupes;

    public Clouds()
    {
        cloud = Resource.getImage("data/mario/cloud.PNG");
        cloudDupes = new ArrayList<>();

        moveCloud cloud1 = new moveCloud();
        cloud1.posX = 100;
        cloud1.posY = 50;
        cloudDupes.add(cloud1);

        cloud1 = new moveCloud();
        cloud1.posX = 200;
        cloud1.posY = 30;
        cloudDupes.add(cloud1);

        cloud1 = new moveCloud();
        cloud1.posX = 300;
        cloud1.posY = 80;
        cloudDupes.add(cloud1);

        cloud1 = new moveCloud();
        cloud1.posX = 450;
        cloud1.posY = 50;
        cloudDupes.add(cloud1);

        cloud1 = new moveCloud();
        cloud1.posX = 600;
        cloud1.posY = 60;
        cloudDupes.add(cloud1);
    }

    public void draw(Graphics g)
    {
        for (moveCloud move: cloudDupes)
        {
            g.drawImage(cloud, (int) move.posX, (int) move.posY, null);
        }
    }

    public void update()
    {
        for (moveCloud move: cloudDupes)
        {
            move.posX-- ;
        }

        moveCloud unaCloud = cloudDupes.get(0);
        if (unaCloud.posX + cloud.getWidth() < 0)
        {
            unaCloud.posX = 600;
//            unaCloud.posX = cloudDupes.get(cloudDupes.size() - 1).posX + imageLand1.getWidth(); //put the first element sa likod sa last element
            cloudDupes.remove(0);
            cloudDupes.add(unaCloud);
        }
    }

    private class moveCloud
    {
        float posX;
        float posY;
    }
}
