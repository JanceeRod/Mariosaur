package UserInterface;

import Objects.*;
import Stuff.Resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameScreen extends JPanel implements Runnable, KeyListener
{
    public static final int startState = 0;
    public static final int runningState = 1;
    public static final int gameOver = 2;

    public static final float gravity = 0.1F;
    public static final float ground = 240;
    private final Thread thread;
    private final Mario mario;
    private final Land land;
    private final Clouds cloud;
    private final Handler enemyManager;

    private int gameState = startState;
    private int currentScore;
    private int highScore = 0;
    private final BufferedImage gameOverText;

    private boolean isMusicPlaying = false;

    public GameScreen()
    {
        thread = new Thread(this);

        mario = new Mario();
        mario.setX(50);
        mario.setY(170);

        land = new Land();
        cloud = new Clouds();
        enemyManager = new Handler(mario, this);
        gameOverText = Resource.getImage("data/mario/gameover.png");
    }

    public void startGame() { thread.start(); }

    private void playMusic()
    {
        if (isMusicPlaying)
        {
            return;
        }

        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("data/mario/marioscoresound_1.wav"));

                Clip musicClip = AudioSystem.getClip();
                musicClip.open(audioInputStream);
                musicClip.start();
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);

                isMusicPlaying = true;

                Thread.sleep(musicClip.getMicrosecondLength() / 1000);

                musicClip.stop();
                musicClip.close();
                audioInputStream.close();

                isMusicPlaying = false;
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }).start();
    }




    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                if (gameState == runningState)
                {
                    update();
                    repaint();
                }

                if (currentScore <= 150)
                {
                    Thread.sleep(6);
                }

                if (currentScore >= 151 && currentScore <= 200)
                {
                    Thread.sleep(5);
                }

                if (currentScore >= 201 && currentScore <= 250)
                {
                    Thread.sleep(4);
                }

                if (currentScore >= 251 && currentScore <= 300)
                {
                    Thread.sleep(3);
                }

                if (currentScore >= 301 && currentScore <= 400)
                {
                    Thread.sleep(2);
                }

                if (currentScore >= 401)
                {
                    Thread.sleep(2);
                }
            }

            catch (InterruptedException ex)
            {
                ex.printStackTrace();
                System.out.println("here");
            }
        }
    }

    public void update()
    {
        if (gameState == runningState) {
            mario.update();
            land.update();
            cloud.update();
            enemyManager.update();

            if (!mario.getLife()) {
                gameState = gameOver;
            }
        }
    }

    public void addScore(int score)
    {
        this.currentScore += score;
        playMusic();
    }

    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.decode("#6A8DC8"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLUE);
        g.drawLine(0, (int) ground, getWidth(), (int) ground);

        switch (gameState)
        {
            case startState -> {
                mario.drawStart(g);
                land.draw(g);
                cloud.draw(g);
                break;
            }

            case runningState -> {
                cloud.draw(g);
                land.draw(g);
                mario.draw(g);
                enemyManager.draw(g);

                g.drawString("HIGHSCORE:  " + highScore, 380, 20);
                g.drawString("SCORE:  " + currentScore, 500, 20);
            }

            case gameOver -> {
                cloud.draw(g);
                land.draw(g);
                mario.draw(g);
                enemyManager.draw(g);
                g.drawImage(gameOverText, 220, 120, null);

                if (currentScore > highScore)
                {
                    highScore = currentScore;
                }

                currentScore = 0;
            }
        }
    }

    public void restartGame()
    {
        mario.setLife(true);
        mario.setX(50);
        mario.setY(60);
        enemyManager.resetEnemies();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == startState)
            {
                gameState = runningState;
            }

            else if (gameState == runningState)
            {
                mario.jump();
            }

            else if (gameState == gameOver)
            {
                restartGame();
                gameState = runningState;
            }
        }
    }
}
