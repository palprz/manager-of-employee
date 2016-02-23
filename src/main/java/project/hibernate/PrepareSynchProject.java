package main.java.project.hibernate;

import java.util.List;

import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;

public class PrepareSynchProject implements IPrepareSynch {
	
	/* (non-Javadoc)
	 * @see main.java.project.hibernate.IPrepareSynch#prepareSynch()
	 */
	@Override
	public void prepareSynch() {
		HibernateProject hibProject = new HibernateProject();
		List<Project> oldProjects = hibProject.readAll();
		List<Project> newProjects = ProjectDAOImpl.getInstance().getAllProjects();
		
		for (Project newProject : newProjects) {
			Project oldProject = getDuplicateID(newProject.getId(), oldProjects);
			if (oldProject != null) {
				if (isChanged(newProject, oldProject)) {
					hibProject.update(newProject);
				}
				//Remove project which is in newProjects
				//after saving and updating we need to remove all projects from this list
				oldProjects.remove(oldProject);
			} else {
				hibProject.save(newProject);
			}
		}
		
		for (Project oldProject : oldProjects) {
			hibProject.delete(oldProject);
		}	
	}
	
	/**
	 * Search projects from database for get project with the same ID like the new one project.
	 * @param id
	 * @param oldProjects
	 * @return Project
	 */
	private Project getDuplicateID(int id, List<Project> oldProjects) {
		for (Project oldProject : oldProjects) {
			if (oldProject.getId() == id) {
				return oldProject;
			}
		}
		
		return null;
	}
	
	/**
	 * Check if there is any changes between two projects.
	 * @param newProject
	 * @param oldProject
	 * @return boolean
	 */
	private boolean isChanged(Project newProject, Project oldProject) {
		if (!newProject.getName().equals(oldProject.getName())) {
			return true;
		} else {
			return false;
		}
	}

}
