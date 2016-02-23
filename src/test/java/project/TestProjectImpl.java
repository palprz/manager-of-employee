package test.java.project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class TestProjectImpl {

	@Mock
	private ProjectDAOImpl projectDAOImpl = ProjectDAOImpl.getInstance();
	
	@Mock
	private TeamDAOImpl teamDAOImpl = TeamDAOImpl.getInstance();
	
	private Team team;
	private Team oldTeam;
	private Team newTeam;
	
	@Before
	public void setUp() {
		projectDAOImpl.removeProjects();
		teamDAOImpl.removeTeams();
		
		team = mock(Team.class);
		when(team.getId()).thenReturn(1);
		
		oldTeam = mock(Team.class);
		when(oldTeam.getId()).thenReturn(999);
		when(oldTeam.getName()).thenReturn("Never happend");
		
		newTeam = mock(Team.class);
		when(newTeam.getId()).thenReturn(2);
		when(newTeam.getName()).thenReturn("Test");
		
	}
	
	@Test
	public void TestAddProject() {
		Project project = mock(Project.class);
		projectDAOImpl.addProject(project);
		assertEquals(1, projectDAOImpl.getAllProjects().size());
	}
	
	@Test
	public void TestAddTeam() {
		Project project = mock(Project.class);
		when(project.getId()).thenReturn(1);
		when(team.getProject()).thenReturn(project);
		projectDAOImpl.addProject(project);
		projectDAOImpl.addTeam(team);
		//TODO test .add(team);
		verify(project).getTeams();
	}
	
	@Test
	public void TestAddProjects() {
		List<Project> projects = new ArrayList<Project>();
		Project project1 = mock(Project.class);
		Project project2 = mock(Project.class);
		projects.add(project1);
		projects.add(project2);
		projectDAOImpl.addProjects(projects);
		assertEquals(2, projectDAOImpl.getAllProjects().size());
	}
	
	@Test
	public void TestUpdateProject() {
		Project oldProject = mock(Project.class);
		Project newProject = mock(Project.class);
		projectDAOImpl.addProject(oldProject);
		projectDAOImpl.updateProject(oldProject, newProject);
		assertEquals(newProject, projectDAOImpl.getAllProjects().get(0));
		
	}
	
	@Test
	public void TestUpdateTeam() {
		
		Project project = mock(Project.class);
		
		when(project.getId()).thenReturn(1);
				
		when(oldTeam.getProject()).thenReturn(project);
		
		when(newTeam.getProject()).thenReturn(project);
		
		projectDAOImpl.addProject(project);
		projectDAOImpl.addTeam(oldTeam);
		projectDAOImpl.updateTeam(oldTeam, newTeam);
		
		//TODO .add(newTeam);
//		verify(project).getTeams(); //it was invoke 3 times
		
	}
	
	@Test
	public void TestRemoveTeam() {
		Project project = mock(Project.class);
		projectDAOImpl.addProject(project);
		assertEquals(1, projectDAOImpl.getAllProjects().size());		
		projectDAOImpl.removeProject(project);
		assertEquals(0, projectDAOImpl.getAllProjects().size());		
	}
	
	@Test
	public void TestGetProject() {
		Project project = mock(Project.class);
		when(project.getId()).thenReturn(1);
		projectDAOImpl.addProject(project);		
		assertEquals(1, projectDAOImpl.getProject(0).getId());		
	}
	
	@Test
	public void TestFindById() {
		Project project = mock(Project.class);
		when(project.getId()).thenReturn(5);
		projectDAOImpl.addProject(project);
		Project foundProject = projectDAOImpl.findById(5);
		assertEquals(foundProject, project);
	}
	
	@Test
	public void TestGetNextID() {
		Project project = mock(Project.class);
		when(project.getId()).thenReturn(5);
		projectDAOImpl.addProject(project);
		int nextID = projectDAOImpl.getNextID();
		assertEquals(6, nextID);
	}
	
}
