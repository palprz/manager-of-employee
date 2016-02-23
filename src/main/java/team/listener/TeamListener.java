package main.java.team.listener;

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
import main.java.employee.model.Employee;
import main.java.employee.table.EmployeeTable;
import main.java.launch.ValidateTeamUtil;
import main.java.project.model.Project;
import main.java.project.table.ProjectTable;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;
import main.java.team.table.TeamTable;
import main.java.team.window.TeamWindow;

public class TeamListener extends ValidateTeamUtil implements ListSelectionListener, ActionListener{

	private static Logger log = Logger.getLogger(TeamListener.class);
	private TeamWindow teamWindow;

	/**
	 * Constructor
	 */
	public TeamListener() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);
	}
	/**
	 * Set data from window
	 * @param inputData
	 */
	public TeamListener(TeamWindow inputData) {
		this.teamWindow = inputData;
	}
	
	/**
	 * Handle event from list selection
	 */
	public void valueChanged(ListSelectionEvent e) {
		TeamTable teamTable = TeamTable.getInstance();
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		if (lsm.isSelectionEmpty()) {
			setContextMenuItemsEnable(teamTable, false);
			Team selectRow = null;
			teamTable.setSelectRow(selectRow);
		} else {
			//Finding the right selected row in all indexes
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
			for (int i = minIndex; i <= maxIndex; i++) {
			    if (lsm.isSelectedIndex(i)) {
			    	setContextMenuItemsEnable(teamTable, true);
					log.debug("Select row: " + i);	
					Team selectRow = teamTable.getTableModel().getRow(i);
					teamTable.setSelectRow(selectRow);
			    }
			}			
		}
	}

	/**
	 * Disable/enable buttons
	 * @param teamTable
	 * @param isSelectedRow
	 */
    private void setContextMenuItemsEnable(TeamTable teamTable, boolean isSelectedRow) {
    	teamTable.getContextMenuAdd().setEnabled(true);
		teamTable.getContextMenuUpdate().setEnabled(isSelectedRow);
		teamTable.getContextMenuRemove().setEnabled(isSelectedRow);
	}

    /**
     * Handle event of action
     */
	public void actionPerformed(ActionEvent e) {
		log.debug("Action event: " + e.getActionCommand());
		switch(e.getActionCommand()) {
			case "Cancel":
	    		TeamWindow.frame.dispose();
				break;
			case "Adding":
				add();
				break;
			case "Updating":
				update();
				break;
		}
    	
    	return;
    }
	
	private void add() {
		Team team = new Team(teamWindow.getNameField().getText(),
				(Project) teamWindow.getProjectField().getSelectedItem(),
				(Employee) teamWindow.getTeamLeaderField().getSelectedItem(),
				new ArrayList<Employee>()
				);
    	
		if (!validate(null, team)) {
			JOptionPane.showMessageDialog(null, "Validation failed: Please set name", "Warning", 0);
			log.debug("Validation failed");	
			return;
		}
		
		TeamDAOImpl.getInstance().addTeam(team);
		TeamTable.getInstance().addRow(team);
		refreshTables();
		
		TeamWindow.frame.dispose();
		log.debug("Team to add: " + team.toString());
	}

	private void update() {
		Team oldTeam = TeamTable.getInstance().getSelectRow();
		Team newTeam = new Team(teamWindow.getNameField().getText(),
				(Project) teamWindow.getProjectField().getSelectedItem(),
				(Employee) teamWindow.getTeamLeaderField().getSelectedItem(),
				oldTeam.getEmployees()
				);
		
		newTeam.setId(oldTeam.getId());
		
		if (!validate(oldTeam, newTeam)) {
			JOptionPane.showMessageDialog(null, "Validation failed: Please set name", "Warning", 0);
			log.debug("Validation failed");	
			return;
		}
		
		TeamDAOImpl.getInstance().updateTeam(oldTeam, newTeam);
		TeamTable.getInstance().updateRow(oldTeam, newTeam);
		refreshTables();
		
    	TeamWindow.frame.dispose();
		log.debug("Team to update: " + oldTeam.toString());
	}
	
	private void refreshTables() {
		EmployeeTable.getInstance().getTableModel().fireTableDataChanged();
		TeamTable.getInstance().getTableModel().fireTableDataChanged();
		ProjectTable.getInstance().getTableModel().fireTableDataChanged();
	}
		
}
