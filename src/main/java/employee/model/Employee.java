package main.java.employee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.java.project.model.Project;
import main.java.team.model.Team;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "salary")
	private int salary;
	
	@ManyToOne
	@JoinColumn(name = "id_team")
	private Team team;

	@ManyToOne
	@JoinColumn(name = "id_project")
	private Project project;
	
	/**
	 * Default constructor
	 */
	public Employee() {
	}
	
	/**
	 * Create Employee with data
	 * @param name
	 * @param surname
	 * @param salary
	 * @param team
	 * @param project
	 */
	public Employee(String name, String surname, int salary, Team team, Project project) {
		this.id = EmployeeDAOImpl.getInstance().getNextID();
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.team = team;
		this.project = project;
	}
	
	/**
	 * Set ID of employee
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Get ID of employee
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set name of employee
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get name of employee
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set surname of employee
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Get surname of employee
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Set salary of employee
	 * @param salary
	 */
	public void setSalary(int salary) {
		this.salary = salary;
	}

	/**
	 * Get salary of employee
	 * @return salary
	 */
	public int getSalary() {
		return salary;
	}
	
	/**
	 * Set team of employee
	 * @param team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Get team of employee
	 * @return team
	 */
	public Team getTeam() {
		return team;
	}
	
	/**
	 * Set project of employee
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Get project of employee
	 * @return project
	 */
	public Project getProject() {
		return project;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.surname; 
	}
	
}
