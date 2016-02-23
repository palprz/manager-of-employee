package main.java.project.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;

import main.java.Paths;
import main.java.project.model.Project;
import main.java.utils.HibernateUtils;

public class HibernateProject {
	
	private static Logger log = Logger.getLogger(HibernateProject.class);
	
	public HibernateProject() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);	
	}
		
	/**
	 * Save project into database
	 * @param projects
	 */
	void save(Project project) {
		log.debug("Saving data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(project);
		session.getTransaction().commit();
		session.close();
		log.debug("Save data complete!");
	}	

	/**
	 * Load projects from database
	 * @return projects
	 */
	public List<Project> readAll() {
		log.debug("Loading data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Project> projects = session.createQuery("FROM Project").list();
		session.close();
		log.debug("Loading data complete!");
		return projects;
	}
	
	/**
	 * Update project into database
	 * @param newProject
	 */
	void update(Project newProject) {
		log.debug("Update data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		Project oldProject = (Project) session.load(Project.class, newProject.getId());
		
		oldProject.setName(newProject.getName());
		
		session.getTransaction().commit();
		session.close();
		log.debug("Update data complete!");
	}
	
	/**
	 * Delete project from database
	 * @param project
	 */
	void delete(Project project) {
		log.debug("Delete data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(project);
		session.getTransaction().commit();
		session.close();
		log.debug("Delete data complete!");
	}
	
}
