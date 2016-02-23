package main.java.project.hibernate;

import java.util.List;

import main.java.employee.hibernate.HibernateEmployee;
import main.java.employee.model.Employee;
import main.java.employee.model.EmployeeDAOImpl;
import main.java.project.model.Project;
import main.java.team.model.Team;

public class PrepareSynchEmployee implements IPrepareSynch{

	/* (non-Javadoc)
	 * @see main.java.project.hibernate.IPrepareSynch#prepareSynch()
	 */
	@Override
	public void prepareSynch() {
		HibernateEmployee hibEmp = new HibernateEmployee();
		List<Employee> oldEmps = hibEmp.readAll();
		List<Employee> newEmps = EmployeeDAOImpl.getInstance().getAllEmployees();
		
		for (Employee newEmp : newEmps) {
			Employee oldEmp = getDuplicateID(newEmp.getId(), oldEmps);
			if (oldEmp != null) {
				if (isChanged(newEmp, oldEmp)) {
					hibEmp.update(newEmp);
				}
				//Remove employee which is in newEmps
				//after saving and updating we need to remove all employees from this list
				oldEmps.remove(oldEmp);
			} else {
				hibEmp.save(newEmp);
			}
		}
		
		for (Employee oldEmp : oldEmps) {
			hibEmp.delete(oldEmp);
		}	
	}
	
	/**
	 * Search employees from database for get employee with the same ID like the new one employee.
	 * @param id
	 * @param oldEmps
	 * @return Employee
	 */
	private Employee getDuplicateID(int id, List<Employee> oldEmps) {
		for (Employee oldEmp : oldEmps) {
			if (oldEmp.getId() == id) {
				return oldEmp;
			}
		}
		
		return null;
	}
	
	/**
	 * Check if there is any changes between two employees.
	 * @param newEmp
	 * @param oldEmp
	 * @return boolean
	 */
	private boolean isChanged(Employee newEmp, Employee oldEmp) {
		if (!newEmp.getName().equals(oldEmp.getName())) {
			return true;
		} 
		
		if (!newEmp.getSurname().equals(oldEmp.getSurname())) {
			return true;
		}
		
		if (!(newEmp.getSalary() == oldEmp.getSalary())) {
			return true;
		}

		Project oldProject = oldEmp.getProject();
		Project newProject = newEmp.getProject();
		boolean isBothNull = oldProject == null && newProject == null;
		boolean isOneNull = oldProject == null || newProject == null;
		if (!isBothNull && (isOneNull || oldProject.getId() != newProject.getId())) {
			return true;
		}

		Team oldTeam = oldEmp.getTeam();
		Team newTeam = newEmp.getTeam();
		isBothNull = oldTeam == null && newTeam == null;
		isOneNull = oldTeam == null || newTeam == null;
		if (!isBothNull && (isOneNull || oldTeam.getId() != newTeam.getId())) {
			return true;
		}
		
		return false;
	}

}
