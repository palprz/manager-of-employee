package main.java.employee.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.employee.model.Employee;
import main.java.employee.model.EmployeeDAOImpl;
import main.java.employee.table.EmployeeTable;
import main.java.employee.window.EmployeeWindow;
import main.java.launch.ValidateEmployeeUtil;
import main.java.project.model.Project;
import main.java.project.table.ProjectTable;
import main.java.team.model.Team;
import main.java.team.table.TeamTable;
import main.java.team.table.TeamTableModel;

public class EmployeeListener extends ValidateEmployeeUtil implements ListSelectionListener, ActionListener{

	private static Logger log = Logger.getLogger(EmployeeListener.class);
	private EmployeeWindow employeeWindow;

	/**
	 * Constructor
	 */
	public EmployeeListener() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);		
	}
	
	/**
	 * Set data from window
	 * @param inputData
	 */
	public EmployeeListener(EmployeeWindow inputData) {
		this.employeeWindow = inputData;
	}
	
	/**
	 * Handle event of change
	 */
	public void valueChanged(ListSelectionEvent e) {
		EmployeeTable employeeTable = EmployeeTable.getInstance();
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		if (lsm.isSelectionEmpty()) {
			setContextMenuItemsEnable(employeeTable, false);
			Employee selectRow = null;
			employeeTable.setSelectRow(selectRow);
		} else {
			//Finding the right selected row in all indexes
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
			for (int i = minIndex; i <= maxIndex; i++) {
			    if (lsm.isSelectedIndex(i)) {
					log.debug("Select row: " + i);
			    	setContextMenuItemsEnable(employeeTable, true);
					Employee selectRow = employeeTable.getTableModel().getRow(i);
					employeeTable.setSelectRow(selectRow);
			    }
			}			
		}
	}

	/**
	 * Enable/disable buttons
	 * @param employeeTable
	 * @param isSelectedRow
	 */
    private void setContextMenuItemsEnable(EmployeeTable employeeTable, boolean isSelectedRow) {
		employeeTable.getContextMenuAdd().setEnabled(true);
		employeeTable.getContextMenuUpdate().setEnabled(isSelectedRow);
		employeeTable.getContextMenuRemove().setEnabled(isSelectedRow);
	}
    
    /**
     * Handle event of action
     */
	public void actionPerformed(ActionEvent e) {
		log.debug("Action event: " + e.getActionCommand());
		switch (e.getActionCommand()) {
			case "Cancel":
	    		EmployeeWindow.frame.dispose();
				break;
			case "Adding":
				add();
				break;
			case "Updating":
				update();
				break;
			case "comboBoxChanged":
				comboBoxChanged();
				break;
		}
    	return;
    }

	private void add() {
		if (!checkSalaryToInt(employeeWindow.getSalaryField().getText())) {
			JOptionPane.showMessageDialog(null, "Validation failed: Incorrect value in salary field", "Warning", 0);
			log.debug("Incorrect value in salary field.");
			return;
		}
		
		Team team = (Team) employeeWindow.getTeamField().getSelectedItem();
		Employee employee = new Employee(employeeWindow.getNameField().getText(), 
				employeeWindow.getSurnameField().getText(), 
				Integer.parseInt(employeeWindow.getSalaryField().getText()),
				team,
				team.getProject()
				);

		if (!validate(employee)) {
			JOptionPane.showMessageDialog(null, "Validation failed: Please set name and surname", "Warning", 0);
			log.debug("Validation failed");	
			return;
		}
		
		EmployeeDAOImpl.getInstance().addEmployee(employee);
		EmployeeTable.getInstance().addRow(employee);
		refreshTables();
		
    	EmployeeWindow.frame.dispose();
		log.debug("Employee to add: " + employee.toString());	
	}
	
	private void update() {
		if (!checkSalaryToInt(employeeWindow.getSalaryField().getText())) {
			JOptionPane.showMessageDialog(null, "Validation failed: Incorrect value in salary field", "Warning", 0);
			log.debug("Incorrect value in salary field.");
			return;
		}
		
		Employee oldEmployee = EmployeeTable.getInstance().getSelectRow();
		Team team = (Team) employeeWindow.getTeamField().getSelectedItem();
		Employee newEmployee = new Employee(employeeWindow.getNameField().getText(), 
				employeeWindow.getSurnameField().getText(), 
				Integer.parseInt(employeeWindow.getSalaryField().getText()),
				team,
				team.getProject()
				);
		newEmployee.setId(oldEmployee.getId());

		if (!validate(newEmployee)) {
			JOptionPane.showMessageDialog(null, "Validation failed: Please set name and surname", "Warning", 0);
			log.debug("Validation failed");	
			return;
		}
		
		EmployeeDAOImpl.getInstance().updateEmployee(oldEmployee, newEmployee);
		EmployeeTable.getInstance().updateRow(oldEmployee, newEmployee);
		refreshTables();
		
    	EmployeeWindow.frame.dispose();
		log.debug("Employee to update: " + oldEmployee.toString());
	}

	/**
	 * Change combobox with project when was changed team combobox.
	 */
	private void comboBoxChanged() {
		Team selectedTeam = (Team) employeeWindow.getTeamField().getSelectedItem();
		Project projectToSet = selectedTeam.getProject();
		employeeWindow.getProjectField().setText(projectToSet != null ? projectToSet.getName() : "");
	}

	private void refreshTables() {
		EmployeeTable.getInstance().getTableModel().fireTableDataChanged();
		TeamTable.getInstance().getTableModel().fireTableDataChanged();
		ProjectTable.getInstance().getTableModel().fireTableDataChanged();
	}
	
}
