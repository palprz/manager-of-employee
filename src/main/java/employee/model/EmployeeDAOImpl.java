package main.java.employee.model;

import java.util.ArrayList;
import java.util.List;

import main.java.employee.hibernate.HibernateEmployee;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	/** Singleton - instance of EmployeeDAOImpl */
	private static final EmployeeDAOImpl INSTANCE = new EmployeeDAOImpl();
	private List<Employee> employees;

	/**
	 * Get instance of class
	 * @return INSTANCE
	 */
    public static EmployeeDAOImpl getInstance() {
        return INSTANCE;
    }

	/**
	 * Singleton constructor
	 */
	private EmployeeDAOImpl() {
		employees = new ArrayList<>();
		HibernateEmployee hibEmp = new HibernateEmployee();
		List<Employee> emp = hibEmp.readAll();
		addEmployees(emp);
	}
	
	/**
	 * Add employee into list of employees
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		employees.add(employee);
		
		//Add into other model table
		TeamDAOImpl.getInstance().addEmployee(employee);
	}

	/**
	 * Add employees into list of employees
	 * @param employees
	 */
	public void addEmployees(List<Employee> employees) {
		for(Employee employee : employees) {
			this.employees.add(employee);
		}
	}
	
	/**
	 * Update employee from list of employees
	 * @param oldEmployee
	 * @param newEmployee
	 */
	public void updateEmployee(Employee oldEmployee, Employee newEmployee) {
		employees.remove(oldEmployee);
		employees.add(newEmployee);
 
		//Set new team leader if it's somewhere
		TeamDAOImpl.getInstance().updateTeamLeader(newEmployee);
		
		Team oldTeam = oldEmployee.getTeam();
		Team newTeam = newEmployee.getTeam();
		boolean isChangedTeam = !(oldTeam == null && newTeam == null) && 
				((oldTeam == null || newTeam == null) ||
				(oldTeam.getId() != newTeam.getId()));
		boolean isChangedName = !oldEmployee.getName().equals(newEmployee.getName());
		boolean isChangedSurname = !oldEmployee.getSurname().equals(newEmployee.getSurname());

		if (isChangedName || isChangedSurname || isChangedTeam) {
			TeamDAOImpl.getInstance().removeEmployee(oldEmployee);
			TeamDAOImpl.getInstance().addEmployee(newEmployee);
		}
	}
	
	/**
	 * Update team and project of team into employee.<br>
	 * Used from Team Table.
	 * @param newTeam 
	 */
	public void updateTeamAndProject(Team newTeam) {
		List<Employee> employees = getAllEmployees();
		for (Employee employee : employees) {
			if (employee.getTeam() != null && employee.getTeam().getId() == newTeam.getId()) {
				employee.setTeam(newTeam);
				employee.setProject(newTeam.getProject());
			}
		}
	}
	
	/**
	 * Delete employee from list of employees
	 * @param employee
	 */
	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		
		//Delete from other model table	
		TeamDAOImpl.getInstance().removeEmployee(employee);
	}

	/**
	 * Remove team from employee.<br>
	 * Used from Team Table.
	 * @param team
	 */
	public void removeTeam(Team team) {
		List<Employee> employees = getAllEmployees();
		for (Employee employee : employees) {
			if (employee.getTeam() != null && employee.getTeam().getId() == team.getId()) {
				employee.setTeam(null);
				employee.setProject(null);
			}
		}
	}
	
	/**
	 * Delete all employees
	 */
	public void removeEmployees() {
		employees = new ArrayList<Employee>();
	}
	
	/**
	 * Get all employees from list
	 * @return employees
	 */
	public List<Employee> getAllEmployees() {
		return employees;
	}
	
	/**
	 * Get employee from employees list
	 * @param index
	 * @return employee
	 */
	public Employee getEmployee(int index) {
		return employees.get(index); 
	}

	/**
	 * Get employee from employees list by ID
	 * @param id
	 * @return emp
	 */
	public Employee findById(int id) {
		for (Employee emp : employees) {
			if (emp.getId() == id) {
				return emp;
			}
		}
		return null;
	}

	/** 
	 * Get next ID ready for set
	 * @return ID
	 */
	public int getNextID() {
		int id = 0;
		for (Employee emp : employees) {
			id = id < emp.getId() ? emp.getId() : id;
		}
		return id+1;
	}

}
