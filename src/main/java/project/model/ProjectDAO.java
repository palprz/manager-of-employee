package main.java.project.model;

import java.util.List;

interface ProjectDAO {
	/** Add new project*/
	public void addProject(Project project);
	/** Update project */
	public void updateProject(Project oldProject, Project newProject);
	/** Remove project */
	public void removeProject(Project project);
	/** Return list of all projects */
	public List<Project> getAllProjects();
	/** Return one project from all projects */
	public Project getProject(int index);
	/** Return one project from all projects by ID */
	public Project findById(int id);
	/** Return next ID to set (max of all projects +1) */
	public int getNextID();
}
