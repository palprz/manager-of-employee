package main.java.team.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;

import main.java.Paths;
import main.java.team.model.Team;
import main.java.utils.HibernateUtils;

public class HibernateTeam {
	
	private static Logger log = Logger.getLogger(HibernateTeam.class);
	
	public HibernateTeam() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);	
	}
	
	/**
	 * Save team into database
	 * @param team
	 */
	public void save(Team team) {
		log.debug("Saving data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(team);
		session.getTransaction().commit();
		session.close();
		log.debug("Save data complete!");
	}	

	/**
	 * Load teams from database
	 * @return teams
	 */
	public List<Team> readAll() {
		log.debug("Loading data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Team> teams = session.createQuery("FROM Team").list();
		session.close();
		log.debug("Loading data complete!");
		return teams;
	}

	/**
	 * Update team into database
	 * @param newTeam
	 */
	public void update(Team newTeam) {
		log.debug("Update data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		Team oldTeam= (Team) session.load(Team.class, newTeam.getId());
		
		oldTeam.setName(newTeam.getName());
		oldTeam.setProject(newTeam.getProject());
		oldTeam.setTeamLeader(newTeam.getTeamLeader());
		
		session.getTransaction().commit();
		session.close();
		log.debug("Update data complete!");
	}
	
	/**
	 * Delete team from database
	 * @param team
	 */
	public void delete(Team team) {
		log.debug("Delete data...");
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(team);
		session.getTransaction().commit();
		session.close();
		log.debug("Delete data complete!");
	}
	
}
