package model;


import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import model.exceptions.SameNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//represents a player on a team
//Players are added onto a list of players on a team
public class Player implements Writable {

    private final double[] stats; //each index represents a different statistic of all games played
    private final double[] currentGameStats; // keeps track of stats in the current Game without GP and FPG

    // {number of games played,ppg,rpg,apg,twoPointersMade,twoPointAttempts,2P%,threePointersMade,threePointAttempts,
    // 3P%,freeThrowsMade,freeThrowAttempts,FT%,fouls,foulsPerGame}

    private final String name;   //name of player
    private double points;     //total points in the current game - ppg
    private double rebounds;   //total rebounds in the current - rpg
    private double assists;    //total assists in the current game - apg
    private double threePointersMade;      //total threes in the current game - 3P%
    private double threePointAttempts;     //total three pointers attempted - 3P%
    private double freeThrowsMade;     //total free throws - FT%
    private double freeThrowAttempts;      //total attempted free throws - FT%
    private double twoPointersMade;    //total number of made two pointers - 2P%
    private double twoPointAttempts;   //total two point attempts - 2P%
    private double fouls;  //average fouls per games
    private double gamesPlayed;    //number of games played

    //EFFECTS: set name to name; set everything to 0; create new instances of both arrays and set values to 0;
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public Player(String name) throws MaxLengthExceededException, EmptyStringException {
        if (name.length() > 9) {
            throw new MaxLengthExceededException();
        }
        if (name.trim().length() == 0) {
            throw new EmptyStringException();
        }
        this.name = name;
        this.points = 0;
        this.rebounds = 0;
        this.assists = 0;
        this.threePointAttempts = 0;
        this.threePointersMade = 0;
        this.freeThrowAttempts = 0;
        this.freeThrowsMade = 0;
        this.twoPointAttempts = 0;
        this.twoPointersMade = 0;
        this.fouls = 0;
        this.gamesPlayed = 0;
        this.stats = new double[15];

        for (int i = 0;i < stats.length;i++) {
            stats[i] = 0;
        }
        this.currentGameStats = new double[13];
        for (int i = 0;i < stats.length;i++) {
            stats[i] = 0;
        }
    }

    public void setAssists(double assists) {
        this.assists = assists;
    }

    public void setFouls(double fouls) {
        this.fouls = fouls;
    }

    public void setFreeThrowAttempts(double freeThrowAttempts) {
        this.freeThrowAttempts = freeThrowAttempts;
    }

    public void setFreeThrowsMade(double freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
    }

