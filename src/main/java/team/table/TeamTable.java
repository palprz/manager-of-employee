package main.java.team.table;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import main.java.team.listener.TeamContextMenuListener;
import main.java.team.listener.TeamListener;
import main.java.team.model.Team;


public class TeamTable {

	/** Singleton - instance of TeamTable */
	private static final TeamTable INSTANCE = new TeamTable();
	private JTable table;
	private TeamTableModel tableModel;
	private Team selectRow;
	private JMenuItem contextMenuAdd;
	private JMenuItem contextMenuUpdate;
	private JMenuItem contextMenuRemove;
	
    public static TeamTable getInstance() {
        return INSTANCE;
    }
    
	/**
	 * Empty singleton constructor for init table for Team type objects
	 */
	private TeamTable() {
	}
	
	/**
	 * Get JTable
	 * @return table
	 */
	public JTable getJTable() {
		return table;
	}
	
	/**
	 * Set JTable, listener and popup menu
	 */
	public void setJTable(JTable table) {
		this.table = table;
		
		//when we set new JTable object (during changing view of objects in table) we need to add all this things bellow
		addListener();
		addPopupMenu();
		
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = (TeamTableModel) table.getModel();
	}
	
	private void addListener() {
		ListSelectionModel  rowSelectionModel = table.getSelectionModel();
		ListSelectionListener listSelectionListener = new TeamListener();
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
	    ActionListener actionListener = new TeamContextMenuListener(this);
	    contextMenuAdd.addActionListener(actionListener);
	    contextMenuUpdate.addActionListener(actionListener);
	    contextMenuRemove.addActionListener(actionListener);
		popupMenu.add(contextMenuAdd);
		popupMenu.add(contextMenuUpdate);
		popupMenu.add(contextMenuRemove);
		table.setComponentPopupMenu(popupMenu);
	}

	/**
	 * Add list of teams to table
	 * @param teams
	 */
	public void addRow(List<Team> teams) {
		for (Team team : teams) {
			TableColumn empColumn = table.getColumnModel().getColumn(3); //column with members of team
			empColumn.setCellEditor(new TableComboBoxCell());
			tableModel.setRow(team);
		}
	}

	/**
	 * Add new team to table
	 * @param team
	 */
	public void addRow(Team team) {
		tableModel.setRow(team);
	}

	/**
	 * Update row with team into table
	 * @param oldTeam
	 * @param newTeam
	 */
	public void updateRow(Team oldTeam, Team newTeam) {
		tableModel.updateRow(oldTeam, newTeam);
	}
	
	/**
	 * Remove row with team from table
	 * @param team
	 */
	public void removeRow(Team team) {
		tableModel.removeRow(team);
	}

	/**
	 * Get selected row in team table
	 * @return selectRow
	 */
	public Team getSelectRow() {
		return selectRow;
	}
	
	/**
	 * Set selected row in team table
	 * @param newValue
	 */
	public void setSelectRow(Team newValue) {
		this.selectRow = newValue;
	}

	/**
	 * Get table model
	 * @return tableModel
	 */
	public TeamTableModel getTableModel() {
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
