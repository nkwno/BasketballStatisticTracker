package ui.panels;

import model.Coach;
import model.Team;
import model.exceptions.TeamDoesNotExistException;
import org.json.JSONException;
import persistence.JsonWriter;
import ui.pages.AddNewTeamPage;
import ui.pages.TeamPage;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

//represents the panel for the title page
public class TitlePanel extends JPanel {

    private final JButton addTeam = new JButton("Add new team");
    private final JButton save = new JButton("Save");
    private final JFrame frame;
    private final Coach coach;
    private final JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/app.json";
    private final List<JButton> listOfButtons = new ArrayList<>();


    public TitlePanel(Coach coach, JFrame frame) {
        this.frame = frame;
        this.coach = coach;
        jsonWriter = new JsonWriter(JSON_STORE);
        designLayout();

        add(addTeam);
        if (coach.getChanged()) {
            add(save);
        }
        setAddTeamButton();
        setTeamButton(listOfButtons);
        setSaveTeamButton();
    }

    //EFFECTS: sets the layout of the panel
    //MODIFIES: this
    private void designLayout() {
        try {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(Box.createRigidArea(new Dimension(0,10)));
            JLabel selectTeam = new JLabel("Select a team to interact with");
            selectTeam.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            add(selectTeam);
            add(Box.createRigidArea(new Dimension(0,10)));
            Panel buttonPanel = new Panel();
            List<Team> listOfTeam = coach.getListOfTeam();
            buttonPanel(listOfTeam, buttonPanel);
            JScrollPane teamScrollPane = new JScrollPane(buttonPanel);
            teamScrollPane.setPreferredSize(new Dimension(150,50));
            add(teamScrollPane);

        } catch (TeamDoesNotExistException e) {
            JOptionPane.showMessageDialog(null,"You have no teams.");
        }
    }

    //EFFECTS: prints out the buttonPanel
    //MODIFIES: this
    private void buttonPanel(List<Team> listOfTeam, Panel buttonPanel) {
        for (Team team : listOfTeam) {
            JButton teamButton = new JButton(team.getName());
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            buttonPanel.add(teamButton);
            listOfButtons.add(teamButton);
        }
    }

    //EFFECTS: set the team button onclick actions
    //MODIFIES: this
    private void setTeamButton(List<JButton> buttonPanel) {
        for (JButton b : buttonPanel) {
            b.addActionListener(e -> {
                frame.setVisible(false);
                new TeamPage(coach, b.getText());
            });
        }
    }

    //EFFECTS: sets the action when add team button is clicked
    //MODIFIES: this
    private void setAddTeamButton() {
        addTeam.addActionListener(e -> {
            frame.setVisible(false);
            new AddNewTeamPage(coach);
        });
    }

    //EFFECTS: sets the action for the save button
    //MODIFIES: this
    private void setSaveTeamButton() {
        save.addActionListener(e -> {
            try {
                jsonWriter.open();
                jsonWriter.write(coach);
                jsonWriter.close();
                add(new JLabel("Saved user " + coach.getName() + " to file " + JSON_STORE));
                revalidate();
            } catch (FileNotFoundException arg) {
                System.out.println("Unable to write to file: " + JSON_STORE);
                revalidate();
            } catch (JSONException arg) {
                add(new JLabel("Unable to save data."));
                revalidate();
            }
        });
    }
}
