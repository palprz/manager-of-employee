package main.java.employee.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.employee.model.EmployeeDAOImpl;
import main.java.employee.table.EmployeeTable;
import main.java.employee.window.EmployeeWindow;

public class EmployeeContextMenuListener implements ActionListener{

	  private static Logger log = Logger.getLogger(EmployeeContextMenuListener.class);
	  private EmployeeTable employeeTable;
	  
	  /**
	   * Constructor - Set model of table 
	   * @param inputData
	   */
	  public EmployeeContextMenuListener(EmployeeTable inputData) {
		  PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);	
		  this.employeeTable = inputData;
	  }
	  
	  /**
	   * Handle event of action
	   */
	  public void actionPerformed(ActionEvent e) {
		  log.debug("Action event: " + e.getActionCommand());
		  switch (e.getActionCommand()) {
			  case "Add...":
				  new EmployeeWindow("Adding", null);	
				  break;
			  case "Update...":
				  new EmployeeWindow("Updating", employeeTable.getSelectRow());	
				  break;
			  case "Remove...":
				  log.debug("Employee to remove: " + employeeTable.getSelectRow().toString());
				  EmployeeDAOImpl.getInstance().removeEmployee(employeeTable.getSelectRow());
				
				  EmployeeTable employeeTable = EmployeeTable.getInstance();
				  employeeTable.removeRow(employeeTable.getSelectRow());
				  employeeTable.getTableModel().fireTableDataChanged();
				  break;
		  }
	  }
	  
}
