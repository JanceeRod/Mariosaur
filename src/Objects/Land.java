package Objects;

import Stuff.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static UserInterface.GameScreen.ground;

public class Land {

    private final List<ImageLand> listImage;

    private final BufferedImage imageLand1, imageLand2, imageLand3;
    private final Random randomizer;

    public Land()
    {
        randomizer = new Random();
        imageLand1 = Resource.getImage("data/mario/land.png");
        imageLand2 = Resource.getImage("data/mario/land.png");
        imageLand3 = Resource.getImage("data/mario/land.png");

        listImage = new ArrayList<>();
        int fullLand = 600 / imageLand1.getWidth() + 2;

        for (int i = 0; i < fullLand; i++)
        {
            ImageLand imageLand = new ImageLand();
            imageLand.posX = (i * imageLand1.getWidth());
            imageLand.image = randomLand();
            listImage.add(imageLand);
        }
    }

    public void update()
    {
        for (ImageLand imageLand: listImage)
        {
            imageLand.posX -= 2;
        }

        ImageLand unaLand = listImage.get(0);
        if (unaLand.posX + imageLand1.getWidth() < 0)
        {
            unaLand.posX = listImage.get(listImage.size() - 1).posX + imageLand1.getWidth(); //put the first element sa likod sa last element
            listImage.add(unaLand);
            listImage.remove(0);
        }
    }

    public void draw(Graphics g)
    {
        for (ImageLand imageLand: listImage)
        {
            g.drawImage(imageLand.image, imageLand.posX, (int) ground, null);
            //g.drawImage(ImageIO.read(new File("data/land1.png")), 50, (int) ground, null);
            //g.drawImage(land1, 50, (int) ground, null);
        }
    }

    private BufferedImage randomLand()
    {
        int count = randomizer.nextInt(4);
        return switch (count)
        {
            case 0 -> imageLand1;
            case 1 -> imageLand3;
            default -> imageLand2;
        };
    }

    private static class ImageLand
    {
        int posX;
        BufferedImage image;
    }
}
