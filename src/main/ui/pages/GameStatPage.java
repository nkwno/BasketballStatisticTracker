package ui.pages;

import model.Game;

import javax.swing.*;
import java.awt.*;

//represents the stats for a selected game
public class GameStatPage extends JFrame {

    private final Game game;
    private String[][] stringStats;

    public GameStatPage(Game game) {
        super("StatTracker");
        this.game = game;
        setSize(new Dimension(700, 250));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getStringStats();
        String[] statVariables = {"Player", "Pts", "Rebounds", "Assists", "2PM", "2PA", "2P%", "3PM", "3PA", "3P%",
                "FTM", "FTA", "FT%", "Fouls"};
        JTable gameStats = new JTable(stringStats, statVariables);
        getContentPane().add(new JScrollPane(gameStats), BorderLayout.CENTER);
        setVisible(true);
    }

    //EFFECTS: converts double values of game.getGameStat to string to use in JTable
    private void getStringStats() {
        stringStats = new String[game.getGameStat().length][game.getGameStat()[0].length];
        for (int i = 0; i < game.getPlayers().size(); i++) {
            stringStats[i][0] = game.getPlayers().get(i).getName();
        }
        for (int i = 0; i < game.getGameStat().length;i++) {
            for (int j = 1; j < game.getGameStat()[0].length - 1;j++) {
                stringStats[i][j] = Double.toString(game.getGameStat()[i][j - 1]);
            }
        }
    }
}
