package main.java.team.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.employee.model.Employee;
import main.java.project.model.Project;
import main.java.project.model.ProjectDAOImpl;
import main.java.team.listener.TeamListener;
import main.java.team.model.Team;

public class TeamWindow {
	
	public static JFrame frame = new JFrame();
	private Team team;
	private String action;
	private JTextField nameTextField;
	private JComboBox<Project> projectComboBox;
	private JComboBox<Employee> membersComboBox;
	private JComboBox<Employee> teamLeaderComboBox;

	/**
	 * Constructor
	 * @param windowAction
	 * @param selectRow
	 */
	public TeamWindow(String windowAction, Team selectRow) {
		this.team = selectRow;
		this.action = windowAction;
		initWindow();
		initContent();
	}

	private void initContent() {
		JPanel panelLabel = new JPanel();
		JPanel panelField = new JPanel();
		JLabel nameLabel = new JLabel("Name:");
		nameTextField =  new JTextField(team == null ? "" : team.getName());
		nameTextField.setPreferredSize(new Dimension(200, 24));

		JLabel projectLabel = new JLabel("Project:");
		List<Project> projects= ProjectDAOImpl.getInstance().getAllProjects();
		projectComboBox = new JComboBox<>();
		for (Project project : projects) {
			projectComboBox.addItem(project);
		}

		//Set correct selected item - I had problem with different references but for now it's working correctly
		for (int i = 0; i < projectComboBox.getItemCount(); i++) {
			if (team == null || team.getProject() == null) {
				projectComboBox.setSelectedIndex(0);
				break;
			}
			
			Project itemFromList = projectComboBox.getModel().getElementAt(i);
			if (team.getProject().getName().equals(itemFromList.getName())) {
				
				projectComboBox.setSelectedIndex(i);
				break;
			}
		}

		JLabel membersLabel = new JLabel("Members (only for show):");
		List<Employee> employees = team != null ? team.getEmployees() : new ArrayList<Employee>();
		membersComboBox = new JComboBox<>();
		for (Employee employee : employees) {
			membersComboBox.addItem(employee);
		}

		JLabel teamLeaderLabel = new JLabel("Team leader:");
		teamLeaderComboBox = new JComboBox<>();
		for (Employee employee : employees) {
			teamLeaderComboBox.addItem(employee);
		}
		teamLeaderComboBox.setSelectedItem(team != null ? team.getTeamLeader() : "");

		JPanel panelBottom = new JPanel();
		JButton okBtn = new JButton(getAction());
		okBtn.addActionListener(new TeamListener(this));
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new TeamListener(this));

		Dimension prefSizeLabel = new Dimension(150, 15);
		Dimension prefSizeComboBox = new Dimension(150, 25);
		
		nameLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(nameLabel);
		projectLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(projectLabel);
		membersLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(membersLabel);
		teamLeaderLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(teamLeaderLabel);
		
		nameTextField.setPreferredSize(prefSizeComboBox);
		panelField.add(nameTextField);
		projectComboBox.setPreferredSize(prefSizeComboBox);
		panelField.add(projectComboBox);
		membersComboBox.setPreferredSize(prefSizeComboBox);
		panelField.add(membersComboBox);
		teamLeaderComboBox.setPreferredSize(prefSizeComboBox);
		panelField.add(teamLeaderComboBox);		
		panelBottom.add(okBtn);
		panelBottom.add(cancelBtn);
		frame.add(panelLabel, BorderLayout.NORTH);
		frame.add(panelField, BorderLayout.CENTER);
		frame.add(panelBottom, BorderLayout.SOUTH);
	}

	private void initWindow() {
		frame = new JFrame();
		frame.setTitle("Team Window - " + getAction());
		frame.setSize(650, 150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Get action from window
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Get name of team from field
	 * @return nameField
	 */
	public JTextField getNameField() {
		return nameTextField;
	}
	
	/**
	 * Get project from field
	 * @return projectField
	 */
	public JComboBox<Project> getProjectField() {
		return projectComboBox;
	}

	/**
	 * Get members from field
	 * @return membersField
	 */
	public JComboBox<Employee> getMembersField() {
		return membersComboBox;
	}

	/**
	 * Get team leader from field
	 * @return teamLeaderField
	 */
	public JComboBox<Employee> getTeamLeaderField() {
		return teamLeaderComboBox;
	}

}
