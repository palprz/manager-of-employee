package main.java.project.hibernate;

import java.util.List;

import main.java.employee.model.Employee;
import main.java.project.model.Project;
import main.java.team.hibernate.HibernateTeam;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class PrepareSynchTeam implements IPrepareSynch{

	/* (non-Javadoc)
	 * @see main.java.project.hibernate.IPrepareSynch#prepareSynch()
	 */
	@Override
	public void prepareSynch() {
		HibernateTeam hibTeam = new HibernateTeam();
		List<Team> oldTeams = hibTeam.readAll();
		List<Team> newTeams = TeamDAOImpl.getInstance().getAllTeams();
		
		for (Team newTeam : newTeams) {
			Team oldTeam = getDuplicateID(newTeam.getId(), oldTeams);
			if (oldTeam != null) {
				if (isChanged(newTeam, oldTeam)) {
					hibTeam.update(newTeam);
				}
				//Remove team which is in newTeams
				//after saving and updating we need to remove all teams from this list
				oldTeams.remove(oldTeam);
			} else {
				hibTeam.save(newTeam);
			}
		}
		
		for (Team oldTeam : oldTeams) {
			hibTeam.delete(oldTeam);
		}	
	}

	/**
	 * Search teams from database for get team with the same ID like the new one team.
	 * @param id
	 * @param oldTeams
	 * @return Team
	 */
	private Team getDuplicateID(int id, List<Team> oldTeams) {
		for (Team oldTeam : oldTeams) {
			if (oldTeam.getId() == id) {
				return oldTeam;
			}
		}
		
		return null;
	}

	/**
	 * Check if there is any changes between two teams.
	 * @param newTeam
	 * @param oldTeam
	 * @return boolean
	 */
	private boolean isChanged(Team newTeam, Team oldTeam) {
		if (!newTeam.getName().equals(oldTeam.getName())) {
			return true;
		} 

		Project oldProject = oldTeam.getProject();
		Project newProject = newTeam.getProject();
		boolean isBothNull = oldProject == null && newProject == null;
		boolean isOneNull = oldProject == null || newProject == null;
		if (!isBothNull && (isOneNull || oldProject.getId() != newProject.getId())) {
			return true;
		}
		
		Employee oldTeamleader = oldTeam.getTeamLeader();
		Employee newTeamLeader = newTeam.getTeamLeader();
		isBothNull = oldTeamleader == null && newTeamLeader == null;
		isOneNull = oldTeamleader == null || newTeamLeader == null;
		if (!isBothNull && (isOneNull || oldTeamleader.getId() != newTeamLeader.getId())) {
			return true;
		}
				
		return false;
	}

}
