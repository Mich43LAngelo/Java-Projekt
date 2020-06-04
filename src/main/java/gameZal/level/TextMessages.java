package gameZal.level;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gameZal.services.ScoreService;


public class TextMessages
{
    private static final Color COLOR = Color.BLACK;

    private static final int FONT_SIZE = 35;
    private static final int CONTROLS_MSG_Y_POS = 180;
    private static final int SCORE_MSG_X_POS = 50;
    private static final int BEST_SCORE_MSG_X_POS = 550;
    private static final int SCORE_MSG_Y_POS = 495;

    private static TextMessages instance;

    private Bird bird;

    public static TextMessages getInstance()
    {
        if (instance == null)
        {
            synchronized (TextMessages.class)
            {
                if (instance == null)
                {
                    instance = new TextMessages();
                }
            }
        }
        synchronized (TextMessages.class)
        {
            return instance;
        }
    }

    private TextMessages()
    {
        bird = Bird.getInstance();
    }

    public void paint(Graphics g, boolean isTimerRunning)
    {
        g.setColor(COLOR);
        g.setFont(new Font("Arial", 1, FONT_SIZE));
        if (!isTimerRunning)
        {
            paintControlsInfo(g);
        }
        g.drawString("Wynik: " + ScoreService.getPlayerScore(), SCORE_MSG_X_POS, SCORE_MSG_Y_POS);
        g.drawString("Najlepszy Wynik: " + ScoreService.getBestScore(), BEST_SCORE_MSG_X_POS, SCORE_MSG_Y_POS);
    }

    private void paintControlsInfo(Graphics g)
    {
        String message;
        int messageXPos;
        if (!bird.isCollided())
        {
            message = "Nacisnij klawisz Spacja by zaczac gre";
            messageXPos = 120;
        }
        else
        {
            message = "Wcisnij Enter by zagrac jeszcze raz";
            messageXPos = 130;
        }
        g.drawString(message, messageXPos, CONTROLS_MSG_Y_POS);
    }
}

