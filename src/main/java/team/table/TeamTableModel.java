package main.java.team.table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import main.java.team.model.Team;

@SuppressWarnings("serial")
public class TeamTableModel extends AbstractTableModel {
	
	 private String[] columnNames = {"Name", "Number of members", "Project", "Members", "Team leader"};
	 private ArrayList<Team> data = new ArrayList<>();	 
	 
	 public int getColumnCount() {
	 	return columnNames.length;
	 }
	 
	public int getRowCount() {
	 	return data.size();
	 }
	
	public Team getRow(int index) {
		Team team = data.get(index);
		return team;
	}
	 
	 public String getColumnName(int col){
	 	return columnNames[col];
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public Class getColumnClass(int c){
	 	return getValueAt(0,c).getClass();
	 }

	 @Override
	 public Object getValueAt(int row, int col) {
		Object value = null;
		switch (col) {
			 case 0:
				 value = data.get(row).getName();
				 break;
			 case 1:
				 value = data.get(row).getEmployees().size();
				 break;
			 case 2:
				 if (data.get(row).getProject() != null) {
					 value = data.get(row).getProject().getName();
				 } else {
					 value = "< No set >";
				 }
				 break;
			 case 3:
				 value = "Click to show combobox";  //ComboBox add in another class
				 break;
			 case 4:
				 if (data.get(row).getTeamLeader() != null) {
					value = data.get(row).getTeamLeader().getName() + " " + data.get(row).getTeamLeader().getSurname();
				 } else {
					 value = "< No set >";					 
				 }
				 
				 break;
		 }
		
		return value;
	 }
	 
	 public void setValueAt(Object value, int row, int col){
		 //do nothing
	 }
	 
	 public boolean isCellEditable(int row, int col){
		 if (col == 3) {
			 return true;
		 } else {
			 return false;
		 }
	 }
	 
	 public void setRow(Team team){
		 data.add(team);
		 fireTableDataChanged();
	 }
	 
	 void updateRow(Team oldTeam, Team newTeam){
		 data.remove(oldTeam);
		 data.add(newTeam);
		 fireTableDataChanged();
	 }

	 void removeRow(Team team){
		 data.remove(team);
		 fireTableDataChanged();
	 }
	 
}
