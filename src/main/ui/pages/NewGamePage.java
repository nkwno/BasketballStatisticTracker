package ui.pages;

import model.Coach;
import model.EventLog;
import model.Team;
import ui.panels.NewGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//represents the page where the user starts a new game
public class NewGamePage extends JFrame {

    public NewGamePage(Coach coach, Team team) {
        super("StatTracker");
        setSize(new Dimension(700,400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        add(new NewGamePanel(coach, team, this), gbc);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }
}
