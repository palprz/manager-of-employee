package main.java.team.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import main.java.employee.model.Employee;
import main.java.project.model.Project;

@Entity
@Table(name = "team")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "id_project")
	private Project project;

	@OneToOne
	@JoinColumn(name = "id_team_leader")
	private Employee teamLeader;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="id_team")
	private List<Employee> employees;
	
	/**
	 * Default constructor
	 */
	public Team() {
	}
	
	/**
	 * Create new team
	 * @param name
	 * @param project
	 * @param teamLeader
	 * @param employees
	 */
	public Team(String name, Project project, Employee teamLeader, List<Employee> employees) {
		this.id = TeamDAOImpl.getInstance().getNextID();
		this.name = name;
		this.project = project;
		this.teamLeader = teamLeader;
		this.employees = employees;
	}

	/**
	 * Set ID of team
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get ID of team
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get name of team
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name of team
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get project of team
	 * @return project
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * Set project of team
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Get team leader of team
	 * @return teamLeader
	 */
	public Employee getTeamLeader() {
		return teamLeader;
	}
	
	/**
	 * Set team leader of team
	 * @param teamLeader
	 */
	public void setTeamLeader(Employee teamLeader) {
		this.teamLeader = teamLeader;
	}

	/**
	 * Get list of employees of team
	 * @return employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}
	
	/**
	 * Set list of employees of team
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return this.name; 
	}
		
}
