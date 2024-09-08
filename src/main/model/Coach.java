package model;

import model.exceptions.EmptyStringException;
import model.exceptions.SameNameException;
import model.exceptions.SameTeamNameException;
import model.exceptions.TeamDoesNotExistException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.List;

//this class represents a coach with a name
// and a list of teams it is coaching
public class Coach implements Writable {

    boolean changed = false;
    List<Team> teams; // list of teams
    String name;    //team name

    //EFFECTS: name for coach is set to ""; and create a new instance of Linked List
    public Coach() {
        teams = new LinkedList<>();
        name = "";
    }

    //EFFECTS: adds team to teams list
    //MODIFIES: this
    public void addTeam(Team team) throws SameTeamNameException {
        for (Team t : teams) {
            if (t.getName().equals(team.getName())) {
                throw new SameTeamNameException();
            }
        }
        teams.add(team);
        EventLog.getInstance().logEvent(new Event("Added team: " + team.getName() + " to " + this.name + "."));
    }

    //REQUIRES: teams.size() > 0
    public List<Team> getListOfTeam() throws TeamDoesNotExistException {
        if (teams.size() == 0) {
            throw new TeamDoesNotExistException();
        }
        return this.teams;
    }

    public void setName(String name) throws EmptyStringException {
        if (name.trim().length() == 0) {
            throw new EmptyStringException();
        }
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Coaches name set to: " + name + "."));
    }

    public void setChanged(boolean b) {
        changed = b;
        EventLog.getInstance().logEvent(new Event("User " + this.name + " has been changed."));
    }

    public boolean getChanged() {
        return changed;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("teams", teamsToJson());
        return json;
    }

    public JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public void printEvents(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }
}
