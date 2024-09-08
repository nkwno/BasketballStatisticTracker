package model;


import model.exceptions.EmptyStringException;
import model.exceptions.SameTeamNameException;
import model.exceptions.TeamDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

//Test for Coach class
public class CoachTest {

    Coach coach;
    Team team1;
    Team team2;
    Team team3;
    Team team4;

    @BeforeEach
    void runBefore() {
        coach = new Coach();
        team1 = new Team("Packers");
        team2 = new Team("Ravens");
        team3 = new Team("Sharks");
        team4 = new Team("Packers");
    }

    @Test
    void testSetChanged() {
        try {
            coach.setName("Squid");
            assertEquals("Squid",coach.getName());
            coach.setChanged(true);
            assertTrue(coach.getChanged());
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testAddTeamAndGetListOfTeam() {

        try {
            coach.addTeam(team1);
            coach.addTeam(team2);
            coach.addTeam(team3);
        } catch (SameTeamNameException e) {
            fail();
        }

        try {
            coach.addTeam(team1);
            coach.addTeam(team2);
            coach.addTeam(team4);
            fail();
        } catch (SameTeamNameException e) {

        }


        try {
            assertEquals(team1,coach.getListOfTeam().get(0));
        } catch (TeamDoesNotExistException e) {
            fail("Should not reach this code");
        }

        try {
            assertEquals(team2,coach.getListOfTeam().get(1));
        } catch (TeamDoesNotExistException e) {
            fail("Should not reach this code");
        }

        try {
            assertEquals(team3,coach.getListOfTeam().get(2));
        } catch (TeamDoesNotExistException e) {
            fail("Should not reach this code");
        }

    }

    @Test
    void testSetNameAndGetName() {
        try {
            coach.setName("Squid");
            assertEquals("Squid",coach.getName());
        } catch (EmptyStringException e) {
            fail();
        }

        try {
            coach.setName("");
            assertEquals("Squid",coach.getName());
            fail();
        } catch (EmptyStringException e) {

        }
    }

    @Test
    void testJson() {
        try {
            coach.setName("Squid");
            System.out.println(coach.toJson().toString());
        } catch (EmptyStringException e) {
            fail();
        }

    }



}
