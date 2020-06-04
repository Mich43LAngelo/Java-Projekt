package gameZal.game;


import javax.swing.JFrame;

public class MainWindow extends JFrame
{
    public MainWindow()
    {
        initUI();
    }

    private void initUI()
    {

        int WIDTH = 960;
        int HEIGHT = 540;

        String TITLE = "Flappy Bird";

        add(BoardFlappyBird.getInstance());//dodajemy do okna plansze -> boardflappybird (panel po ktorym rysujemy
        setTitle(TITLE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null); // center window
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); //odpala sie nasze okienko
    }
}

