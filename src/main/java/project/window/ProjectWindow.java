package main.java.project.window;

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

import main.java.project.listener.ProjectListener;
import main.java.project.model.Project;
import main.java.team.model.Team;

public class ProjectWindow {
	
	public static JFrame frame = new JFrame();
	private Project project;
	private String action;
	private JTextField nameTextField;
	private JComboBox<Team> teamComboBox;

	/**
	 * Constructor
	 * @param windowAction
	 * @param selectRow
	 */
	public ProjectWindow(String windowAction, Project selectRow) {
		this.project = selectRow;
		this.action = windowAction;
		initWindow();
		initContent();
	}

	private void initContent() {
		JPanel panelLabel = new JPanel();
		JPanel panelField = new JPanel();
		JLabel nameLabel = new JLabel("Name:");
		nameTextField =  new JTextField(project == null ? "" : project.getName());
		nameTextField.setPreferredSize(new Dimension(200, 24));

		JLabel teamsLabel = new JLabel("Teams (only for show):");
		List<Team> teams = project != null ? project.getTeams() : new ArrayList<Team>();
		teamComboBox = new JComboBox<>();
		for (Team team : teams) {
			teamComboBox.addItem(team);
		}

		JPanel panelBottom = new JPanel();
		JButton okBtn = new JButton(getAction());
		okBtn.addActionListener(new ProjectListener(this));
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ProjectListener(this));

		Dimension prefSizeLabel = new Dimension(150, 15);
		Dimension prefSizeComboBox = new Dimension(150, 25);
		
		nameLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(nameLabel);
		teamsLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(teamsLabel);

		nameTextField.setPreferredSize(prefSizeComboBox);
		panelField.add(nameTextField);
		teamComboBox.setPreferredSize(prefSizeComboBox);
		panelField.add(teamComboBox);
		
		panelBottom.add(okBtn);
		panelBottom.add(cancelBtn);
		frame.add(panelLabel, BorderLayout.NORTH);
		frame.add(panelField, BorderLayout.CENTER);
		frame.add(panelBottom, BorderLayout.SOUTH);
	}

	private void initWindow() {
		frame = new JFrame();
		frame.setTitle("Project Window - " + getAction());
		frame.setSize(400, 150);
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
	 * Get name field
	 * @return nameField
	 */
	public JTextField getNameField() {
		return nameTextField;
	}

	/**
	 * Get names of teams field
	 * @return teamComboBox
	 */
	public JComboBox<Team> getTeamsField() {
		return teamComboBox;
	}

		
}
