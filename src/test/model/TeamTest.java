package model;

import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import model.exceptions.SameNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for Team class
public class TeamTest {

    Team team;
    Player player1;
    Player player2;
    Player player3;
    Game game1;
    Game game2;
    Game game3;

    @BeforeEach
    void runBefore() {
        try {
            team = new Team("Packers");
            player1 = new Player("Steph");
            player2 = new Player("Michael");
            player3 = new Player("Shaq");

            team.addPlayer(player1);
            team.addPlayer(player2);
            team.addPlayer(player3);

            game1 = new Game("Warriors");
            game2 = new Game("Nets");
            game3 = new Game("Knicks");
            team.addGame(game1);
            team.addGame(game2);
            team.addGame(game3);

            game1.addPlayer(player1);
            game2.addPlayer(player2);
            game3.addPlayer(player3);
        } catch (MaxLengthExceededException e) {
            fail();
            System.out.println("Max name length exceeded");
        } catch (SameNameException e) {
            fail();
            System.out.println("same name exists in team already");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testSameNameException() {
        try {
            team = new Team("Packers");
            player1 = new Player("Steph");
            player2 = new Player("Steph");
            player3 = new Player("Shaq");

            team.addPlayer(player1);
            team.addPlayer(player2);
            team.addPlayer(player3);

            game1 = new Game("Warriors");
            game2 = new Game("Nets");
            game3 = new Game("Knicks");
            team.addGame(game1);
            team.addGame(game2);
            team.addGame(game3);

            game1.addPlayer(player1);
            game2.addPlayer(player2);
            game3.addPlayer(player3);
        } catch (MaxLengthExceededException e) {
            fail();
            System.out.println("Max name length exceeded");
        } catch (SameNameException e) {

        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testEmptyStringException() {
        try {
            team = new Team("Packers");
            player1 = new Player("");
            player2 = new Player("Michael");
            player3 = new Player("Shaq");

            team.addPlayer(player1);
            team.addPlayer(player2);
            team.addPlayer(player3);

            game1 = new Game("Warriors");
            game2 = new Game("Nets");
            game3 = new Game("Knicks");
            team.addGame(game1);
            team.addGame(game2);
            team.addGame(game3);

            game1.addPlayer(player1);
            game2.addPlayer(player2);
            game3.addPlayer(player3);
        } catch (MaxLengthExceededException e) {
            fail();
            System.out.println("Max name length exceeded");
        } catch (SameNameException e) {
            fail();
            System.out.println("same name exists in team already");
        } catch (EmptyStringException e) {

        }
    }

    @Test
    void testGetListOfPlayersAndGetListOfGames() {
        assertEquals(player1, team.getListOfPlayers().get(0));
        assertEquals(player2, team.getListOfPlayers().get(1));
        assertEquals(player3, team.getListOfPlayers().get(2));
        assertEquals(game1, team.getListOfGames().get(0));
        assertEquals(game2, team.getListOfGames().get(1));
        assertEquals(game3, team.getListOfGames().get(2));
    }

    @Test
    void testGetName() {
        assertEquals("Packers", team.getName());
    }
}
