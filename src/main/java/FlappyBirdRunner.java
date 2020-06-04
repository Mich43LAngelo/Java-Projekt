import java.awt.EventQueue;
import gameZal.game.MainWindow;

public class FlappyBirdRunner
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> new MainWindow());
    }

    // EventQueue.invokeLater(() tworzymy nowe okno bo moze sie wysypac

}
