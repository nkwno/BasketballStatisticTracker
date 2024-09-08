package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//represents a single game of a team
public class Game implements Writable {

    private String opponent; // name of the opponent
    private List<Player> players; //list of players playing in the game
    private double[][] statSheet; //row being players and column being stats from PLayer
    private String finalScore;


//    statSheet.put(combineStats(statSheet.get(player), newStats));
    //creates new Game; set this.opponent to opponent;
    //create new instance of Players and set final score to ""
    public Game(String opponent) {
        this.opponent = opponent;
        players = new LinkedList<>();
        finalScore = "";
    }

    public String getOpponentName() {
        return this.opponent;
    }

    //EFFECTS: sets the final score of the game
    //MODIFIES: this
    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
        EventLog.getInstance().logEvent(new Event("Final score against " + this.opponent
                + " set to: " + finalScore + "."));
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
        EventLog.getInstance().logEvent(new Event("Opponent set to: " + opponent + "."));
    }

    public void setStatSheet(JSONArray statSheet, int numOfPlayers) {
        this.statSheet = new double[numOfPlayers][15];
        for (int i = 0; i < statSheet.length(); i++) {
            JSONArray rowArray = statSheet.getJSONArray(i);
            for (int j = 0; j < statSheet.getJSONArray(0).length(); j++) {
                this.statSheet[i][j] = rowArray.getDouble(j);
            }
        }
    }

    public double[][] getGameStat() {
        return this.statSheet;
    }

    public String getFinalScore() {
        return this.finalScore;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    //EFFECTS: adds player to the list of players that are playing in the game
    //MODIFIES: this
    public void addPlayer(Player player) {
        players.add(player);
        EventLog.getInstance().logEvent(new Event("Added " + player.getName() + " to game against "
                + this.opponent + "."));
    }

    //EFFECTS: fills statSheet with the currentGame stats from each player.
    //MODIFIES: this
    //REQUIRES: team games played > 0.
    public void fillStatSheet() {
        statSheet = new double[players.size()][13];
        int colIndex = 0;
        int rowIndex = 0;
        //loop through number of players
        for (Player player : players) {
            for (double stat : player.getCurrentGameStats()) {
                statSheet[rowIndex][colIndex] = stat;
                colIndex++;
            }
            colIndex = 0;
            rowIndex++;
        }
        EventLog.getInstance().logEvent(new Event("Stat sheet against " + this.opponent + " filled."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("opponent", opponent);
        json.put("players", playersToJson());
        json.put("statSheet", statSheetToJson());
        json.put("finalScore", finalScore);
        return json;
    }

    public JSONArray statSheetToJson() {
        JSONArray jsonArray = new JSONArray();

        for (double[] row : statSheet) {
            JSONArray rowArray = new JSONArray();
            for (double value : row) {
                rowArray.put(value);
            }
            jsonArray.put(rowArray);
        }

        return jsonArray;
    }

    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : players) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
