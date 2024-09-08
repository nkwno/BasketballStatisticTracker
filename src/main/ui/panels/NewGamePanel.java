package ui.panels;

import model.Coach;
import model.Game;
import model.Player;
import ui.pages.GamePage;
import model.Team;
import javax.swing.*;
import java.awt.*;
import java.util.List;


//represents the panel for NewGamePage
public class NewGamePanel extends JPanel {

    private JList playerNames;
    private final JButton submitPlayers = new JButton("submit");
    private final JButton submitOpponent = new JButton("submit");
    private final JTextField opponentField = new JTextField(10);
    private final JLabel opLabel = new JLabel("Enter opponent name.");
    private final List<Player> players;
    private final Game game;
    private final Coach coach;
    private final JFrame frame;

    public NewGamePanel(Coach coach, Team team, JFrame frame) {
        this.coach = coach;
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        String opponent = getOpponent();
        game = new Game(opponent);
        team.addGame(game);
        coach.setChanged(true);
        players = team.getListOfPlayers();
        playerNames = getPlayerNames(players);
        playerNames.setFixedCellHeight(20);
        JLabel choosePlayer = new JLabel("Choose players playing in the game.");
        choosePlayer.setAlignmentX(0f);
        playerNames.setAlignmentX(0f);
        submitPlayers.setAlignmentX(0f);
        add(choosePlayer);
        add(Box.createVerticalStrut(25));
        add(playerNames);
        add(Box.createVerticalStrut(10));
        add(submitPlayers);
        add(Box.createVerticalStrut(25));
        setSubmitPlayersButton();
        submitOpponent();

    }

    //EFFECTS: creates the JList of players and makes it multi selectable
    private JList getPlayerNames(List<Player> players) {
        String[] names = new String[players.size()];
        int count = 0;
        for (Player p : players) {
            names[count] = p.getName();
            count++;
        }
        playerNames = new JList(names);

        //from https://stackoverflow.com/questions/2404546/select-multiple-items-in-jlist-without-using-the-ctrl-command-key
        playerNames.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        }
        );
        return playerNames;
    }

    //EFFECTS: sets the action for submitPlayers button
    private void setSubmitPlayersButton() {
        submitPlayers.addActionListener(e -> {
            int[] selected = playerNames.getSelectedIndices();
            if (game.getOpponentName().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,"You must submit opponent name first.");
            } else if (selected.length == 0) {
                JOptionPane.showMessageDialog(null,"You must select a player.");
            } else {

                for (int i : selected) {
                    game.addPlayer(players.get(i));
                }
                new GamePage(coach, game);
                frame.setVisible(false);
            }
        });
    }

    //EFFECTS: gets the opponent name from user
    //MODIFIES: this
    private String getOpponent() {

        add(opLabel);
        add(Box.createVerticalStrut(25));
        add(opponentField);
        add(Box.createVerticalStrut(10));
        opponentField.setMaximumSize(new Dimension(300,30));
        submitOpponent.setAlignmentX(0f);
        add(submitOpponent);
        add(Box.createVerticalStrut(25));

        return opponentField.getText();
    }

    //EFFECTS: sets the action for submitOpponent button
    private void submitOpponent() {
        submitOpponent.addActionListener(e -> {
            game.setOpponent(opponentField.getText());
            JLabel submitted = new JLabel("Opponent name set to: " + game.getOpponentName());
            submitted.setAlignmentX(0f);
            add(submitted);
            add(Box.createVerticalStrut(25));
            revalidate();
        });
    }
}