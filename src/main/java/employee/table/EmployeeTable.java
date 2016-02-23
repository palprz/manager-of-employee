package main.java.employee.table;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import main.java.employee.listener.EmployeeContextMenuListener;
import main.java.employee.listener.EmployeeListener;
import main.java.employee.model.Employee;

public class EmployeeTable {
	
	/** Singleton - instance of EmployeeTable */
	private static final EmployeeTable INSTANCE = new EmployeeTable();
	private JTable table;
	private EmployeeTableModel tableModel;
	private Employee selectRow;
	private JMenuItem contextMenuAdd;
	private JMenuItem contextMenuUpdate;
	private JMenuItem contextMenuRemove;

    public static EmployeeTable getInstance() {
        return INSTANCE;
    }
    
	/**
	 * Empty singleton constructor for init table for Employee type objects
	 */
	private EmployeeTable() {
	}
	
	/**
	 * Get JTable 
	 * @return table
	 */
	public JTable getJTable() {
		return table;
	}
	
	/**
	 * Set JTable and add listener, popup menu, set table model
	 * @param table
	 */
	public void setJTable(JTable table) {
		this.table = table;
		
		//when we set new JTable object (during changing view of objects in table) we need to add all this things bellow
		addListener();
		addPopupMenu();
		
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = (EmployeeTableModel) table.getModel();
	}
	
	private void addListener() {
		ListSelectionModel  rowSelectionModel = table.getSelectionModel();
		ListSelectionListener listSelectionListener = new EmployeeListener();
		rowSelectionModel.addListSelectionListener(listSelectionListener);
	}

	private void addPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		contextMenuAdd = new JMenuItem("Add...");
		contextMenuAdd.setEnabled(true);
		contextMenuUpdate = new JMenuItem("Update...");
		contextMenuUpdate.setEnabled(false);
		contextMenuRemove = new JMenuItem("Remove...");
		contextMenuRemove.setEnabled(false);
	    ActionListener actionListener = new EmployeeContextMenuListener(this);
	    contextMenuAdd.addActionListener(actionListener);
	    contextMenuUpdate.addActionListener(actionListener);
	    contextMenuRemove.addActionListener(actionListener);
		popupMenu.add(contextMenuAdd);
		popupMenu.add(contextMenuUpdate);
		popupMenu.add(contextMenuRemove);
		table.setComponentPopupMenu(popupMenu);
	}

	/**
	 * Add list of employees to table
	 * @param employees
	 */
	public void addRow(List<Employee> employees) {
		for (Employee employee : employees) {
			tableModel.setRow(employee);			
		}
	}

	/**
	 * Add employee to table
	 * @param employee
	 */
	public void addRow(Employee employee) {
		tableModel.setRow(employee);
	}

	/**
	 * Update row with employee from table
	 * @param oldEmployee
	 * @param newEmployee
	 */
	public void updateRow(Employee oldEmployee, Employee newEmployee) {
		tableModel.updateRow(oldEmployee, newEmployee);
	}
	
	/**
	 * Remove row with employee from table
	 * @param employee
	 */
	public void removeRow(Employee employee) {
		tableModel.removeRow(employee);
	}

	/**
	 * Get selected row in employee table
	 * @return selectRow
	 */
	public Employee getSelectRow() {
		return selectRow;
	}
	
	/**
	 * Set selected row in employee table
	 * @param newValue
	 */
	public void setSelectRow(Employee newValue) {
		this.selectRow = newValue;
	}

	/**
	 * Get table model
	 * @return tableModel
	 */
	public EmployeeTableModel getTableModel() {
		return tableModel;
	}
	
	/**
	 * Get context menu with add
	 * @return contextMenuAdd
	 */
	public JMenuItem getContextMenuAdd() {
		return contextMenuAdd;
	}

	/**
	 * Get context menu with update
	 * @return contextMenuUpdate
	 */
	public JMenuItem getContextMenuUpdate() {
		return contextMenuUpdate;
	}

	/**
	 * Get context menu with remove
	 * @return contextMenuRemove
	 */
	public JMenuItem getContextMenuRemove() {
		return contextMenuRemove;
	}
	
}