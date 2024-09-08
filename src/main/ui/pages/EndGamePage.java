package ui.pages;

import model.Coach;
import model.EventLog;
import model.Game;
import ui.panels.EndGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


//represents the page when the user ends a game
public class EndGamePage extends JFrame {

    public EndGamePage(Coach coach, Game game) {
        super("StatTracker");
        setSize(new Dimension(700,400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        add(new EndGamePanel(coach, game, this));
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }
}
