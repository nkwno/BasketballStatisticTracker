package ui;

import model.Coach;
import model.Game;
import model.Player;
import model.Team;
import model.exceptions.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.pages.LoadPage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//represents a StatTracker app
public class StatTracker {

    private static final String JSON_STORE = "./data/app.json";
    private Coach coach;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public StatTracker() throws FileNotFoundException {
        new LoadPage();
        System.out.println("Would you like to load data?. Yes or No");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        boolean cont = true;

        while (cont) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("Yes")) {
                cont = false;
                loadCoach();
                titlePage();
            } else if (input.equals("No")) {
                cont = false;
                coach = new Coach();
                coach.setChanged(true);
                startPage();
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }


    //EFFECTS: load coach from app.json
    //MODIFIES: this
    private void loadCoach() {
        try {
            coach = jsonReader.read();
            System.out.println("Loaded " + coach.getName() + " from " + JSON_STORE);
            coach.setChanged(false);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("Unable to read data");
            startPage();
        }
    }

    //the start page when you first run the application
    //MODIFIES: coach name
    public void startPage() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to StatTracker! Let's start by adding your name: ");
            System.out.println("Name: ");
            String name = scanner.nextLine();
            coach.setName(name);
            System.out.println("Hi " + coach.getName() + ", you currently have no teams. Let's add a team!");
            addTeam();
        } catch (EmptyStringException e) {
            System.out.println("Cannot accept empty name.");
        }
    }

    //title page of the app
    //EFFECTS: displays the options for user
    private void titlePage() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome " + coach.getName() + "!\n Choose a team to interact with or enter -1"
                    + " to add a new team. Enter 0 to quit app.");
            for (int i = 1; i < coach.getListOfTeam().size() + 1; i++) {
                System.out.println(i + ". " + coach.getListOfTeam().get(i - 1).getName());
            }


            int userInput = scanner.nextInt();
            if (userInput == 0) {
                if (coach.getChanged()) {
                    savePage();
                }
                quit();
            }

            if (userInput == -1) {
                addTeam();
            }

            if (userInput > 0 && userInput <= coach.getListOfTeam().size()) {
                teamPage(userInput - 1);
            }
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team.");
        }

    }

    //EFFECTS: asks user if they want to save coach to file and saves it if they do
    private void savePage() {
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Would you like to save data? \n Yes or No?");

        String input = scanner.nextLine();

        if (input.equals("Yes")) {
            try {
                jsonWriter.open();
                jsonWriter.write(coach);
                jsonWriter.close();
                System.out.println("Saved user " + coach.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        } else if (input.equals("No")) {
            System.out.println("Data not saved");
        } else {
            System.out.println("Invalid input. Please try again.");
            savePage();
        }

    }

    //EFFECTS: adds a new team to coach from user input; team will have players and team name from user input
    //MODIFIES: this
    private void addTeam() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type team name: ");
        String teamName = scanner.nextLine();
        Team team = new Team(teamName);
        try {
            coach.addTeam(team);
            System.out.println("Next let's add players to your team. Each time you type a player, press enter.\n"
                    + "Enter DONE when you are finished." + "(Max 9 characters)");
            addPlayerToNewTeam(team);
            System.out.println("All set! you can add more teams and view statistics.");
            titlePage();
        } catch (SameTeamNameException e) {
            System.out.println("A team with that name already exists");
        }

    }

    //EFFECTS: add a new player to new team
    //MODIFIES: this
    private void addPlayerToNewTeam(Team team) {
        Scanner scanner = new Scanner(System.in);
        String playerName;

        try {
            playerName = scanner.nextLine();

            if (playerName.equals("DONE")) {
                coach.setChanged(true);

            } else {
                Player player = new Player(playerName);
                team.addPlayer(player);
                System.out.println(playerName + " added to team " + team.getName());
                addPlayerToNewTeam(team);
            }

        } catch (MaxLengthExceededException e) {
            System.out.println("Max length of name exceeded. Try again.");
            addPlayerToNewTeam(team);
        } catch (SameNameException e) {
            System.out.println("A player with that name already exists. Try again.");
            addPlayerToNewTeam(team);
        } catch (EmptyStringException e) {
            System.out.println("Cannot accept empty name.");
            addPlayerToNewTeam(team);
        }
    }

    //EFFECTS: displays the team page to the user
    private void teamPage(int teamIndex) {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Choose an option: \n" + "1. Start a new game\n"
                            + "2. View games played\n" + "3. View team statistics\n" + "4. Back to title page\n"
                            + "5. Add new player");
        int userInput = scanner.nextInt();
        switch (userInput) {

            case 1:
                newGame(teamIndex);
                break;
            case 2:
                viewGames(teamIndex);
                break;
            case 3:
                viewTeamStats(teamIndex);
                break;
            case 4:
                titlePage();
                break;
            case 5:
                addNewPlayer(teamIndex);
                break;
        }
    }

    //EFFECTS: Asks user to enter the new players name
    //MODIFIES: this
    private void addNewPlayer(int teamIndex) {

        try {
            System.out.println("Enter new player's name");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            Player player = new Player(userInput);
            coach.getListOfTeam().get(teamIndex).addPlayer(player);
            coach.setChanged(true);
        } catch (SameNameException e) {
            System.out.println("Cannot input the same name! Try Again");
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have any teams.");
        } catch (MaxLengthExceededException e) {
            System.out.println("Max length of name exceeded. Try again.");
            addNewPlayer(teamIndex);
        } catch (EmptyStringException e) {
            System.out.println("Cannot have empty name.");
        }
        teamPage(teamIndex);
    }

    //EFFECTS: prints out the entire teams statistics
    private void viewTeamStats(int teamIndex) {
        List<Player> listOfPlayers;
        try {
            listOfPlayers = coach.getListOfTeam().get(teamIndex).getListOfPlayers();
            if (listOfPlayers.isEmpty()) {
                System.out.println("This team has not played any games yet");
                teamPage(teamIndex);
            }
            printVariables();
            System.out.println();
            printTeamStatistics(listOfPlayers);
            teamPage(teamIndex);
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have any teams");
            titlePage();
        }

    }

    //EFFECTS: prints the teams statistics
    private void printTeamStatistics(List<Player> listOfPlayers) {
        double[] individualPlayerStat = new double[15];
        //get players stats and print them individually
        for (Player player : listOfPlayers) {
            System.out.printf("%-9s", player.getName());
            for (int i = 0; i < player.getStats().length;i++) {
                individualPlayerStat[i] = player.getStats()[i];
            }
            //print out player stats in specific format
            for (double stat: individualPlayerStat) {
                System.out.printf("%-4.2f     ", stat);
            }
            //reset individualPlayerStat for next player use
            for (int i = 0; i < player.getStats().length;i++) {
                individualPlayerStat[i] = 0;
            }
            System.out.println();
        }
    }


    //EFFECTS: prints out the variables in a specific format
    private void printVariables() {
        String[] statVariables = {"Player", "GP","PPG","Rebounds","Assists","2PM","2PA","2P%","3PM","3PA","3P%","FTM",
                "FTA","FT%","Fouls","Fouls/Game"};
        for (String variables: statVariables) {
            System.out.printf("%-9s", variables);
        }
    }

    //EFFECTS: prints out the list of games and gives options for the user to view game stats
    private void viewGames(int teamIndex) {

        try {
            List<Game> listOfGames = coach.getListOfTeam().get(teamIndex).getListOfGames();

            if (listOfGames.size() == 0) {
                System.out.println("This team has not played any games yet!");
                teamPage(teamIndex);
            }
            String teamName = coach.getListOfTeam().get(teamIndex).getName();
            System.out.println("Enter a game option to see the stats");
            int gameNumber = 1;
            for (Game game : listOfGames) {
                System.out.print(gameNumber + ". " + teamName + " vs " + game.getOpponentName());
                System.out.println(" - Score: " + game.getFinalScore());
                gameNumber++;
            }
            Scanner scanner = new Scanner(System.in);
            int userInput = scanner.nextInt();
            viewGameStatistic(teamIndex, userInput);
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team");
            titlePage();
        }


    }

    //EFFECTS: print out the game statistics chosen from userInput
    private void viewGameStatistic(int teamIndex, int userInput) {
        try {
            Game game = coach.getListOfTeam().get(teamIndex).getListOfGames().get(userInput - 1);
            List<Player> listOfPlayers = game.getPlayers();
            double[][] statSheet = game.getGameStat();
            String[] statVariables = {"Player","Points","Rebounds","Assists","2PM","2PA","2P%","3PM","3PA","3P%","FTM",
                    "FTA","FT%","Fouls"};
            for (String variables: statVariables) {
                System.out.printf("%-9s", variables);
            }
            System.out.println();
            for (int i = 0;i < listOfPlayers.size();i++) {
                System.out.printf("%-9s",listOfPlayers.get(i).getName());
                for (int j = 0; j < listOfPlayers.get(i).getCurrentGameStats().length; j++) {
                    System.out.printf("%-4.2f     ",statSheet[i][j]);
                }
                System.out.println();
            }
            teamPage(teamIndex);
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team.");
        }

    }

    //starts a new game
    //MODIFIES: listOfGames
    //EFFECTS: adds a game to listOfGames and stores opponent name and players playing to game based on userInput
    private void newGame(int teamIndex) {
        try {
            coach.setChanged(true);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter opponent name: ");
            String opponent = scanner.nextLine();
            coach.getListOfTeam().get(teamIndex).addGame(new Game(opponent));

            //this is used ot get the last index of the list of games of a team where the new game was added
            int sizeOfGamesList = coach.getListOfTeam().get(teamIndex).getListOfGames().size();
            //list of games of current team
            List<Game> gamesList =  coach.getListOfTeam().get(teamIndex).getListOfGames();
            System.out.println("Enter the players playing. Enter 0 when you're done.");

            List<Player> listOfPlayers = coach.getListOfTeam().get(teamIndex).getListOfPlayers();
            printListOfPlayers(listOfPlayers);
            addPlayerToCurrentGame(gamesList, sizeOfGamesList, listOfPlayers);
            startGame(teamIndex);
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team.");
            titlePage();
        }

    }

    private void addPlayerToCurrentGame(List<Game> gamesList, int sizeOfGamesList, List<Player> listOfPlayers) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            userInput = scanner.nextInt();
            if (userInput == 0) {
                break;
            }
            gamesList.get(sizeOfGamesList - 1).addPlayer(listOfPlayers.get(userInput - 1));
            listOfPlayers.get(userInput - 1).addGamesPlayed();
            System.out.println(listOfPlayers.get(userInput - 1).getName() + " added to the list of players playing"
                    + " this game.");
        }
    }

    //EFFECTS: print the list of players
    private void printListOfPlayers(List<Player> listOfPlayers) {
        int number = 1;
        for (Player player : listOfPlayers) {
            System.out.println(number + ". " + player.getName());
            number++;
        }
    }


    //start game after inputting players
    //EFFECTS: displays options for what you can do in a game
    //MODIFIES: player stats
    private void startGame(int teamIndex) {
        try {
            System.out.println("GAME ON!!!");
            Scanner scanner = new Scanner(System.in);
            //this is used to get the last index of the list of games of a team where the new game was added
            int sizeOfGamesList = coach.getListOfTeam().get(teamIndex).getListOfGames().size();
            //get the current team that is playing the game
            Team team = coach.getListOfTeam().get(teamIndex);
            //this is used to get the players currently playing in the game
            List<Player> playing = team.getListOfGames().get(sizeOfGamesList - 1).getPlayers();
            System.out.println("Choose player to add stat. Enter 0 to end game and view stats");
            for (int i = 1; i < playing.size() + 1; i++) {
                System.out.println(i + ". " + playing.get(i - 1).getName());
            }

            int userInput = scanner.nextInt();

            if (userInput > 0 && userInput <= playing.size()) {
                displayPlayers(playing.get(userInput - 1),teamIndex);
            }
            if (userInput == 0) {
                setFinalScore(teamIndex);
                endGame(teamIndex);
                teamPage(teamIndex);
            }
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team.");
            titlePage();
        }


    }


    //EFFECTS: asks user to set the final score
    //MODIFIES: games final score
    private void setFinalScore(int teamIndex) {
        try {
            List<Game> listOfGames = coach.getListOfTeam().get(teamIndex).getListOfGames();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter final score: ");
            listOfGames.get(listOfGames.size() - 1).setFinalScore(scanner.nextLine());
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team");
            titlePage();
        }

    }

    //EFFECTS: ends the game and fills the game stat sheet then prints it out
    //MODIFIES: game stat sheet
    private void endGame(int teamIndex) {
        try {
            List<Game> listOfGames = coach.getListOfTeam().get(teamIndex).getListOfGames();
            List<Player> listOfPlayers = listOfGames.get(listOfGames.size() - 1).getPlayers();
            listOfGames.get(listOfGames.size() - 1).fillStatSheet();
            //variables for player stats
            String[] statVariables = {"Player","Points","Rebounds","Assists","2PM","2PA","2P%","3PM","3PA","3P%","FTM",
                    "FTA","FT%","Fouls"};
            for (String variables: statVariables) {
                System.out.printf("%-10s", variables);
            }
            System.out.println();

            printEndGameStat(listOfPlayers, listOfGames);
            reset(listOfPlayers);
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have a team.");
        }

    }

    //EFFECTS: prints out the end game stats for a specific game
    private void printEndGameStat(List<Player> listOfPlayers, List<Game> listOfGames) {
        for (int i = 0;i < listOfPlayers.size();i++) {
            System.out.printf("%-9s ",listOfPlayers.get(i).getName());
            for (int j = 0; j < listOfPlayers.get(i).getCurrentGameStats().length; j++) {
                if (listOfGames.get(listOfGames.size() - 1).getGameStat()[i][j] > 10) {
                    System.out.printf("%-4.2f     ",listOfGames.get(listOfGames.size() - 1).getGameStat()[i][j]);
                } else {
                    System.out.printf("%-3.2f      ",listOfGames.get(listOfGames.size() - 1).getGameStat()[i][j]);
                }
            }
            System.out.println();
        }
    }

    //EFFECTS: fills the overall stats of the player then resets their current game stats to 0
    //MODIFIES player currentGame stats
    private void reset(List<Player> listOfPlayers) {
        for (Player player : listOfPlayers) {
            player.fillStats();
            player.reset();
        }
    }


    //EFFECTS: prints out user option for adding stats
    //adding points to a player increments the players made and players attempt
    private void displayPlayers(Player player, int teamIndex) {
        System.out.println("Select which stat to add. (Adding +1,2,3 Points also adds an attempt).Enter 0 to go back.");
        System.out.println("1. +1 Point\n2. +2 Points\n3. +3 Points\n4. +1 Rebound\n5. +1 Assist\n"
                            + "6. +1 3 Point Attempt\n7. +1 2 Point Attempt\n8. +1 Free Throw Attempt\n9. +1 Foul");
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        addSpecifiedStat(player,teamIndex,userInput);
    }

    @SuppressWarnings("methodlength")
    //EFFECTS: adds stats to player depending on userInput
    //MODIFIES: player stats
    private void addSpecifiedStat(Player player,int teamIndex,int userInput) {
        switch (userInput) {
            case 0:
                startGame(teamIndex);
            case 1:
                addOnePoint(player,teamIndex);
                break;
            case 2:
                addTwoPoints(player,teamIndex);
                break;
            case 3:
                addThreePoints(player,teamIndex);
                break;
            case 4:
                player.addRebound();
                break;
            case 5:
                player.addAssist();
                break;
            case 6:
                player.addThreePointAttempt();
                break;
            case 7:
                player.addTwoPointAttempts();
                break;
            case 8:
                player.addFreeThrowAttempts();
                break;
            case 9:
                player.addFouls();
                break;
        }
        startGame(teamIndex);
    }

    //EFFECTS: adds one point, free throw attempt, free throws made, to chosen player
    //MODIFIES: player stat
    private void addOnePoint(Player player,int teamIndex) {
        player.addOnePoint();
        player.addFreeThrowsMade();
        player.addFreeThrowAttempts();
        startGame(teamIndex);
    }

    //EFFECTS: adds two points, two point attempt, two pointers made, to chosen player
    //MODIFIES: player stat
    private void addTwoPoints(Player player, int teamIndex) {
        player.addTwoPoints();
        player.addTwoPointersMade();
        player.addTwoPointAttempts();
        startGame(teamIndex);
    }

    //EFFECTS: adds three points, three point attempt, three point made, to chosen player
    //MODIFIES: player stat
    private void addThreePoints(Player player, int teamIndex) {
        player.addThreePoints();
        player.addThreePointersMade();
        player.addThreePointAttempt();
        startGame(teamIndex);
    }


    //EFFECTS: exits the program
    private void quit() {
        System.exit(0);
    }


}



