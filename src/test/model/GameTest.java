package model;

import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for Game class
public class GameTest {

    Game game;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void runBefore() {
        game = new Game("Poodle");
        try {
            player1 = new Player("Steph");
            player2 = new Player("Michael");
            player3 = new Player("Shaq");

            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addPlayer(player3);

            player1.addGamesPlayed();
            player2.addGamesPlayed();
            player3.addGamesPlayed();

            player1.addThreePoints();
            player1.addThreePointersMade();
            player1.addThreePointAttempt();
            player2.addTwoPoints();
            player2.addTwoPointersMade();
            player2.addTwoPointAttempts();
            player3.addOnePoint();
            player3.addFreeThrowsMade();
            player3.addFreeThrowAttempts();

            player1.addRebound();
            player2.addAssist();
            player3.addFouls();

            player1.addThreePointAttempt();
            player1.addThreePointAttempt();
            player1.addThreePointAttempt();
            player2.addTwoPointAttempts();
            player3.addFreeThrowAttempts();

            player1.addFouls();

            player1.fillStats();
            player2.fillStats();
            player3.fillStats();
        } catch (MaxLengthExceededException e) {
            System.out.println("Max length exceeded.");
        } catch (EmptyStringException e) {
            System.out.println("Empty String not accepted");
        }

    }

    @Test
    void testGame() {
        assertEquals("Poodle", game.getOpponentName());
    }

    @Test
    void testSetAndGetScore() {
        game.setFinalScore("90-87");
        assertEquals("90-87",game.getFinalScore());
    }

    @Test
    void testGetGameStatAndFillStatSheet() {

        game.fillStatSheet();

        assertEquals(3, game.getGameStat()[0][0]);
        assertEquals(1, game.getGameStat()[0][1]);
        assertEquals(0, game.getGameStat()[0][2]);
        assertEquals(0, game.getGameStat()[0][3]);
        assertEquals(0, game.getGameStat()[0][4]);
        assertEquals(0, game.getGameStat()[0][5]);
        assertEquals(1, game.getGameStat()[0][6]);
        assertEquals(4, game.getGameStat()[0][7]);
        assertEquals(0.25, game.getGameStat()[0][8]);
        assertEquals(0, game.getGameStat()[0][9]);
        assertEquals(0, game.getGameStat()[0][10]);
        assertEquals(0, game.getGameStat()[0][11]);
        assertEquals(1, game.getGameStat()[0][12]);

        assertEquals(2, game.getGameStat()[1][0]);
        assertEquals(0, game.getGameStat()[1][1]);
        assertEquals(1, game.getGameStat()[1][2]);
        assertEquals(1, game.getGameStat()[1][3]);
        assertEquals(2, game.getGameStat()[1][4]);
        assertEquals(0.5, game.getGameStat()[1][5]);
        assertEquals(0, game.getGameStat()[1][6]);
        assertEquals(0, game.getGameStat()[1][7]);
        assertEquals(0, game.getGameStat()[1][8]);
        assertEquals(0, game.getGameStat()[1][9]);
        assertEquals(0, game.getGameStat()[1][10]);
        assertEquals(0, game.getGameStat()[1][11]);
        assertEquals(0, game.getGameStat()[1][12]);

        assertEquals(1, game.getGameStat()[2][0]);
        assertEquals(0, game.getGameStat()[2][1]);
        assertEquals(0, game.getGameStat()[2][2]);
        assertEquals(0, game.getGameStat()[2][3]);
        assertEquals(0, game.getGameStat()[2][4]);
        assertEquals(0, game.getGameStat()[2][5]);
        assertEquals(0, game.getGameStat()[2][6]);
        assertEquals(0, game.getGameStat()[2][7]);
        assertEquals(0, game.getGameStat()[2][8]);
        assertEquals(1, game.getGameStat()[2][9]);
        assertEquals(2, game.getGameStat()[2][10]);
        assertEquals(0.5, game.getGameStat()[2][11]);
        assertEquals(1, game.getGameStat()[2][12]);

    }

    @Test
    void testGetPlayers() {
        assertEquals(player1, game.getPlayers().get(0));
        assertEquals(player2, game.getPlayers().get(1));
        assertEquals(player3, game.getPlayers().get(2));
    }
}

