package UserInterface;

import javax.swing.*;

public class GameWindow extends JFrame
{
    private final GameScreen gameScreen = new GameScreen();

    public GameWindow()
    {
        super("Mariosaur");
        setSize(600, 300);
        setLocation(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(gameScreen);
        addKeyListener(gameScreen);
    }

    public void startGame()
    {
        gameScreen.startGame();
    }

    public static void main(String[] args)
    {
        GameWindow window = new GameWindow();
        window.setVisible(true);
        window.startGame();
    }
}