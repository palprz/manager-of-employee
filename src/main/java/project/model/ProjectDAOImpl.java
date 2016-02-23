package main.java.project.model;

import java.util.ArrayList;
import java.util.List;

import main.java.project.hibernate.HibernateProject;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class ProjectDAOImpl implements ProjectDAO {
	
	/** Singleton - instance of ProjectDAOImpl */
	private static final ProjectDAOImpl INSTANCE = new ProjectDAOImpl();
	private List<Project> projects;

	/**
	 * Get instance of class
	 * @return INSTANCE
	 */
    public static ProjectDAOImpl getInstance() {
        return INSTANCE;
    }

	/**
	 * Singleton constructor
	 */
	private ProjectDAOImpl() {
		projects = new ArrayList<>();
		HibernateProject hibPro = new HibernateProject();
		List<Project> projects = hibPro.readAll();
		addProjects(projects);
	}
	
	/**
	 * Add project into list of projects
	 * @param project
	 */
	public void addProject(Project project) {
		projects.add(project);
	}
	
	/**
	 * Add team into list of teams of projects
	 * @param team
	 */
	public void addTeam(Team team) {
		int projectID = team.getProject().getId();
		Project project = findById(projectID);
		project.getTeams().add(team);
	}

	/**
	 * Add projects into list of projects
	 * @param projects
	 */
	public void addProjects(List<Project> projects) {
		for(Project project : projects) {
			this.projects.add(project);
		}
	}
	
	/**
	 * Update project from list of projects
	 * @param oldProject
	 * @param newProject
	 */
	public void updateProject(Project oldProject, Project newProject) {
		projects.remove(oldProject);
		projects.add(newProject);

		//Change in team table
		TeamDAOImpl.getInstance().updateProject(newProject);
	}
	
	/**
	 * Update team in project.<br>
	 * Used from Team Table.
	 * @param oldTeam
	 * @param newTeam
	 */
	public void updateTeam(Team oldTeam, Team newTeam) {
		List<Project> projects = getAllProjects();
		for (Project project : projects) {
			if (project.getId() == oldTeam.getProject().getId()) {
				//Problem with references
				for (int i = 0; i < project.getTeams().size(); i++) {
					if (project.getTeams().get(i).getId() == oldTeam.getId()) {
						project.getTeams().remove(i);							
					}				
				}
			}
			
			if (project.getId() == newTeam.getProject().getId()) {
				project.getTeams().add(newTeam);
			}
		}
	}
	
	/**
	 * Delete project from list of projects
	 * @param project
	 */
	public void removeProject(Project project) {
		projects.remove(project);
		
		//Delete from other model table
		TeamDAOImpl.getInstance().removeProject(project);
	}

	/**
	 * Remove team from project.<br>
	 * Used from Team Table.
	 * @param removedTeam
	 */
	public void removeTeam(Team removedTeam) {
		List<Project> projects = getAllProjects();
		for (Project project : projects) {
			for (int i = 0; i < project.getTeams().size(); i++) {
				if (project.getTeams().get(i).getId() == removedTeam.getId()) {
					project.getTeams().remove(i);
				}
			}
		}
	}
	
	/**
	 * Delete all projects
	 */
	public void removeProjects() {
		projects = new ArrayList<Project>();
	}
	
	/**
	 * Get all projects from list
	 * @return projects
	 */
	public List<Project> getAllProjects() {
		return projects;
	}
	
	/**
	 * Get project from projects list
	 * @param index
	 * @return project
	 */
	public Project getProject(int index) {
		return projects.get(index); 
	}

	/**
	 * Get project from projects list by ID
	 * @param id
	 * @return project
	 */
	public Project findById(int id) {
		for (Project project : projects) {
			if (project.getId() == id) {
				return project;
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
		for (Project project : projects) {
			id = id < project.getId() ? project.getId() : id;
		}
		return id+1;
	}
	
}
