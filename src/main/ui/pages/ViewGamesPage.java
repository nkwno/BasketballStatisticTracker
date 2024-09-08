package ui.pages;

import model.Coach;
import model.EventLog;
import model.Game;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

//represents the page where a user can view all the games played for a selected team
public class ViewGamesPage extends JFrame {

    private final Team team;
    private final List<Game> gamesList;
    private final List<JButton> gameButtons = new ArrayList<>();

    public ViewGamesPage(Coach coach, Team team) {
        super("StatTracker");
        setSize(new Dimension(700, 400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        this.team = team;
        gamesList = team.getListOfGames();
        gameStatToString();
        JLabel title = new JLabel("Select a game to view statistics");
        add(title);
        addButtons();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }

    //EFFECTS: converts the game to String to use in JTable
    private void gameStatToString() {
        for (Game g : gamesList) {
            gameButtons.add(new JButton(team.getName() + " vs. " + g.getOpponentName()) {
                {
                    addActionListener(e -> new GameStatPage(g));
                }
            });
        }
    }

    //EFFECTS: adds buttons to the page
    private void addButtons() {
        for (JButton b : gameButtons) {
            add(b);
        }
    }

}
