package model;

import model.exceptions.EmptyStringException;
import model.exceptions.SameNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

//represents a team with a list of players, list of games, and a team name.
public class Team implements Writable {

    private final String teamName;    //team name
    private final List<Player> listOfPlayers; // list of players
    private final List<Game> listOfGames; //list of the games played
    private int numOfPlayers;

    //EFFECTS: creates new instances for both lists and set this.name to name
    public Team(String teamName) {
        listOfPlayers = new LinkedList<>();
        listOfGames = new LinkedList<>();
        this.teamName = teamName;
        numOfPlayers = 0;
    }

    public void addNumOfPlayers() {
        this.numOfPlayers++;
    }

    ///MODIFIES: this
    //EFFECTS: adds a player to the list of players
    public void addPlayer(Player player) throws SameNameException {

        for (Player p : listOfPlayers) {
            if (p.getName().equals(player.getName())) {
                throw new SameNameException();
            }
        }
        listOfPlayers.add(player);
        EventLog.getInstance().logEvent(new Event(player.getName() + " added to team: " + this.teamName + "."));
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public String getName() {
        return this.teamName;
    }

    public List<Game> getListOfGames() {
        return this.listOfGames;
    }

    public List<Player> getListOfPlayers() {
        return this.listOfPlayers;
    }

    //add a game to listOfGames
    public void addGame(Game game) {
        listOfGames.add(game);
        EventLog.getInstance().logEvent(new Event("New game added to the list of games."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamName", teamName);
        json.put("listOfPlayers", playersToJson());
        json.put("listOfGames", gamesToJson());
        return json;
    }

    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : listOfPlayers) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    public JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : listOfGames) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

}
