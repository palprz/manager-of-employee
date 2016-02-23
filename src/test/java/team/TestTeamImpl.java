package test.java.team;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import main.java.employee.model.Employee;
import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class TestTeamImpl {

	@Mock
	private TeamDAOImpl teamDAOImpl = TeamDAOImpl.getInstance();
	
	@Mock
	private ProjectDAOImpl projectDAOImpl = ProjectDAOImpl.getInstance();
	
	private Project project;
	
	@Before
	public void setUp() {
		projectDAOImpl.removeProjects();
		teamDAOImpl.removeTeams();
		
		project = mock(Project.class);
		when(project.getId()).thenReturn(1);
		projectDAOImpl.addProject(project);
	}
	
	@Test
	public void TestAddTeam() {		
		Team team = mock(Team.class);
		when(team.getProject()).thenReturn(project);
		teamDAOImpl.addTeam(team);
		assertEquals(1, teamDAOImpl.getAllTeams().size());
	}

	@Test
	public void TestAddEmployee() {
		Team team = mock(Team.class);
		when(team.getId()).thenReturn(1);
		when(team.getProject()).thenReturn(project);
		
		Employee employee = mock(Employee.class);
		when(employee.getTeam()).thenReturn(team);

		//TODO
//		teamDAOImpl.addTeam(team);
//		verify()
	}

	@Test
	public void TestAddTeams() {
		List<Team> teams = new ArrayList<Team>();
		Team team1 = mock(Team.class);
		Team team2 = mock(Team.class);
		teams.add(team1);
		teams.add(team2);
		teamDAOImpl.addTeams(teams);
		assertEquals(2, teamDAOImpl.getAllTeams().size());		
	}

	@Test
	public void TestUpdateTeam() {
		Team oldTeam = mock(Team.class);
		when(oldTeam.getId()).thenReturn(1);
		when(oldTeam.getName()).thenReturn("Test");
		when(oldTeam.getProject()).thenReturn(project);
		
		Team newTeam = mock(Team.class);
		when(newTeam.getId()).thenReturn(2);
		when(newTeam.getName()).thenReturn("Test");
		when(newTeam.getProject()).thenReturn(project);
		
		teamDAOImpl.updateTeam(oldTeam, newTeam);
		assertEquals(2, teamDAOImpl.getTeam(0).getId());
	}
	
	@Test
	public void TestUpdateTeamLeader() {
		Employee oldTeamLeader = mock(Employee.class);
		when(oldTeamLeader.getId()).thenReturn(1);
		
		Team team = mock(Team.class);
		when(team.getId()).thenReturn(1);
		when(team.getProject()).thenReturn(project);
		when(team.getTeamLeader()).thenReturn(oldTeamLeader);
		
		Employee newTeamLeader = mock(Employee.class);
		when(newTeamLeader.getId()).thenReturn(2);
		when(newTeamLeader.getTeam()).thenReturn(team);
		
		teamDAOImpl.addTeam(team);
		teamDAOImpl.updateTeamLeader(newTeamLeader);
		//TODO
//		verify(newTeamLeader).
	}

	@Test
	public void TestRemoveEmployee() {
		Team team = mock(Team.class);
		when(team.getProject()).thenReturn(project);
		
		teamDAOImpl.addTeam(team);
		assertEquals(1, teamDAOImpl.getAllTeams().size());
		teamDAOImpl.removeTeam(team);
		assertEquals(0, teamDAOImpl.getAllTeams().size());
	}
	
	@Test
	public void TestGetTeam() {
		List<Team> teams = new ArrayList<Team>();
		Team team1 = mock(Team.class);
		when(team1.getId()).thenReturn(999);
		
		Team team2 = mock(Team.class);
		when(team2.getId()).thenReturn(1);
		
		teams.add(team1);
		teams.add(team2);
		
		teamDAOImpl.addTeams(teams);
		
		assertEquals(1, teamDAOImpl.getTeam(1).getId());
	}
	
	@Test
	public void TestFindByID() {
		Team team = mock(Team.class);
		when(team.getId()).thenReturn(5);
		when(team.getProject()).thenReturn(project);
		
		teamDAOImpl.addTeam(team);
		Team foundTeam = teamDAOImpl.findById(5);
		assertEquals(foundTeam, team);		
	}	

	@Test
	public void TestGetNextID() {
		Team team = mock(Team.class);
		when(team.getId()).thenReturn(5);
		when(team.getProject()).thenReturn(project);
		
		teamDAOImpl.addTeam(team);
		int nextID = teamDAOImpl.getNextID();
		assertEquals(6, nextID);		
	}
	
}
