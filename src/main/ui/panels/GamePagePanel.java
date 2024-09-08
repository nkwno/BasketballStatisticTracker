package ui.panels;

import model.Coach;
import model.Game;
import model.Player;
import ui.pages.EndGamePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//represents the panel for GamePage
public class GamePagePanel extends JPanel {

    private final Game game;
    private final JButton onePoint = new JButton("+1 point");
    private final JButton twoPoints = new JButton("+2 points");
    private final JButton threePoints = new JButton("+3 points");
    private final JButton rebound = new JButton("+1 rebound");
    private final JButton assist = new JButton("+1 assist");
    private final JButton threePointAttempt = new JButton("+1 three point attempt");
    private final JButton twoPointAttempt = new JButton("+1 two point attempt");
    private final JButton freeThrowAttempt = new JButton("+1 free throw attempt");
    private final JButton foul = new JButton("+1 foul");
    private final JButton endGame = new JButton("End Game");
    private final List<JButton> listOfPlayers = new ArrayList<>();
    private final List<Player> playing;
    private Player currentPlayer;
    private final JFrame frame;
    private final Coach coach;
    private final JLabel msg = new JLabel();

    public GamePagePanel(Coach coach, Game game, JFrame frame) {
        this.game = game;
        this.frame = frame;
        this.coach = coach;
        playing = game.getPlayers();
        JLabel info = new JLabel("Select player to add stat");
        add(info);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,0,5,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        printButtonList(gbc);
        for (JButton b : listOfPlayers) {
            add(b, gbc);
            gbc.gridy++;
        }
        add(endGame);
        add(msg);
        callButtons();
    }

    //EFFECTS: sets the action for each player button and adds them to panel
    private void printButtonList(GridBagConstraints gbc) {
        for (Player p : game.getPlayers()) {
            p.addGamesPlayed();
            p.reset();
            listOfPlayers.add(new JButton(p.getName()) {{
                    addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            currentPlayer = p;
                            addButtons(gbc);
                            repaint();
                            revalidate();
                        }
                    });
                    }
            });
        }
    }

    //EFFECTS: calls all the buttons
    private void callButtons() {
        setOnePoint();
        setTwoPoints();
        setThreePoints();
        setRebound();
        setAssist();
        setTwoPointAttempt();
        setThreePointAttempt();
        setFreeThrowAttempt();
        setFoul();
        setEndGame();
    }

    //EFFECTS: adds all the stat buttons to panel
    private void addButtons(GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(onePoint, gbc);
        gbc.gridy++;
        add(twoPoints, gbc);
        gbc.gridy++;
        add(threePoints, gbc);
        gbc.gridy++;
        add(rebound, gbc);
        gbc.gridy++;
        add(assist, gbc);
        gbc.gridy++;
        add(threePointAttempt, gbc);
        gbc.gridy++;
        add(twoPointAttempt, gbc);
        gbc.gridy++;
        add(freeThrowAttempt, gbc);
        gbc.gridy++;
        add(foul, gbc);
    }

    //EFFECTS: finds the player that matches with name
    private Player findPlayer(String name) {
        for (Player p : playing) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    //EFFECTS: adds a point to player
    private void setOnePoint() {
        onePoint.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addOnePoint();
            player.addFreeThrowAttempts();
            player.addFreeThrowsMade();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one point to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds two points to player
    private void setTwoPoints() {
        twoPoints.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addTwoPoints();
            player.addTwoPointersMade();
            player.addTwoPointAttempts();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added two points to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds three points to player
    private void setThreePoints() {
        threePoints.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addThreePoints();
            player.addTwoPointersMade();
            player.addThreePointAttempt();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added three points to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds a rebound to player
    private void setRebound() {
        rebound.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addRebound();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one rebound to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds an assist to player
    private void setAssist() {
        assist.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addAssist();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one assist to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds a three point attempt to player
    private void setThreePointAttempt() {
        threePointAttempt.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addThreePointAttempt();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one three point attempt to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds a two point attempt to player
    private void setTwoPointAttempt() {
        twoPointAttempt.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addTwoPointAttempts();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one two point attempt to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds a free throw attempt to player
    private void setFreeThrowAttempt() {
        freeThrowAttempt.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addOnePoint();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one free throw attempt to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: adds a foul to player
    private void setFoul() {
        foul.addActionListener(e -> {
            Player player = findPlayer(currentPlayer.getName());
            assert player != null;
            player.addFouls();
            remove(onePoint);
            remove(twoPoints);
            remove(threePoints);
            remove(rebound);
            remove(assist);
            remove(threePointAttempt);
            remove(twoPointAttempt);
            remove(freeThrowAttempt);
            remove(foul);
            msg.setText("Added one foul to: " + player.getName());
            repaint();
            revalidate();
        });
    }

    //EFFECTS: sets the action for endGame button
    private void setEndGame() {
        endGame.addActionListener(e -> {
            new EndGamePage(coach, game);
            frame.setVisible(false);
        });
    }
}
