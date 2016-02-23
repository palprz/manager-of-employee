package main.java.launch;

import java.util.List;

import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

/**
 * Class open for new conditions
 */
public class ValidateTeamUtil {

	/**
	 * @param oldTeam if we add, set <code>null</code>
	 * @param newTeam
	 * @return <code>true</code> if there aren't duplicates name of team and name is set.
	 */
	public boolean validate(Team oldTeam, Team newTeam) {
		List<Team> teams = TeamDAOImpl.getInstance().getAllTeams();
		removeTeamFromList(oldTeam, teams);
		
		if (checkName(teams, newTeam)) {
			return false;
		}
		
		return true;
	}

	/**
	 * If there is old team just remove from list to not checking the same data for
	 * false duplicates
	 * @param oldTeam
	 * @param teams
	 */	
	private void removeTeamFromList(Team oldTeam, List<Team> teams) {
		for (int i = 0; i < teams.size(); i++) {
			if (oldTeam == null) {
				break;
			}
			
			if (teams.get(i).getId() == oldTeam.getId()) {
				teams.remove(i);
			}
		}
	}

	private boolean checkName(List<Team> teams, Team newTeam) {
		if (newTeam.getName().length() <= 0) {
			return true;
		}
		
		for (Team team : teams) {
			if (team.getName().equals(newTeam.getName())) {
				return true;
			}
		}
		return false;
	}
	
}
