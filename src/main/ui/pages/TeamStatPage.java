package ui.pages;

import model.Player;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//represents the page where user views the team statistics
public class TeamStatPage extends JFrame {

    public TeamStatPage(Team team) {
        super("StatTracker");
        setSize(new Dimension(700, 250));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        List<Player> listOfPlayers = team.getListOfPlayers();
        String[][] playerStats = getPlayerStats(listOfPlayers);
        String[] statVariables = {"Player", "GP", "PPG", "Rebounds", "Assists", "2PM", "2PA", "2P%", "3PM", "3PA",
                "3P%", "FTM", "FTA", "FT%", "Fouls", "Fouls/Game"};
        JTable stats = new JTable(playerStats, statVariables);
        getContentPane().add(new JScrollPane(stats), BorderLayout.CENTER);
        setVisible(true);
    }

    //EFFECTS: fill in stats and return a string version for JTable
    private String[][] getPlayerStats(List<Player> players) {
        double[][] playerStats = new double[players.size()][15];
        int row = 0;
        int col = 0;
        for (Player p : players) {
            for (Double ignored : p.getStats()) {
                playerStats[row][col] = p.getStats()[col];
                col++;
            }
            row++;
            col = 0;
        }

        String[][] toRet = new String[playerStats.length][16];
        for (int i = 0; i < players.size(); i++) {
            toRet[i][0] = players.get(i).getName();
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 1; j < 16; j++) {
                toRet[i][j] = Double.toString(playerStats[i][j - 1]);
            }
        }
        return toRet;
    }
}
