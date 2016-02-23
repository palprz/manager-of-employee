package test.java.employee;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import main.java.employee.model.Employee;
import main.java.employee.model.EmployeeDAOImpl;
import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class TestEmployeeImpl {
	
	@Mock
	private EmployeeDAOImpl employeeDAOImpl = EmployeeDAOImpl.getInstance(); 

	@Mock
	private TeamDAOImpl teamDAOImpl = TeamDAOImpl.getInstance();
	
	@Mock
	private ProjectDAOImpl projectDAOImpl = ProjectDAOImpl.getInstance();
	
	private Team team;
	private Project project;
	
	@Before
	public void setUp() {
		teamDAOImpl.removeTeams();
		employeeDAOImpl.removeEmployees();

		project = mock(Project.class);
		when(project.getId()).thenReturn(1);
		projectDAOImpl.addProject(project);
		
		team = mock(Team.class);
		when(team.getId()).thenReturn(1);
		when(team.getProject()).thenReturn(project);
		teamDAOImpl.addTeam(team);
	}
	
	@Test
	public void TestAddEmployee() {
		Employee employee = mock(Employee.class);
		when(employee.getTeam()).thenReturn(team);
		employeeDAOImpl.addEmployee(employee);
		assertEquals(1, employeeDAOImpl.getAllEmployees().size());
	}
	
	@Test
	public void TestAddEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee1 = mock(Employee.class);
		Employee employee2 = mock(Employee.class);
		employees.add(employee1);
		employees.add(employee2);
		employeeDAOImpl.addEmployees(employees);
		assertEquals(2, employeeDAOImpl.getAllEmployees().size());
	}
	
	@Test
	public void TestUpdateEmployee() {
		Employee oldEmployee = mock(Employee.class);
		when(oldEmployee.getTeam()).thenReturn(team);
		when(oldEmployee.getName()).thenReturn("John");
		when(oldEmployee.getSurname()).thenReturn("Smith");
		employeeDAOImpl.addEmployee(oldEmployee);
		assertEquals(1, employeeDAOImpl.getAllEmployees().size());
		
		Employee newEmployee = mock(Employee.class);
		when(newEmployee.getTeam()).thenReturn(team);
		when(newEmployee.getName()).thenReturn("John");
		when(newEmployee.getSurname()).thenReturn("Smith");
		employeeDAOImpl.updateEmployee(oldEmployee, newEmployee);
	}
	
	@Test
	public void TestUpdateTeamAndProject() {
		Employee employee = mock(Employee.class);
		when(employee.getTeam()).thenReturn(team);
		employeeDAOImpl.addEmployee(employee);	
		assertEquals(1, employeeDAOImpl.getAllEmployees().size());
		employeeDAOImpl.updateTeamAndProject(team);
		//TODO I couldn't add null into mock - find the solution
//		assertEquals(team, employeeDAOImpl.getEmployee(0).getTeam());
//		assertEquals(project, employeeDAOImpl.getEmployee(0).getProject());
	}
	
	@Test
	public void TestRemoveEmployee() {
		Employee employee = mock(Employee.class);
		when(employee.getTeam()).thenReturn(team);
		employeeDAOImpl.addEmployee(employee);
		assertEquals(1, employeeDAOImpl.getAllEmployees().size());
		employeeDAOImpl.removeEmployee(employee);
		assertEquals(0, employeeDAOImpl.getAllEmployees().size());		
	}
	
	@Test
	public void TestRemoveTeam() {
		Employee employee = mock(Employee.class);
		when(employee.getTeam()).thenReturn(team);
		employeeDAOImpl.addEmployee(employee);
		assertEquals(1, employeeDAOImpl.getEmployee(0).getTeam().getId());
		employeeDAOImpl.removeTeam(team);
		//TODO I couldn't add null into mock - find the solution
//		assertEquals(null, employeeDAOImpl.getEmployee(0).getTeam());
	}
	
	@Test
	public void TestGetEmployee() {
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee1 = mock(Employee.class);
		Employee employee2 = mock(Employee.class);
		
		employees.add(employee1);
		employees.add(employee2);
		
		employeeDAOImpl.addEmployees(employees);
		assertEquals(2, employeeDAOImpl.getAllEmployees().size());
	}
	
	@Test
	public void TestFindByID() {
		Employee employee = mock(Employee.class);
		when(employee.getId()).thenReturn(5);
		when(employee.getTeam()).thenReturn(team);
		
		employeeDAOImpl.addEmployee(employee);
		Employee foundEmployee = employeeDAOImpl.findById(5);
		assertEquals(foundEmployee, employee);		
	}
	
	@Test
	public void TestGetNextID() {
		Employee employee = mock(Employee.class);
		when(employee.getId()).thenReturn(5);
		when(employee.getTeam()).thenReturn(team);
		
		employeeDAOImpl.addEmployee(employee);
		int nextID = employeeDAOImpl.getNextID();
		assertEquals(6, nextID);	
	}
	
}
