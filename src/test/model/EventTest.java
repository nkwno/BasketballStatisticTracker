package model;

import model.exceptions.EmptyStringException;
import model.exceptions.MaxLengthExceededException;
import model.exceptions.SameNameException;
import model.exceptions.SameTeamNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Sensor open at door");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e.getDescription());
		assertEquals(d, e.getDate());
	}

    @Test
    public void testCoachEvent() {
        Coach coach = new Coach();
        try {
            List<String> l = new ArrayList<>();
            coach.setName("Nao");
            coach.setChanged(true);
            coach.addTeam(new Team("Packers"));
            EventLog el = EventLog.getInstance();
            for (Event next : el) {
                l.add(next.getDescription());
            }
            assertTrue(l.contains("Coaches name set to: Nao."));
            assertTrue(l.contains("Added team: Packers to Nao."));
            assertTrue(l.contains("User Nao has been changed."));
        } catch (EmptyStringException e) {
            fail();
        } catch (SameTeamNameException e) {
            fail();
        }
    }

    @Test
    public void testPlayerEvent() {
        try {
            Player testPlayer = new Player("Test");
            testPlayer.addOnePoint();
            testPlayer.addTwoPoints();
            testPlayer.addThreePoints();
            testPlayer.addRebound();
            testPlayer.addAssist();
            testPlayer.addTwoPointersMade();
            testPlayer.addTwoPointAttempts();
            testPlayer.addThreePointAttempt();
            testPlayer.addThreePointersMade();
            testPlayer.addFreeThrowsMade();
            testPlayer.addFreeThrowAttempts();
            testPlayer.addFouls();
            testPlayer.addGamesPlayed();
            testPlayer.fillStats();
            List<String> l = new ArrayList<>();
            EventLog el = EventLog.getInstance();
            for (Event next : el) {
                l.add(next.getDescription());
            }
            assertTrue(l.contains("Added one point to: Test."));
            assertTrue(l.contains("Added two points to: Test."));
            assertTrue(l.contains("Added three points to: Test."));
            assertTrue(l.contains("Added one rebound to: Test."));
            assertTrue(l.contains("Added one two point make to: Test."));
            assertTrue(l.contains("Added one three point make to: Test."));
            assertTrue(l.contains("Added one three point attempt to: Test."));
            assertTrue(l.contains("Added one free throw made to: Test."));
            assertTrue(l.contains("Added one free throw attempt to: Test."));
            assertTrue(l.contains("Added one foul to: Test."));
            assertTrue(l.contains("Added one games played to: Test."));
            assertTrue(l.contains("Filled stat sheet for Test."));
        } catch (MaxLengthExceededException e) {
            fail();
        } catch (EmptyStringException e) {
            fail();
        }


    }

    @Test
    public void testGameEvent() {
        try {
            Player player = new Player("Player");
            Game game = new Game("Opponent");
            game.setOpponent("Opponent");
            game.setFinalScore("9-9");
            game.addPlayer(player);
            game.fillStatSheet();
            List<String> l = new ArrayList<>();
            EventLog el = EventLog.getInstance();
            for (Event next : el) {
                l.add(next.getDescription());
            }
            assertTrue(l.contains("Final score against Opponent set to: 9-9."));
            assertTrue(l.contains("Opponent set to: Opponent."));
            assertTrue(l.contains("Added Player to game against Opponent."));
            assertTrue(l.contains("Stat sheet against Opponent filled."));
        } catch (MaxLengthExceededException e) {
            fail();
        } catch (EmptyStringException e) {
            fail();
        }

    }

    @Test
    public void testTeamEvent() {
        try {
            Team team = new Team("Team");
            Player player = new Player("Player");
            Game game = new Game("Opponent");
            team.addPlayer(player);
            team.addGame(game);
            List<String> l = new ArrayList<>();
            EventLog el = EventLog.getInstance();
            for (Event next : el) {
                l.add(next.getDescription());
            }
            assertTrue(l.contains("Player added to team: Team."));
            assertTrue(l.contains("New game added to the list of games."));

        } catch (MaxLengthExceededException e) {
            fail();
        } catch (EmptyStringException e) {
            fail();
        } catch (SameNameException e) {
            fail();
        }
    }

    @Test
    public void testLogEvent() {
        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

    }
	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
	}
}
