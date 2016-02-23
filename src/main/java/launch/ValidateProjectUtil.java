package main.java.launch;

import java.util.List;

import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;

/**
 * Class open for new conditions
 */
public class ValidateProjectUtil {

	/**
	 * @param oldProject if we add, set <code>null</code>
	 * @param newProject
	 * @return <code>true</code> if there aren't duplicates name of project and name is set.
	 */
	public boolean validate(Project oldProject, Project newProject) {
		List<Project> projects = ProjectDAOImpl.getInstance().getAllProjects();
		removeProjectFromList(oldProject, projects);
		
		if (checkName(projects, newProject)) {
			return false;
		}
		
		return true;
	}

	/**
	 * If there is old project just remove from list to not checking the same data for
	 * false duplicates
	 * @param oldProject
	 * @param projects
	 */
	private void removeProjectFromList(Project oldProject, List<Project> projects) {
		for (int i = 0; i < projects.size(); i++) {
			if (oldProject == null) {
				break;
			}
			
			if (projects.get(i).getId() == oldProject.getId()) {
				projects.remove(i);
			}
		}
	}
	
	private boolean checkName(List<Project> projects, Project newProject) {
		if (newProject.getName().length() <= 0) {
			return true;
		}
		
		for (Project project : projects) {
			if (project.getName().equals(newProject.getName())) {
				return true;
			}
		}
		return false;
	}
	
}
