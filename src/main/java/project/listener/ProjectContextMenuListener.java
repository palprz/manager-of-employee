package main.java.project.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.project.model.ProjectDAOImpl;
import main.java.project.table.ProjectTable;
import main.java.project.window.ProjectWindow;

public class ProjectContextMenuListener implements ActionListener{

	  private static Logger log = Logger.getLogger(ProjectContextMenuListener.class);
	  private ProjectTable projectTable;
	  
	  /**
	   * Constructor - Set model of table 
	   * @param inputData
	   */
	  public ProjectContextMenuListener(ProjectTable inputData) {
		  PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);	
		  this.projectTable = inputData;
	  }
	  
	  /**
	   * Handle event of action
	   */
	  public void actionPerformed(ActionEvent e) {
		  log.debug("Action event: " + e.getActionCommand());
		  switch (e.getActionCommand()) {
			  case "Add...":
				  new ProjectWindow("Adding", null);	
				  break;
			  case "Update...":
				  new ProjectWindow("Updating", projectTable.getSelectRow());
				  break;
			  case "Remove...":
				  log.debug("Project to remove: " + projectTable.getSelectRow().toString());
				  ProjectDAOImpl.getInstance().removeProject(projectTable.getSelectRow());
				
				  ProjectTable projectTable = ProjectTable.getInstance();
				  projectTable.removeRow(projectTable.getSelectRow());
				  projectTable.getTableModel().fireTableDataChanged();	
				  break;
		  }
	  }
	  
}
