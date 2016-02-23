package main.java.project.table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import main.java.project.model.Project;

@SuppressWarnings("serial")
public class ProjectTableModel extends AbstractTableModel {
	
	 private String[] columnNames = {"Name", "Teams"};
	 private ArrayList<Project> data = new ArrayList<>();
	 
	 public int getColumnCount() {
	 	return columnNames.length;
	 }
	 
	public int getRowCount() {
	 	return data.size();
	 }
	
	public Project getRow(int index) {
		Project project = data.get(index);
		return project;
	}
	 
	 public String getColumnName(int col){
	 	return columnNames[col];
	 }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public Class getColumnClass(int c){
	 	return getValueAt(0,c).getClass();
	 }

	 public Object getValueAt(int row, int col) {
		Object value = null;
		switch (col) {
			 case 0:
				 value = data.get(row).getName();
				 break;
			 case 1:
				 value = "Click to show combobox"; //ComboBox add in another class
				 break;
		 }
		
		return value;
	 }
	 
	 public void setValueAt(Object value, int row, int col){
		 //do nothing
	 }
	 
	 public boolean isCellEditable(int row, int col){
		 if (col == 1) {
			 return true;
		 } else {
			 return false;
		 }
	 }
	 
	 public void setRow(Project project){
		 data.add(project);
		 fireTableDataChanged();
	 }
	 
	 void updateRow(Project oldProject, Project newProject){
		 data.remove(oldProject);
		 data.add(newProject);
		 fireTableDataChanged();
	 }

	 void removeRow(Project project){
		 data.remove(project);
		 fireTableDataChanged();
	 }
	 
}
