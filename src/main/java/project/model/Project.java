package main.java.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import main.java.team.model.Team;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="id_project")
	private List<Team> teams;
	
	/**
	 * Default constructor
	 */
	public Project() {
	}
	
	/**
	 * Create new project
	 * @param name
	 * @param teams
	 */
	public Project(String name, List<Team> teams) {
		this.id = ProjectDAOImpl.getInstance().getNextID();
		this.name = name;
		this.teams = teams;
	}

	/**
	 * Set ID of project
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get ID of project
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get name of project
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name of project
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get list of teams of project
	 * @return teams
	 */
	public List<Team> getTeams() {
		return teams;
	}
	
	/**
	 * Set list of teams of project
	 * @param teams
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
