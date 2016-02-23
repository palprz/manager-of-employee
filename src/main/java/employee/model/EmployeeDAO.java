package main.java.employee.model;

import java.util.List;

interface EmployeeDAO {
	/** Add new employee*/
	public void addEmployee(Employee employee);
	/** Update employee */
	public void updateEmployee(Employee oldEmployee, Employee newEmployee);
	/** Remove employee */
	public void removeEmployee(Employee employee);
	/** Return list of all employees */
	public List<Employee> getAllEmployees();
	/** Return one employee from all employees */
	public Employee getEmployee(int index);
	/** Return one employee from all employees by ID */
	public Employee findById(int id);
	/** Return next ID to set (max of all employees +1) */
	public int getNextID();
}
