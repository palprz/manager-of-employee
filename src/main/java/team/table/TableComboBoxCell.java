package main.java.team.table;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.project.table.ProjectTableModel;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

@SuppressWarnings("serial")
public class TableComboBoxCell extends DefaultCellEditor{

	private DefaultComboBoxModel<String> model;
	
	@SuppressWarnings("unchecked")
	public TableComboBoxCell() {
		super(new JComboBox<String>());
		this.model = (DefaultComboBoxModel<String>)((JComboBox<String>)getComponent()).getModel();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		TableModel tableModel = table.getModel();
		if (tableModel instanceof TeamTableModel && column == 3) {
			addElementsIntoTeam(table, value, isSelected, row, column);
		} else if (tableModel instanceof ProjectTableModel && column == 1) {
			addElementsIntoProject(table, value, isSelected, row, column);			
		}
		
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	/**
	 * Add all elements into column with members of team.
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param row
	 * @param column
	 */
	private void addElementsIntoTeam(JTable table, Object value, boolean isSelected, int row, int column) {
		model.removeAllElements();
		String nameOfTeam = (String) table.getValueAt(row, 0);
		String nameOfTeamLeader = (String) table.getValueAt(row, 4);
		List<Team> teams = TeamDAOImpl.getInstance().getAllTeams();
		for (Team team : teams) {
			if (compareTeam(team, nameOfTeam, nameOfTeamLeader)) {
				setElementsTeam(team);
			}
		}	
	}

	/**
	 * Compare strings from checking Team by name of team and name of team leader
	 * @param team
	 * @param nameOfTeam
	 * @param nameOfTeamLeader
	 * @return boolean
	 */
	private boolean compareTeam(Team team, String nameOfTeam, String nameOfTeamLeader) {
		boolean isSameName = team.getName().equals(nameOfTeam);
		boolean isSameTeamLeader = false;
		if (team.getTeamLeader() != null) { //for the case when team leader == null
			String checkingTeamLeader = team.getTeamLeader().getName() + " " + team.getTeamLeader().getSurname();
			isSameTeamLeader = checkingTeamLeader.equals(nameOfTeamLeader);
		} else {
			isSameTeamLeader = true;
		}
		return isSameName && isSameTeamLeader;
	}
	
	/**
	 * Add elements into model
	 * @param team
	 */
	private void setElementsTeam(Team team) {
		for (int i = 0; i < team.getEmployees().size(); i++) {
			String nameOfEmployee = team.getEmployees().get(i).getName() + " " + team.getEmployees().get(i).getSurname();
			model.addElement(nameOfEmployee);
		}		
	}

	/**
	 * Add all elements into column with teams of project
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param row
	 * @param column
	 */
	private void addElementsIntoProject(JTable table, Object value, boolean isSelected, int row, int column) {
		model.removeAllElements();
		String nameOfProject = (String) table.getValueAt(row, 0);
		List<Project> projects = ProjectDAOImpl.getInstance().getAllProjects();
		for (Project project : projects) {
			if (compareProject(project, nameOfProject)) {
				setElementsProject(project);
			}
		}		
	}

	/**
	 * Compare strings from checking Project by name of project
	 * @param project
	 * @param nameOfProject
	 * @return
	 */
	private boolean compareProject(Project project, String nameOfProject) {
		return project.getName().equals(nameOfProject);
	}

	/**
	 * Add elements into model
	 * @param project
	 */
	private void setElementsProject(Project project) {
		for (int i = 0; i < project.getTeams().size(); i++) {
			String nameOfTeam = project.getTeams().get(i).getName();
			model.addElement(nameOfTeam);
		}
	}

}