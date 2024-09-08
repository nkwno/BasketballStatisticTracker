package model;


import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for Player class
public class PlayerTest {

    Player player;

    @BeforeEach
    void runBefore() {
        try {
            player = new Player("Ena");
        } catch (MaxLengthExceededException e) {
            fail();
            System.out.println("Max length exceeded");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testException() {
        try {
            player = new Player("  ");
            fail();
        } catch (MaxLengthExceededException e) {
            fail();
            System.out.println("Max length exceeded");
        } catch (EmptyStringException e) {

        }

        try {
            player = new Player("12345678910");
            fail();
        } catch (MaxLengthExceededException e) {

        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testPlayer() {
        assertEquals(0,player.getAssists());
        assertEquals(0,player.getPoints());
        assertEquals(0,player.getFouls());
        assertEquals(0,player.getTwoPointersMade());
        assertEquals(0,player.getRebounds());
        assertEquals(0,player.getGamesPlayed());
        assertEquals(0,player.getThreePointAttempts());
        assertEquals(0,player.getThreePointersMade());
        assertEquals(0,player.getFreeThrowAttempts());
        assertEquals(0,player.getFreeThrowsMade());
        assertEquals(0,player.getTwoPointAttempts());
    }

    @Test
    void testGetName() {
        assertEquals("Ena", player.getName());
    }

    @Test
    void testAddPoints() {
        player.addOnePoint();
        assertEquals(1, player.getCurrentGameStats()[0]);
        assertEquals(0, player.getCurrentGameStats()[1]);
        assertEquals(0, player.getCurrentGameStats()[2]);
        assertEquals(0, player.getCurrentGameStats()[3]);
        assertEquals(0, player.getCurrentGameStats()[4]);
        assertEquals(0, player.getCurrentGameStats()[5]);
        assertEquals(0, player.getCurrentGameStats()[6]);
        assertEquals(0, player.getCurrentGameStats()[7]);
        assertEquals(0, player.getCurrentGameStats()[8]);
        assertEquals(0, player.getCurrentGameStats()[9]);
        assertEquals(0, player.getCurrentGameStats()[10]);
        assertEquals(0, player.getCurrentGameStats()[11]);
        assertEquals(0, player.getCurrentGameStats()[12]);
        player.addTwoPoints();
        assertEquals(3, player.getCurrentGameStats()[0]);
        assertEquals(0, player.getCurrentGameStats()[1]);
        assertEquals(0, player.getCurrentGameStats()[2]);
        assertEquals(0, player.getCurrentGameStats()[3]);
        assertEquals(0, player.getCurrentGameStats()[4]);
        assertEquals(0, player.getCurrentGameStats()[5]);
        assertEquals(0, player.getCurrentGameStats()[6]);
        assertEquals(0, player.getCurrentGameStats()[7]);
        assertEquals(0, player.getCurrentGameStats()[8]);
        assertEquals(0, player.getCurrentGameStats()[9]);
        assertEquals(0, player.getCurrentGameStats()[10]);
        assertEquals(0, player.getCurrentGameStats()[11]);
        assertEquals(0, player.getCurrentGameStats()[12]);
        player.addThreePoints();
        assertEquals(6, player.getCurrentGameStats()[0]);
        assertEquals(0, player.getCurrentGameStats()[1]);
        assertEquals(0, player.getCurrentGameStats()[2]);
        assertEquals(0, player.getCurrentGameStats()[3]);
        assertEquals(0, player.getCurrentGameStats()[4]);
        assertEquals(0, player.getCurrentGameStats()[5]);
        assertEquals(0, player.getCurrentGameStats()[6]);
        assertEquals(0, player.getCurrentGameStats()[7]);
        assertEquals(0, player.getCurrentGameStats()[8]);
        assertEquals(0, player.getCurrentGameStats()[9]);
        assertEquals(0, player.getCurrentGameStats()[10]);
        assertEquals(0, player.getCurrentGameStats()[11]);
        assertEquals(0, player.getCurrentGameStats()[12]);
    }

    @Test
    void testAddStat() {
        player.addAssist();
        player.addRebound();
        player.addThreePointersMade();
        player.addThreePointAttempt();
        player.addFreeThrowsMade();
        player.addFreeThrowAttempts();
        player.addTwoPointersMade();
        player.addTwoPointAttempts();
        player.addFouls();

        assertEquals(0, player.getCurrentGameStats()[0]);
        assertEquals(1, player.getCurrentGameStats()[1]);
        assertEquals(1, player.getCurrentGameStats()[2]);
        assertEquals(1, player.getCurrentGameStats()[3]);
        assertEquals(1, player.getCurrentGameStats()[4]);
        assertEquals(1, player.getCurrentGameStats()[5]);
        assertEquals(1, player.getCurrentGameStats()[6]);
        assertEquals(1, player.getCurrentGameStats()[7]);
        assertEquals(1, player.getCurrentGameStats()[8]);
        assertEquals(1, player.getCurrentGameStats()[9]);
        assertEquals(1, player.getCurrentGameStats()[10]);
        assertEquals(1, player.getCurrentGameStats()[11]);
        assertEquals(1, player.getCurrentGameStats()[12]);

    }

    @Test
    void testFillStats() {
        player.addGamesPlayed();
        player.addAssist();
        player.addRebound();
        player.addThreePointersMade();
        player.addThreePointAttempt();
        player.addThreePoints();
        player.addFreeThrowsMade();
        player.addFreeThrowAttempts();
        player.addOnePoint();
        player.addTwoPointersMade();
        player.addTwoPointAttempts();
        player.addTwoPointAttempts();
        player.addTwoPoints();
        player.addFouls();
        player.fillStats();

        assertEquals(1,player.getStats()[0]);
        assertEquals(6,player.getStats()[1]);
        assertEquals(1,player.getStats()[2]);
        assertEquals(1,player.getStats()[3]);
        assertEquals(1,player.getStats()[4]);
        assertEquals(2,player.getStats()[5]);
        assertEquals(0.5,player.getStats()[6]);
        assertEquals(1,player.getStats()[7]);
        assertEquals(1,player.getStats()[8]);
        assertEquals(1,player.getStats()[9]);
        assertEquals(1,player.getStats()[10]);
        assertEquals(1,player.getStats()[11]);
        assertEquals(1,player.getStats()[12]);
        assertEquals(1,player.getStats()[13]);
        assertEquals(1,player.getStats()[14]);

    }

    @Test
    void testReset() {
        player.addAssist();
        player.addRebound();
        player.addThreePointersMade();
        player.addThreePointAttempt();
        player.addThreePoints();
        player.addFreeThrowsMade();
        player.addFreeThrowAttempts();
        player.addOnePoint();
        player.addTwoPointersMade();
        player.addTwoPointAttempts();
        player.addTwoPointAttempts();
        player.addTwoPoints();
        player.addFouls();
        player.reset();

        assertEquals(0,player.getAssists());
        assertEquals(0,player.getRebounds());
        assertEquals(0,player.getThreePointersMade());
        assertEquals(0,player.getThreePointAttempts());
        assertEquals(0,player.getPoints());
        assertEquals(0,player.getFreeThrowsMade());
        assertEquals(0,player.getFreeThrowAttempts());
        assertEquals(0,player.getTwoPointersMade());
        assertEquals(0,player.getTwoPointAttempts());
        assertEquals(0,player.getFouls());
    }

    @Test
    void testJson() {
        System.out.println(player.toJson().toString());
    }





}
