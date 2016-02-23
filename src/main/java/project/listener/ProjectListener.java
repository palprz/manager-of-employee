package main.java.project.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.employee.table.EmployeeTable;
import main.java.launch.ValidateProjectUtil;
import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.project.table.ProjectTable;
import main.java.project.window.ProjectWindow;
import main.java.team.model.Team;
import main.java.team.table.TeamTable;

public class ProjectListener extends ValidateProjectUtil implements ListSelectionListener, ActionListener{

	private static Logger log = Logger.getLogger(ProjectListener.class);
	private ProjectWindow projectWindow;

	/**
	 * Constructor
	 */
	public ProjectListener() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);		
	}
	
	/**
	 * Set data from window
	 * @param inputData
	 */
	public ProjectListener(ProjectWindow inputData) {
		this.projectWindow = inputData;
	}
	
	/**
	 * Handle event of change
	 */
	public void valueChanged(ListSelectionEvent e) {
		ProjectTable projectTable = ProjectTable.getInstance();
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		if (lsm.isSelectionEmpty()) {
			setContextMenuItemsEnable(projectTable, false);
			Project selectRow = null;
			projectTable.setSelectRow(selectRow);
		} else {
			//Finding the right selected row in all indexes
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
			for (int i = minIndex; i <= maxIndex; i++) {
			    if (lsm.isSelectedIndex(i)) {
					log.debug("Select row: " + i);
			    	setContextMenuItemsEnable(projectTable, true);
					Project selectRow = projectTable.getTableModel().getRow(i);
					projectTable.setSelectRow(selectRow);
			    }
			}			
		}
	}

	/**
	 * Enable/disable buttons
	 * @param projectTable
	 * @param isSelectedRow
	 */
    private void setContextMenuItemsEnable(ProjectTable projectTable, boolean isSelectedRow) {
    	projectTable.getContextMenuAdd().setEnabled(true);
		projectTable.getContextMenuUpdate().setEnabled(isSelectedRow);
		projectTable.getContextMenuRemove().setEnabled(isSelectedRow);
	}
    
    /**
     * Handle event of action
     */
	public void actionPerformed(ActionEvent e) {
		log.debug("Action event: " + e.getActionCommand());
		switch (e.getActionCommand()) {
			case "Cancel":
	    		ProjectWindow.frame.dispose();
				break;
			case "Adding":
				add();
				break;
			case "Updating":
				update();
				break;			
		}
    }

	private void add() {
		Project project = new Project(projectWindow.getNameField().getText(),
				new ArrayList<Team>()
				);
    	
		if (!validate(null, project)) {
			JOptionPane.showMessageDialog(null, "Validation failed: Please set name", "Warning", 0);
			log.debug("Validation failed");	
			return;
		}
		
		ProjectDAOImpl.getInstance().addProject(project);
		ProjectTable.getInstance().addRow(project);
		refreshTables();
		
		ProjectWindow.frame.dispose();
		log.debug("Project to add: " + project.toString());
	}

	private void update() {
		Project oldProject = ProjectTable.getInstance().getSelectRow();
		Project newProject = new Project(projectWindow.getNameField().getText(),
				oldProject.getTeams()
				);
		
		newProject.setId(oldProject.getId());

		if (!validate(oldProject, newProject)) {
			JOptionPane.showMessageDialog(null, "Validation failed: Please set name", "Warning", 0);
			log.debug("Validation failed");	
			return;
		}
		
		ProjectDAOImpl.getInstance().updateProject(oldProject, newProject);
		ProjectTable.getInstance().updateRow(oldProject, newProject);
		refreshTables();
		
		ProjectWindow.frame.dispose();
		log.debug("Project to update: " + oldProject.toString());
	}
	
	private void refreshTables() {
		EmployeeTable.getInstance().getTableModel().fireTableDataChanged();
		TeamTable.getInstance().getTableModel().fireTableDataChanged();
		ProjectTable.getInstance().getTableModel().fireTableDataChanged();
	}
	
}
