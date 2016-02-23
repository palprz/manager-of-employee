package main.java.employee.table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import main.java.employee.model.Employee;

@SuppressWarnings("serial")
public class EmployeeTableModel extends AbstractTableModel {
	
	 private String[] columnNames = {"Name", "Surname", "Salary", "Team", "Project"};
	 private ArrayList<Employee> data = new ArrayList<>();
	 
	 public int getColumnCount() {
	 	return columnNames.length;
	 }
	 
	public int getRowCount() {
	 	return data.size();
	 }
	
	public Employee getRow(int index) {
		Employee employee = data.get(index);
		return employee;
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
				 value = data.get(row).getSurname();
				 break;
			 case 2:
				 value = data.get(row).getSalary();
				 break;
			 case 3:
				 if (data.get(row).getTeam() != null) {
					 value = data.get(row).getTeam().getName();				 
				 } else {
					 value = "< No set >";
				 }
				 break;
			 case 4:
				 if (data.get(row).getProject() != null) {
					 value = data.get(row).getProject().getName();					 
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
		 return false;
	 }
	 
	 public void setRow(Employee employee){
		 data.add(employee);
		 fireTableDataChanged();
	 }
	 
	 void updateRow(Employee oldEmployee, Employee newEmployee){
		 data.remove(oldEmployee);
		 data.add(newEmployee);
		 fireTableDataChanged();
	 }

	 void removeRow(Employee employee){
		 data.remove(employee);
		 fireTableDataChanged();
	 }
	 
}
