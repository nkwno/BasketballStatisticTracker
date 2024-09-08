package ui.panels;

import model.Coach;
import model.Player;
import model.Team;
import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import model.exceptions.SameNameException;
import ui.pages.*;

import javax.swing.*;

//represents the panel gor the TeamPage
public class TeamPagePanel extends JPanel {

    private final JButton newGame = new JButton("New Game");
    private final JButton viewGames = new JButton("View Games Played");
    private final JButton viewTeamStats = new JButton("View Team Statistics");
    private final JButton addNewPlayer = new JButton("Add New Player");
    private final JButton backToTitlePage = new JButton("Back to Title Page");
    private final Team team;
    private final Coach coach;
    private final JFrame frame;
    private final JLabel enterName = new JLabel("Enter Player Name");
    private final JTextField name = new JTextField("");
    private final JButton submit = new JButton("Submit");

    public TeamPagePanel(Coach coach, Team team, JFrame frame) {
        this.team = team;
        this.coach = coach;
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(newGame);
        add(viewGames);
        add(viewTeamStats);
        add(addNewPlayer);
        add(backToTitlePage);
        viewGameButton();
        callButtons();
    }

    //EFFECTS: calls all the buttons
    private void callButtons() {
        setViewTeamButton();
        setNewGameButton();
        setAddNewPlayerButton();
        setBackToTitlePageButton();
        setSubmitButton();
    }

    //EFFECTS: adds the action for the viewTeam button
    private void setViewTeamButton() {
        viewTeamStats.addActionListener(e -> new TeamStatPage(team));
    }

    //EFFECTS: adds the action for newGame button
    private void setNewGameButton() {
        newGame.addActionListener(e -> {
            new NewGamePage(coach, team);
            frame.setVisible(false);
        });
    }

    //EFFECTS: adds the action for addNewPlayer button
    private void setBackToTitlePageButton() {
        backToTitlePage.addActionListener(e -> {
            new TitlePage(coach);
            frame.setVisible(false);
        });
    }

    //EFFECTS: adds the action for addNewPlayer button
    private void setAddNewPlayerButton() {
        addNewPlayer.addActionListener(e -> {
            add(enterName);
            add(name);
            add(submit);
            revalidate();
        });
    }


    //EFFECTS: adds the action for submit button
    private void setSubmitButton() {
        submit.addActionListener(e -> {
            try {
                coach.setChanged(true);
                Player toAdd = new Player(name.getText());
                name.setText("");
                team.addPlayer(toAdd);
                JLabel label = new JLabel("Added player: " + toAdd.getName() + " to this team.");
                add(label);
                revalidate();
            } catch (SameNameException ex) {
                JOptionPane.showMessageDialog(null,"Cannot add player with the same name.");
            } catch (MaxLengthExceededException ex) {
                JOptionPane.showMessageDialog(null,"Max length of 9 exceeded. Try again.");
            } catch (EmptyStringException ex) {
                JOptionPane.showMessageDialog(null,"Cannot accept empty spaces.");
            }
        });
    }

    //EFFECTS: adds the action for viewGames button
    private void viewGameButton() {
        viewGames.addActionListener(e -> {
            new ViewGamesPage(coach, team);
            frame.setVisible(false);
        });
    }

}


