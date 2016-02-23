package main.java.team.model;

import java.util.List;

interface TeamDAO {
	/** Add new team */
	public void addTeam(Team team);
	/** Update team */
	public void updateTeam(Team oldTeam, Team newTeam);
	/** Remove team */
	public void removeTeam(Team team);
	/** Return list of teams */
	public List<Team> getAllTeams();
	/** Return team from list of teams */
	public Team getTeam(int index);
	/** Return one team from all teams by ID */
	public Team findById(int id);
	/** Return next ID to set (max of all teams +1) */
	public int getNextID();
}
