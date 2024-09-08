package ui.panels;

import model.Coach;
import model.Game;
import model.Player;
import ui.pages.TitlePage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//represents the panel for EndGamePage
public class EndGamePanel extends JPanel {

    private final Coach coach;
    private final Game game;
    private final JFrame frame;
    private final JTextField score = new JTextField();
    private final JButton submit = new JButton("submit");
    private JLabel msg;
    private final JButton backToTitlePage = new JButton("BackToTitlePage");
    private final String[] statVariables = {"Player","Pts","Rebounds","Assists","2PM","2PA","2P%","3PM","3PA","3P%",
            "FTM", "FTA","FT%","Fouls"};

    public EndGamePanel(Coach coach, Game game, JFrame frame) {
        this.coach = coach;
        this.game = game;
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        game.fillStatSheet();
        JLabel info = new JLabel("Enter final score");
        add(info);
        add(score);
        add(submit);
        add(backToTitlePage);
        backToTitlePage.setVisible(false);
        setScore();
        displayGameStat();
        setBackToTitlePage();
    }

    //EFFECTS: sets the action for the setScore button
    private void setScore() {
        submit.addActionListener(e -> {
            game.setFinalScore(score.getText());
            msg = new JLabel("Final score against " + game.getOpponentName() + " set to "
                    + game.getFinalScore());
            add(msg);
            backToTitlePage.setVisible(true);
            revalidate();
        });
    }

    //EFFECTS: displays the game statistic
    private void displayGameStat() {
        List<Player> listOfPlayers = game.getPlayers();
        String[][] playerStats = getPlayerStats(listOfPlayers);
        JTable stats = new JTable(playerStats, statVariables);
        add(new JScrollPane(stats), BorderLayout.CENTER);

    }

    //EFFECTS: converts the double statistics to string to use for JTable
    private String[][] getPlayerStats(List<Player> players) {
        double[][] playerStats = new double[players.size()][13];
        int row = 0;
        int col = 0;
        for (Player p : players) {
            for (Double ignored : p.getCurrentGameStats()) {
                playerStats[row][col] = p.getCurrentGameStats()[col];
                col++;
            }
            row++;
            col = 0;
        }

        String[][] toRet = new String[playerStats.length][14];
        for (int i = 0; i < players.size(); i++) {
            toRet[i][0] = players.get(i).getName();
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 1; j < 14; j++) {
                toRet[i][j] = Double.toString(playerStats[i][j - 1]);
            }
        }
        reset(players);
        return toRet;
    }

    //EFFECTS: fills each players statistics and set currentGameStats to 0 for next game
    private void reset(List<Player> listOfPlayers) {
        for (Player player : listOfPlayers) {
            player.fillStats();
            player.reset();
        }
    }

    //EFFECTS: sets the action for backToTitlePage button
    private void setBackToTitlePage() {
        backToTitlePage.addActionListener(e -> {
            frame.setVisible(false);
            new TitlePage(coach);
        });
    }
}
