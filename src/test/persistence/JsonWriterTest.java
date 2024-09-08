package persistence;

import model.Coach;
import model.Game;
import model.Player;
import model.Team;
import model.exceptions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Coach coach = new Coach();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Coach coach = new Coach();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCoach.json");
            writer.open();
            writer.write(coach);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCoach.json");
            coach = reader.read();
            assertEquals("", coach.getName());
            assertEquals(0, coach.getListOfTeam().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (TeamDoesNotExistException e) {
            fail("Should not reach this code");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Team team1 = new Team("testTeam1");
            Team team2 = new Team("testTeam1");
            Player player = new Player("testPlayer");
            team1.addPlayer(player);
            Game game = new Game("testOpponent");
            game.addPlayer(player);
            player.addThreePoints();
            player.addFreeThrowsMade();
            player.addFouls();
            game.setFinalScore("9-9");
            game.fillStatSheet();
            team1.addGame(game);
            Coach coach = new Coach();
            coach.setName("testCoach");
            coach.addTeam(team1);
            coach.addTeam(team2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCoach.json");
            writer.open();
            writer.write(coach);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCoach.json");
            coach = reader.read();
            assertEquals("testCoach", coach.getName());
            List<Team> teams = coach.getListOfTeam();
            assertEquals(2, teams.size());
            assertEquals("testTeam1",coach.getListOfTeam().get(0).getName());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (TeamDoesNotExistException e) {
            fail("Should not reach this code");
        } catch (SameNameException e) {
            System.out.println("Players have same name that already exists in team.");
        } catch (MaxLengthExceededException e) {
            System.out.println("Max length exceeded");
        } catch (SameTeamNameException e) {
            System.out.println("Same team exists.");
        } catch (EmptyStringException e) {
            System.out.println("Empty String not accepted");
        }
    }

}
