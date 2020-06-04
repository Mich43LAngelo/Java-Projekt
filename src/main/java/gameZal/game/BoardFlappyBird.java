package gameZal.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameZal.level.Bird;
import gameZal.level.Pipes;
import gameZal.services.CollisionService;
import gameZal.level.Ground;
import gameZal.level.TextMessages;
import gameZal.services.ScoreService;

public class BoardFlappyBird extends JPanel implements ActionListener
{
    private static BoardFlappyBird instance;

    private Timer timer; // generuje wydarzenia (ruch)
    private Bird bird;
    private Pipes pipes;
    private Ground ground;
    private TextMessages textMessages;

    // singleton - wzorzec projektory ze mozemy utworzyc tylko jeden obiekt

    public static BoardFlappyBird getInstance()
    {
        if (instance == null)
        {
            synchronized (BoardFlappyBird.class)
            {
                if (instance == null)
                {
                    instance = new BoardFlappyBird();
                }
            }
        }
        synchronized (BoardFlappyBird.class)
        {
            return instance;
        }
    }

    private BoardFlappyBird()
    {
        initBoard();
    }

    private void initBoard()
    {
        setBackground(Color.GRAY.darker());
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
        timer = new Timer(20, this);
        bird = Bird.getInstance();
        pipes = Pipes.getInstance();
        ground = Ground.getInstance();
        textMessages = TextMessages.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) // metoda z ActionListener wywolana za kazdym razem zdarzenia, glowna metoda gry.
    {
        ScoreService.examineBirdPositionBeforePipesUpdate(); //pozycja ptaka
        pipes.update();
        ScoreService.examineBirdPositionAfterPipesUpdate();

        if (ScoreService.birdPassedFrontPipes()) // sprawdzam czy przekroczyl ptaszek rure
        {
            ScoreService.scorePlayer();
        }

        bird.update(); //update ruchu ptaszka
        CollisionService.resolveCollision();

        if (bird.isCollided()) ///sprawdzamy kolizje
        {
            ScoreService.updateBestScore();
            timer.stop();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) // rysuje nam nowa klatke wywolywana przez repaint
    {
        super.paintComponent(g);
        revalidate();
        ground.paint(g);
        bird.paint(g);
        pipes.paint(g);
        textMessages.paint(g, timer.isRunning());
    }

    private class MyKeyAdapter extends KeyAdapter //obsluguje sterowanie klawiatura
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            boolean isTimerRunning = timer.isRunning();
            int key = e.getKeyCode();
            if (isTimerRunning)
            {
                bird.keyPressed(e);
            }
            else if (bird.isCollided() && key == KeyEvent.VK_ENTER)
            {
                resetGame();
                repaint();
            }
            else if (!bird.isCollided() && key == KeyEvent.VK_SPACE)
            {
                timer.start();
                bird.keyPressed(e);
            }
        }
    }

    private void resetGame()
    {
        bird.reset();
        pipes.reset();
        ScoreService.reset();
    }
}
