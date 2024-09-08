package ui.panels;

import model.Coach;
import model.Player;
import model.Team;
import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import model.exceptions.SameNameException;
import model.exceptions.SameTeamNameException;
import ui.pages.TitlePage;

import javax.swing.*;
import java.awt.*;

//represents the panel for adding the first team
public class SurveyPanel extends JPanel {
    private final JTextField userEntry = new JTextField();
    private final JButton submitCoachName = new JButton("Enter");
    private final JButton submitPlayer = new JButton("Enter");
    private final JButton submitTeamName = new JButton("Enter");
    private final JButton done = new JButton("Done");
    private final Coach coach = new Coach();
    private final JLabel submitMsg = new JLabel();
    private Team teamToAdd;
    private Player playerToAdd;
    private final JLabel instruction = new JLabel();
    private final JFrame frame;

    public SurveyPanel(JFrame frame) {
        this.frame = frame;
        userEntry.setPreferredSize(new Dimension(250,50));
        instruction.setText("Enter coach name");
        add(instruction);
        add(userEntry);
        add(submitCoachName);
        add(submitMsg);
        add(done);
        callButtons();
        done.setVisible(false);
    }

    //EFFECTS: calls all the buttons
    private void callButtons() {
        setSubmitCoachButton();
        setSubmitTeamNameButton();
        setSubmitPlayerButton();
        setDoneButton();
    }

    //EFFECTS: sets the action for submitCoach button
    private void setSubmitCoachButton() {
        submitCoachName.addActionListener(e -> {
            try {
                coach.setName(userEntry.getText());
                userEntry.setText("");
                submitMsg.setText("Coaches name set to: " + coach.getName());
                instruction.setText("Enter team name");
                add(submitMsg);
                remove(submitCoachName);
                remove(submitMsg);
                add(submitTeamName);
                add(submitMsg);
            } catch (EmptyStringException arg) {
                JOptionPane.showMessageDialog(null,"Cannot accept empty name.");
            }

        });
    }

    //EFFECTS: sets the action for the submitTeamName button
    private void setSubmitTeamNameButton() {
        submitTeamName.addActionListener(e -> {
            teamToAdd = new Team(userEntry.getText());
            userEntry.setText("");
            try {
                coach.addTeam(teamToAdd);
                submitMsg.setText("Team name set to: " + teamToAdd.getName());
                instruction.setText("Enter player name and submit");
                remove(submitTeamName);
                remove(submitMsg);
                add(submitPlayer);
                add(submitMsg);
                coach.setChanged(true);
            } catch (SameTeamNameException arg) {
                JOptionPane.showMessageDialog(null,"A team with that name already exists.");
            }
        });
    }

    //EFFECTS: sets the action for the submitPlayer button
    private void setSubmitPlayerButton() {
        submitPlayer.addActionListener(e -> {
            try {
                playerToAdd = new Player(userEntry.getText());
                userEntry.setText("");
                teamToAdd.addPlayer(playerToAdd);
                submitMsg.setText(playerToAdd.getName() + " added to team " + teamToAdd.getName());
                done.setVisible(true);
            } catch (SameNameException arg) {
                JOptionPane.showMessageDialog(null,"Cannot add player with the same name.");
            } catch (MaxLengthExceededException arg) {
                JOptionPane.showMessageDialog(null,"Name is too long. Max 9 characters.");
            } catch (EmptyStringException arg) {
                JOptionPane.showMessageDialog(null,"Cannot accept empty name.");
            }
        });
    }

    //EFFECTS: sets the action for the done button
    private void setDoneButton() {
        done.addActionListener(e -> {
            frame.setVisible(false);
            new TitlePage(coach);
        });
    }
}
