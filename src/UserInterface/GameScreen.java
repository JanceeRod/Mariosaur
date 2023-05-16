package UserInterface;

import Objects.*;
import Stuff.Resource;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameScreen extends JPanel implements Runnable, KeyListener
{
    public static final int startState = 0;
    public static final int runningState = 1;
    public static final int gameOver = 2;
    public static final float gravity = 0.1F;
    public static final float ground = 240;

    private final Thread thread;
    private Thread audioThread;
    private boolean audioPlaying;


    private final Mario mario;
    private final Land land;
    private final Clouds cloud;
    private final Handler enemyManager;

    private int gameState = startState;
    private int currentScore;
    private int highScore = 0;
    private final BufferedImage gameOverText;


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

//    private void playBackgroundAudio(String audioFilePath)
//    {
//        audioThread = new Thread(() ->
//        {
//            try
//            {
//                File audioFile = new File(audioFilePath);
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
//                AudioFormat format = audioInputStream.getFormat();
//                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//                SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
//                audioLine.open(format);
//                audioLine.start();
//
//                int bufferSize = 1024;
//                byte[] buffer = new byte[bufferSize];
//                int bytesRead;
//
//                audioPlaying = true;
//
//                while (!Thread.currentThread().isInterrupted() && audioPlaying)
//                {
//                    while ((bytesRead = audioInputStream.read(buffer, 0, bufferSize)) != -1)
//                    {
//                        audioLine.write(buffer, 0, bytesRead);
//                    }
//                    audioInputStream.reset();  // Reset to the beginning of the audio file
//                }
//
//                audioLine.drain();
//                audioLine.stop();
//                audioLine.close();
//                audioInputStream.close();
//            }
//
//            catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
//            {
//                System.out.println("Error playing background audio: " + e.getMessage());
//            }
//        });
//        audioThread.start();
//    }

//    public void stopBackgroundAudio()
//    {
//        if (audioPlaying)
//        {
//            audioPlaying = false;
//            if (audioThread != null && audioThread.isAlive())
//            {
//                audioThread.interrupt();
//                audioThread = null;
//            }
//        }
//    }

    public void startGame()
    {
        thread.start();
//        playBackgroundAudio("data/mario/bgm.wav");
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (gameState == runningState)
            {
                update();
                repaint();
                try {
                    if (currentScore <= 150) {
                        Thread.sleep(5);
                    }

                    if (currentScore >= 151 && currentScore <= 300) {
                        Thread.sleep(3);
                    }

                    if (currentScore >= 301) {
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
//                stopBackgroundAudio();
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
