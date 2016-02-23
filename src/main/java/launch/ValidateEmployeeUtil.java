package main.java.launch;

import main.java.employee.model.Employee;

/**
 * Class open for new conditions
 */
public class ValidateEmployeeUtil {

	/**
	 * @param employee
	 * @return <code>true</code> if name and surname.
	 */
	public boolean validate(Employee employee) {
		if (employee.getName().length() <= 0) {
			return false;
		}
		
		if (employee.getSurname().length() <= 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param salary
	 * @return <code>true</code> if we can convert from string to integer
	 */
	public boolean checkSalaryToInt(String salary) {
		return salary.matches("[0-9]+");
	}
}
