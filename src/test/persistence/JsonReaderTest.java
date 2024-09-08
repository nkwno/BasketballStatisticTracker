package persistence;

import model.Coach;
import model.Team;

import model.exceptions.TeamDoesNotExistException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Coach coach = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCoach() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCoach.json");
        try {
            Coach coach = reader.read();
            assertEquals("", coach.getName());
            assertEquals(0, coach.getListOfTeam().size());
            fail("Should not read this code");
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (TeamDoesNotExistException e) {
            System.out.println("Coach does not have team.");
        }
    }

    @Test
    void testReaderCoach() {
        JsonReader reader = new JsonReader("./data/testReaderCoach.json");
        try {
            Coach coach = reader.read();
            assertEquals("Nao", coach.getName());
            List<Team> teams = coach.getListOfTeam();
            assertEquals(1, teams.size());
            assertEquals("packers", teams.get(0).getName());
            assertEquals(2, teams.get(0).getListOfPlayers().size());
            assertEquals(1, teams.get(0).getListOfGames().size());
            assertEquals(1, teams.get(0).getListOfGames().get(0).getGameStat()[0][0]);
            assertEquals(3, teams.get(0).getListOfGames().get(0).getGameStat()[0][12]);
            assertEquals("9-9", teams.get(0).getListOfGames().get(0).getFinalScore());
            assertEquals("testOpponent", teams.get(0).getListOfGames().get(0).getOpponentName());
//            assertEquals(3.0, teams.get(0).getListOfGames().get(0).getPlayers().get(0).getStats()[8]);

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (TeamDoesNotExistException e) {
            System.out.println("Team does not exist in coach");
        }
    }
}