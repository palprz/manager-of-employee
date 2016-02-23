package main.java.launch;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.employee.hibernate.HibernateEmployee;
import main.java.employee.model.Employee;
import main.java.employee.model.EmployeeDAOImpl;
import main.java.employee.table.EmployeeTable;
import main.java.employee.table.EmployeeTableModel;
import main.java.project.hibernate.HibernateProject;
import main.java.project.hibernate.IPrepareSynch;
import main.java.project.hibernate.PrepareSynchEmployee;
import main.java.project.hibernate.PrepareSynchProject;
import main.java.project.hibernate.PrepareSynchTeam;
import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.project.table.ProjectTable;
import main.java.project.table.ProjectTableModel;
import main.java.team.hibernate.HibernateTeam;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;
import main.java.team.table.TeamTable;
import main.java.team.table.TeamTableModel;

class MainModel {
	
	private static Logger log = Logger.getLogger(MainModel.class);
	private JTable table;
	
	public MainModel() {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);
		log.debug("Init main frame");
		MainWindow window = new MainWindow(this);
		window.initTopBar();
		initData();
		window.initWindow();
	}

	/**
	 * Init data with default employee table
	 */
	private void initData() {
		//Init instances of three tables
		EmployeeDAOImpl.getInstance();
		TeamDAOImpl.getInstance();
		ProjectDAOImpl.getInstance();

		//Init data into three tables
		addEmployeeJTable();
		addTeamJTable();
		addProjectJTable();
		
		//Set display table with employees
		setJTable("Employee");
	}

	/**
	 * Set new JTable (Employee/Team/Project). During setting check if there is another table in frame and remove it if it is
	 * @param nameOfTable
	 */
	public void setJTable(String nameOfTable) {
		log.debug("Set table: " + nameOfTable);
		Container c = MainWindow.getFrame().getContentPane();
		switch(nameOfTable) {
			case "Employee":
				containerCheck(c);
				addEmployeeJTable();
				break;
			case "Team":
				containerCheck(c);
				addTeamJTable();
				break;
			case "Project":
				containerCheck(c);
				addProjectJTable();
		}
		table.setRowHeight(20);
		//FIXME there is problem when we sort something and then try to select row to do something (wrong row)
//		table.setAutoCreateRowSorter(true);
//		table.getRowSorter().toggleSortOrder(0);
		
		c.setLayout(new BorderLayout());
		c.add(table.getTableHeader(), BorderLayout.PAGE_START);
		c.add(table, BorderLayout.CENTER);
		MainWindow.getFrame().setVisible(true);
	}
	
	/**
	 * Check and remove header of table and table from frame
	 * @param container
	 */
	private void containerCheck(Container container) {	
		if (container.getComponentCount() > 1) {
			container.remove(table);
			container.remove(table.getTableHeader());
		}
	}

	private void addEmployeeJTable() {
		table = new JTable(new EmployeeTableModel());
		EmployeeTable employeeTable = EmployeeTable.getInstance();
		employeeTable.setJTable(table);
		employeeTable.addRow(EmployeeDAOImpl.getInstance().getAllEmployees());		
	}

	private void addTeamJTable() {
		table = new JTable(new TeamTableModel());
		TeamTable teamTable = TeamTable.getInstance();
		teamTable.setJTable(table);
		teamTable.addRow(TeamDAOImpl.getInstance().getAllTeams());
	}

	private void addProjectJTable() {
		table = new JTable(new ProjectTableModel());
		ProjectTable projectTable = ProjectTable.getInstance();
		projectTable.setJTable(table);
		projectTable.addRow(ProjectDAOImpl.getInstance().getAllProjects());		
	}

	/**
	 * Save all data into database
	 */
	void saveAll() {
		IPrepareSynch preSynProject = new PrepareSynchProject();
		preSynProject.prepareSynch();
		
		IPrepareSynch preSynTeam = new PrepareSynchTeam();
		preSynTeam.prepareSynch();
		
		IPrepareSynch preSynEmp = new PrepareSynchEmployee();
		preSynEmp.prepareSynch();
	}
	
	/**
	 * Load all data from database into table and check what table is need to be display right now
	 */
	void loadAll() {
		HibernateEmployee hibEmp = new HibernateEmployee();
		List<Employee> employees = hibEmp.readAll();
		EmployeeDAOImpl.getInstance().removeEmployees();
		EmployeeDAOImpl.getInstance().addEmployees(employees);
		
		HibernateTeam hibTeam = new HibernateTeam();
		List<Team> teams = hibTeam.readAll();
		TeamDAOImpl.getInstance().removeTeams();
		TeamDAOImpl.getInstance().addTeams(teams);
		
		HibernateProject hibProject = new HibernateProject();
		List<Project> projects = hibProject.readAll();
		ProjectDAOImpl.getInstance().removeProjects();
		ProjectDAOImpl.getInstance().addProjects(projects);
		
		TableModel tableModel = table.getModel();
		if (tableModel instanceof EmployeeTableModel) {
			setJTable("Employee");
		} else if (tableModel instanceof TeamTableModel) {
			setJTable("Team");			
		} else {
			setJTable("Project");			
		}
	}

}
