package main.java.employee.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;

import main.java.Paths;
import main.java.employee.model.Employee;
import main.java.utils.HibernateUtils;

public class HibernateEmployee  {
	
	private static Logger log = Logger.getLogger(HibernateEmployee.class);
	
	public HibernateEmployee() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);	
	}
	
	/**
	 * Save employees into database
	 * @param emp
	 */
	public void save(Employee emp) {
		log.debug("Saving data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(emp);
		session.getTransaction().commit();
		session.close();
		log.debug("Save data complete!");
	}	

	/**
	 * Load employees from database
	 * @return employees
	 */
	public List<Employee> readAll() {
		log.debug("Loading data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Employee> employees = session.createQuery("FROM Employee").list();
		session.close();
		log.debug("Loading data complete!");
		return employees;
	}

	/**
	 * Update employee into database
	 * @param newEmp
	 */
	public void update(Employee newEmp) {
		log.debug("Update data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		Employee oldEmp = (Employee) session.load(Employee.class, newEmp.getId());
		
		oldEmp.setName(newEmp.getName());
		oldEmp.setSurname(newEmp.getSurname());
		oldEmp.setSalary(newEmp.getSalary());
		oldEmp.setTeam(newEmp.getTeam());
		oldEmp.setProject(newEmp.getProject());
		
		session.getTransaction().commit();
		session.close();
		log.debug("Update data complete!");
	}
	
	/**
	 * Delete employee from database
	 * @param emp
	 */
	public void delete(Employee emp) {
		log.debug("Delete data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(emp);
		session.getTransaction().commit();
		session.close();
		log.debug("Delete data complete!");
	}
	
}
