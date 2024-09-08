package persistence;

import model.Coach;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Game;
import model.Player;
import model.Team;
import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import model.exceptions.SameNameException;
import model.exceptions.SameTeamNameException;
import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Coach read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCoach(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Coach parseCoach(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Coach coach = new Coach();
        try {
            coach.setName(name);
            addTeams(coach, jsonObject);
        } catch (EmptyStringException e) {
            System.out.println("failed to load data");
        }
        return coach;
    }

    // MODIFIES: coach
    // EFFECTS: parses team from JSON object and adds them to coach
    private void addTeams(Coach coach, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(coach, nextTeam);
        }
    }

    // MODIFIES: coach
    // EFFECTS: parses team from JSON object and adds it to coach
    private void addTeam(Coach coach, JSONObject jsonObject) {
        String name = jsonObject.getString("teamName");
        Team team = new Team(name);
        JSONArray listOfPlayers = jsonObject.getJSONArray("listOfPlayers");
        for (Object json : listOfPlayers) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }

        JSONArray listOfGames = jsonObject.getJSONArray("listOfGames");
        for (Object json : listOfGames) {
            JSONObject nextGame = (JSONObject) json;
            addGame(team, nextGame);
        }

        try {
            coach.addTeam(team);
        } catch (SameTeamNameException e) {
            System.out.println("Could not load data");
        }
    }

    //MODIFIES: coach
    //EFFECTS: parses game from JSON object and adds them to team
    private void addGame(Team team, JSONObject jsonObject) {
        String opponent = jsonObject.getString("opponent");
        Game game = new Game(opponent);
        game.setOpponent(opponent);

        JSONArray listOfCurrentPlayers = jsonObject.getJSONArray("players");

        for (Object currentPlayer : listOfCurrentPlayers) {
            JSONObject nextCurrentPlayer = (JSONObject) currentPlayer;
            addPlayerToGame(team, game, nextCurrentPlayer);
        }

        JSONArray statSheet = jsonObject.getJSONArray("statSheet");
        game.setStatSheet(statSheet, team.getNumOfPlayers());
        String finalScore = jsonObject.getString("finalScore");
        game.setFinalScore(finalScore);
        team.addGame(game);
    }

    private void addPlayerToGame(Team team, Game game, JSONObject nextCurrentPlayer) {
        for (Player player : team.getListOfPlayers()) {
            if (player.getName().equals(nextCurrentPlayer.getString("name"))) {
                game.addPlayer(player);
            }
        }
    }

    //MODIFIES: coach
    //EFFECTS: parses player from JSON object and adds them to team which then is added to coach
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addPlayer(Team team, JSONObject jsonObject) {

        try {
            String name = jsonObject.getString("name");
            Player player = new Player(name);
            JSONArray playerStats = jsonObject.getJSONArray("stats");
            team.addNumOfPlayers();
            player.setGamesPlayed(playerStats.getDouble(0));
            player.setPoints(playerStats.getDouble(1));
            player.setRebounds(playerStats.getDouble(2));
            player.setAssists(playerStats.getDouble(3));
            player.setTwoPointersMade(playerStats.getDouble(4));
            player.setTwoPointAttempts(playerStats.getDouble(5));
            player.setThreePointersMade(playerStats.getDouble(7));
            player.setThreePointAttempts(playerStats.getDouble(8));
            player.setFreeThrowsMade(playerStats.getDouble(10));
            player.setFreeThrowAttempts(playerStats.getDouble(11));
            player.setFouls(playerStats.getDouble(13));
            player.setStats();
            JSONArray currentGameStats = jsonObject.getJSONArray("currentGameStats");
            player.setCurrentGameStats(currentGameStats);
            team.addPlayer(player);
        } catch (MaxLengthExceededException e) {
            System.out.println("Unable to read data.");
        } catch (SameNameException e) {
            System.out.println("Unable to read data.");
        } catch (EmptyStringException e) {
            System.out.println("Unable to read data.");
        }

    }



}
