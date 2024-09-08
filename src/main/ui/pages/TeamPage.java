package ui.pages;

import model.Coach;
import model.EventLog;
import model.Team;
import model.exceptions.TeamDoesNotExistException;
import ui.panels.TeamPagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//represents the team page when you click on a team
public class TeamPage extends JFrame {

    private Team team;

    public TeamPage(Coach coach, String teamName) {
        getTeam(coach, teamName);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,30,40);
        addTeamName(gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new TeamPagePanel(coach, team, this), gbc);
        setSize(new Dimension(700,400));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coach.printEvents(EventLog.getInstance());
                System.exit(0);
            }
        });
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setVisible(true);
    }

    //EFFECTS: adds team name JLabel to frame
    private void addTeamName(GridBagConstraints gbc) {
        JLabel teamName = new JLabel(team.getName());
        teamName.setFont(new Font("Sans Serif", Font.BOLD, 25));
        JPanel title = new JPanel();
        title.add(teamName);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(title, gbc);
    }

    private void getTeam(Coach coach, String teamName) {
        try {
            for (Team t : coach.getListOfTeam()) {
                if (t.getName().equals(teamName)) {
                    this.team = t;
                    break;
                }
            }
        } catch (TeamDoesNotExistException e) {
            JOptionPane.showMessageDialog(null,"Coach does not have this team.");
        }
    }
}
