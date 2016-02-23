package main.java.project.table;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import main.java.project.listener.ProjectContextMenuListener;
import main.java.project.listener.ProjectListener;
import main.java.project.model.Project;
import main.java.team.table.TableComboBoxCell;

public class ProjectTable {
	
	/** Singleton - instance of ProjectTable */
	private static final ProjectTable INSTANCE = new ProjectTable();
	private JTable table;
	private ProjectTableModel tableModel;
	private Project selectRow;
	private JMenuItem contextMenuAdd;
	private JMenuItem contextMenuUpdate;
	private JMenuItem contextMenuRemove;

    public static ProjectTable getInstance() {
        return INSTANCE;
    }
    
	/**
	 * Empty singleton constructor for init table for Project type objects
	 */
	private ProjectTable() {
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
		tableModel = (ProjectTableModel) table.getModel();
	}
	
	private void addListener() {
		ListSelectionModel  rowSelectionModel = table.getSelectionModel();
		ListSelectionListener listSelectionListener = new ProjectListener();
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
	    ActionListener actionListener = new ProjectContextMenuListener(this);
	    contextMenuAdd.addActionListener(actionListener);
	    contextMenuUpdate.addActionListener(actionListener);
	    contextMenuRemove.addActionListener(actionListener);
		popupMenu.add(contextMenuAdd);
		popupMenu.add(contextMenuUpdate);
		popupMenu.add(contextMenuRemove);
		table.setComponentPopupMenu(popupMenu);
	}

	/**
	 * Add list of projects to table
	 * @param projects
	 */
	public void addRow(List<Project> projects) {
		for (Project project : projects) {
			TableColumn teamColumn = table.getColumnModel().getColumn(1); //column with teams
			teamColumn.setCellEditor(new TableComboBoxCell());
			tableModel.setRow(project);			
		}
	}

	/**
	 * Add project to table
	 * @param project
	 */
	public void addRow(Project project) {
		tableModel.setRow(project);
	}

	/**
	 * Update row with project from table
	 * @param oldProject
	 * @param newProject
	 */
	public void updateRow(Project oldProject, Project newProject) {
		tableModel.updateRow(oldProject, newProject);
	}
	
	/**
	 * Remove row with project from table
	 * @param project
	 */
	public void removeRow(Project project) {
		tableModel.removeRow(project);
	}

	/**
	 * Get selected row in project table
	 * @return selectRow
	 */
	public Project getSelectRow() {
		return selectRow;
	}
	
	/**
	 * Set selected row in project table
	 * @param newValue
	 */
	public void setSelectRow(Project newValue) {
		this.selectRow = newValue;
	}

	/**
	 * Get table model
	 * @return tableModel
	 */
	public ProjectTableModel getTableModel() {
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