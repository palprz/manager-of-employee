package main.java.team.model;

import java.util.ArrayList;
import java.util.List;

import main.java.employee.model.Employee;
import main.java.employee.model.EmployeeDAOImpl;
import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.team.hibernate.HibernateTeam;

public class TeamDAOImpl implements TeamDAO{
	
	/** Singleton - instance of TeamDAOImpl */
	private static final TeamDAOImpl INSTANCE = new TeamDAOImpl();
	private List<Team> teams;

	/**
	 * Return instance of class
	 * @return INSTANCE
	 */
    public static TeamDAOImpl getInstance() {
        return INSTANCE;
    }

	/**
	 * Singleton constructor
	 */
	private TeamDAOImpl() {
		teams = new ArrayList<>();
		HibernateTeam hibTeam = new HibernateTeam();
		List<Team> teams = hibTeam.readAll();
		addTeams(teams);
	}
	
	/**
	 * Add new team
	 * @param team
	 */
	public void addTeam(Team team) {
		teams.add(team);
		
		//Add into other model table
		ProjectDAOImpl.getInstance().addTeam(team);
	}

	/**
	 * Add employee into team.<br>
	 * Used from Employee table.
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		int teamID = employee.getTeam().getId();
		Team team = findById(teamID);
		team.getEmployees().add(employee);
	}
	
	/**
	 * Add list of teams
	 * @param teams
	 */
	public void addTeams(List<Team> teams) {
		for(Team team : teams) {
			this.teams.add(team);
		}
	}
	
	/**
	 * Update team
	 * @param oldTeam
	 * @param newTeam
	 */
	public void updateTeam(Team oldTeam, Team newTeam) {
		teams.remove(oldTeam);
		teams.add(newTeam);

		//Change in project table and employee table
		boolean isChangedNameOfTeam = !(oldTeam.getName().equals(newTeam.getName()));
		boolean isChangedProject = oldTeam.getProject().getId() != newTeam.getProject().getId();
		
		if (isChangedNameOfTeam || isChangedProject) {
			ProjectDAOImpl.getInstance().updateTeam(oldTeam, newTeam);
			EmployeeDAOImpl.getInstance().updateTeamAndProject(newTeam);
		}
	}
	
	/**
	 * Update project in team.<br>
	 * Used from Project Table.
	 * @param newproject
	 */
	public void updateProject(Project newProject) {
		List<Team> teams = getAllTeams();
		for (Team team : teams) {
			if (team.getProject()!= null && team.getProject().getId() == newProject.getId()) {
				team.setProject(newProject);
				EmployeeDAOImpl.getInstance().updateTeamAndProject(team);
			}
		}
	}
	
	/**
	 * Update team leader in team.<br>
	 * Used from Employee Table.
	 * @param newEmployee
	 */
	public void updateTeamLeader(Employee newEmployee) {
		List<Team> teams = getAllTeams();
		for (Team team : teams) {
			if (team.getTeamLeader() != null && team.getTeamLeader().getId() == newEmployee.getId()) {
				//check teams between employee and team where he was - if is different (team leader was moved) set null
				if (team.getId() == newEmployee.getTeam().getId()) {
					team.setTeamLeader(newEmployee);
				} else {
					team.setTeamLeader(null);
				}
			}
		}
	}
	
	/**
	 * Remove team
	 * @param team
	 */
	public void removeTeam(Team team) {
		teams.remove(team);		
		
		//Remove team from project
		ProjectDAOImpl.getInstance().removeTeam(team);
		EmployeeDAOImpl.getInstance().removeTeam(team);
	}

	/**
	 * Remove employee and check if there was team leader in team.<br>
	 * Used from Employee Table.
	 * @param employee
	 */
	public void removeEmployee(Employee employee) {
		if (employee.getTeam() == null) {
			return;
		}
		
		int teamID = employee.getTeam().getId();
		Team team = findById(teamID);
		if (team.getTeamLeader() != null && team.getTeamLeader().getId() == employee.getId()) {
			team.setTeamLeader(null); //Remove if there was team leader
		}
		
		for (int i = 0; i < team.getEmployees().size(); i++) {
			if (employee.getId() == team.getEmployees().get(i).getId()) {
				team.getEmployees().remove(i); //Remove old employee	
				break;
			}
		}
	}
	
	/**
	 * Delete all teams
	 */
	public void removeTeams() {
		teams = new ArrayList<>();
	}
	
	/**
	 * Remove project from team.<br>
	 * Used from Project Table.
	 * @param project
	 */
	public void removeProject(Project project) {
		List<Team> teams = getAllTeams();
		for (int i = 0; i < teams.size(); i++) {
			if (teams.get(i).getProject() != null && teams.get(i).getProject().getId() == project.getId()) {
				Team newTeam = teams.get(i);
				newTeam.setProject(null);
				EmployeeDAOImpl.getInstance().updateTeamAndProject(newTeam);
			}
		}	
	}
	
	/**
	 * Return all teams
	 * @return 
	 */
	public List<Team> getAllTeams() {
		return teams;
	}
	
	/**
	 * Return one of team
	 * @param index
	 * @return team
	 */
	public Team getTeam(int index) {
		return teams.get(index);
	}

	/**
	 * Get team from teams list by ID
	 * @param id
	 * @return team
	 */
	public Team findById(int id) {
		for (Team team : teams) {
			if (team.getId() == id) {
				return team;
			}
		}
		return null;
	}
	
	/** 
	 * Get next ID ready for set
	 * @return ID
	 */
	public int getNextID() {
		int id = 0;
		for (Team team : teams) {
			id = id < team.getId() ? team.getId() : id;
		}
		return id+1;
	}
	
}
