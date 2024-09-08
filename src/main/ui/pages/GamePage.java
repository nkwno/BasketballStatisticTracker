package ui.pages;

import model.Coach;
import model.EventLog;
import model.Game;
import ui.panels.GamePagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//represents a page when the game starts
public class GamePage extends JFrame {

    private final Coach coach;
    private final Game game;

    public GamePage(Coach coach, Game game) {
        super("StatTracker");
        this.coach = coach;
        this.game = game;

        setSize(new Dimension(700,400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        addPanels();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }

    //EFFECTS: sets the layout and adds GamePagePanel to frame
    private void addPanels() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10,30,10,30);
        add(new GamePagePanel(coach, game, this), gbc);
        gbc.gridx = 1;
    }





}
