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

//represents the panel for AddNewTeamPage
public class NewTeamPanel extends JPanel {

    JFrame frame;
    private final JTextField userEntry = new JTextField();
    private final JButton submitPlayer = new JButton("Enter");
    private final JButton submitTeamName = new JButton("Enter");
    private final JButton done = new JButton("Done");
    private final Coach coach;
    private Team teamToAdd;
    private Player playerToAdd;
    private final JLabel instruction = new JLabel();
    private final JLabel submitMsg = new JLabel();

    public NewTeamPanel(JFrame frame, Coach coach) {
        this.frame = frame;
        this.coach = coach;
        userEntry.setPreferredSize(new Dimension(250,50));
        instruction.setText("Enter team name");
        add(instruction);
        add(userEntry);
        add(submitTeamName);
        add(submitMsg);
        add(done);
        done.setVisible(false);
        callButtons();
    }

    //EFFECTS: sets the action for submitTeamName button
    private void setSubmitTeamNameButton() {
        submitTeamName.addActionListener(e -> {
            teamToAdd = new Team(userEntry.getText());
            userEntry.setText("");
            try {
                coach.setChanged(true);
                coach.addTeam(teamToAdd);
                submitMsg.setText("Team name set to: " + teamToAdd.getName());
                instruction.setText("Enter player name and submit");
                remove(submitTeamName);
                remove(submitMsg);
                add(submitPlayer);
                add(submitMsg);
            } catch (SameTeamNameException erg) {
                JOptionPane.showMessageDialog(null,"A team with the same name already exists");
            }
        });
    }


    //EFFECTS: sets the actions for the submitPlayer button
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

    //EFFECTS: calls all the buttons
    private void callButtons() {
        setSubmitPlayerButton();
        setDoneButton();
        setSubmitTeamNameButton();
    }


}