    public void setGamesPlayed(double gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void setRebounds(double rebounds) {
        this.rebounds = rebounds;
    }

    public void setThreePointAttempts(double threePointAttempts) {
        this.threePointAttempts = threePointAttempts;
    }

    public void setThreePointersMade(double threePointersMade) {
        this.threePointersMade = threePointersMade;
    }

    public void setTwoPointAttempts(double twoPointAttempts) {
        this.twoPointAttempts = twoPointAttempts;
    }

    public void setTwoPointersMade(double twoPointersMade) {
        this.twoPointersMade = twoPointersMade;
    }



    public void setCurrentGameStats(JSONArray currentGameStats) {
        for (int i = 0; i < currentGameStats.length(); i++) {
            this.currentGameStats[i] = currentGameStats.getDouble(i);
        }
    }

    public double[] getStats() {
        return this.stats;
    }

    public String getName() {
        return this.name;
    }

    public double getAssists() {
        return assists;
    }

    public double getFouls() {
        return fouls;
    }

    public double getFreeThrowAttempts() {
        return freeThrowAttempts;
    }

    public double getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public double getGamesPlayed() {
        return gamesPlayed;
    }

    public double getPoints() {
        return points;
    }

    public double getRebounds() {
        return rebounds;
    }

    public double getThreePointAttempts() {
        return threePointAttempts;
    }

    public double getThreePointersMade() {
        return threePointersMade;
    }

    public double getTwoPointAttempts() {
        return twoPointAttempts;
    }

    public double getTwoPointersMade() {
        return twoPointersMade;
    }

    //EFFECTS: adds one to points;
    //MODIFIES: this
    public void addOnePoint() {
        points += 1;
        currentGameStats[0] = points;
        EventLog.getInstance().logEvent(new Event("Added one point to: " + this.name + "."));
    }

    //EFFECTS: adds two to points;
    //MODIFIES: this
    public void addTwoPoints() {
        points += 2;
        currentGameStats[0] = points;
        EventLog.getInstance().logEvent(new Event("Added two points to: " + this.name + "."));
    }

    //EFFECTS: adds three to points;
    //MODIFIES: this
    public void addThreePoints() {
        points += 3;
        currentGameStats[0] = points;
        EventLog.getInstance().logEvent(new Event("Added three points to: " + this.name + "."));
    }

    //EFFECTS: adds one rebound;
    //MODIFIES: this
    public void addRebound() {
        rebounds++;
        currentGameStats[1] = rebounds;
        EventLog.getInstance().logEvent(new Event("Added one rebound to: " + this.name + "."));
    }

    //EFFECTS: adds one assist;
    //MODIFIES: this
    public void addAssist() {
        assists++;
        currentGameStats[2] = assists;
        EventLog.getInstance().logEvent(new Event("Added one assist to: " + this.name + "."));
    }

    //EFFECTS: adds one TwoPointersMade;
    //MODIFIES: this
    public void addTwoPointersMade() {
        twoPointersMade++;
        currentGameStats[3] = twoPointersMade;
        EventLog.getInstance().logEvent(new Event("Added one two point make to: " + this.name + "."));
    }

    //EFFECTS: adds one TwoPointAttempts;
    //MODIFIES: this
    public void addTwoPointAttempts() {
        twoPointAttempts++;
        currentGameStats[4] = twoPointAttempts;
        currentGameStats[5] = twoPointersMade / twoPointAttempts;
        EventLog.getInstance().logEvent(new Event("Added one two point attempt to: " + this.name + "."));
    }

    //EFFECTS: adds one ThreePointersMade;
    //MODIFIES: this
    public void addThreePointersMade() {
        threePointersMade++;
        currentGameStats[6] = threePointersMade;
        EventLog.getInstance().logEvent(new Event("Added one three point make to: " + this.name + "."));
    }

    //EFFECTS: adds one ThreePointAttempts;
    //MODIFIES: this
    public void addThreePointAttempt() {
        threePointAttempts++;
        currentGameStats[7] = threePointAttempts;
        currentGameStats[8] = threePointersMade / threePointAttempts;
        EventLog.getInstance().logEvent(new Event("Added one three point attempt to: " + this.name + "."));

    }

    //EFFECTS: adds one FreeThrowsMade;
    //MODIFIES: this
    public void addFreeThrowsMade() {
        freeThrowsMade++;
        currentGameStats[9] = freeThrowsMade;
        EventLog.getInstance().logEvent(new Event("Added one free throw made to: " + this.name + "."));
    }

    //EFFECTS: adds one FreeThrowAttempts;
    //MODIFIES: this
    public void addFreeThrowAttempts() {
        freeThrowAttempts++;
        currentGameStats[10] = freeThrowAttempts;
        currentGameStats[11] = freeThrowsMade / freeThrowAttempts;
        EventLog.getInstance().logEvent(new Event("Added one free throw attempt to: " + this.name + "."));
    }

    //EFFECTS: adds one Foul;
    //MODIFIES: this
    public void addFouls() {
        fouls++;
        currentGameStats[12] = fouls;
        EventLog.getInstance().logEvent(new Event("Added one foul to: " + this.name + "."));
    }

    //EFFECTS: adds one GamesPlayed;
    //MODIFIES: this
    public void addGamesPlayed() {
        gamesPlayed++;
        EventLog.getInstance().logEvent(new Event("Added one games played to: " + this.name + "."));
    }

    public double[] getCurrentGameStats() {
        return this.currentGameStats;
    }

    //EFFECTS: fills in the stats of the player
    //MODIFIES: this
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void fillStats() {
        stats[0] = gamesPlayed;
        stats[1] = Math.floor(((stats[1] + points) / gamesPlayed) * 100) / 100; //prints to 2 decimal places
        stats[2] = Math.floor(((stats[2] + rebounds) / gamesPlayed) * 100) / 100;
        stats[3] = Math.floor(((stats[3] + assists) / gamesPlayed) * 100) / 100;
        stats[4] += twoPointersMade;
        stats[5] += twoPointAttempts;
        if (stats[5] == 0) {
            stats[6] = 0;
        } else {
            stats[6] = Math.floor((stats[4] / stats[5]) * 100) / 100;
        }
        stats[7] += threePointersMade;
        stats[8] += threePointAttempts;
        if (stats[8] == 0) {
            stats[9] = 0;
        } else {
            stats[9] = Math.floor((stats[7] / stats[8]) * 100) / 100;
        }
        stats[10] += freeThrowsMade;
        stats[11] += freeThrowAttempts;
        if (stats[11] == 0) {
            stats[12] = 0;
        } else {
            stats[12] = Math.floor((stats[10] / stats[11]) * 100) / 100;
        }
        stats[13] += fouls;
        if (gamesPlayed == 0) {
            stats[14] = 0;
        } else {
            stats[14] = Math.floor((stats[13] / gamesPlayed) * 100) / 100;
        }
        EventLog.getInstance().logEvent(new Event("Filled stat sheet for " + this.name + "."));
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void setStats() {
        stats[0] = gamesPlayed;
        stats[1] = points;
        stats[2] = rebounds;
        stats[3] = assists;
        stats[4] = twoPointersMade;
        stats[5] = twoPointAttempts;
        if (stats[5] == 0) {
            stats[6] = 0;
        } else {
            stats[6] = Math.floor((stats[4] / stats[5]) * 100) / 100;
        }
        stats[7] = threePointersMade;
        stats[8] = threePointAttempts;
        if (stats[8] == 0) {
            stats[9] = 0;
        } else {
            stats[9] = Math.floor((stats[7] / stats[8]) * 100) / 100;
        }
        stats[10] = freeThrowsMade;
        stats[11] = freeThrowAttempts;
        if (stats[11] == 0) {
            stats[12] = 0;
        } else {
            stats[12] = Math.floor((stats[10] / stats[11]) * 100) / 100;
        }
        stats[13] = fouls;
        if (gamesPlayed == 0) {
            stats[14] = 0;
        } else {
            stats[14] = Math.floor((stats[13] / gamesPlayed) * 100) / 100;
        }
    }


    //EFFECTS: resets fields to 0 for next use in the next game
    //MODIFIES: this
    public void reset() {
        this.points = 0;
        this.rebounds = 0;
        this.assists = 0;
        this.threePointersMade = 0;
        this.threePointAttempts = 0;
        this.freeThrowsMade = 0;
        this.freeThrowAttempts = 0;
        this.twoPointersMade = 0;
        this.twoPointAttempts = 0;
        this.fouls = 0;

        for (int i = 0; i < currentGameStats.length; i++) {
            currentGameStats[i] = 0;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("points", points);
        json.put("rebounds", rebounds);
        json.put("assists", assists);
        json.put("threePointersMade", threePointersMade);
        json.put("threePointAttempts", threePointAttempts);
        json.put("freeThrowsMade", freeThrowsMade);
        json.put("freeThrowAttempts", freeThrowAttempts);
        json.put("twoPointersMade", twoPointersMade);
        json.put("twoPointAttempts", twoPointAttempts);
        json.put("fouls", fouls);
        json.put("gamesPlayed", gamesPlayed);
        json.put("stats", statToJson());
        json.put("currentGameStats", currentGameStatsToJson());
        return json;
    }

    public JSONArray statToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < stats.length;i++) {
            jsonArray.put(stats[i]);
        }
        return  jsonArray;
    }

    public JSONArray currentGameStatsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < currentGameStats.length;i++) {
            jsonArray.put(currentGameStats[i]);
        }
        return  jsonArray;
    }
}
