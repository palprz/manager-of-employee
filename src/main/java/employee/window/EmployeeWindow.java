package main.java.employee.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.employee.listener.EmployeeListener;
import main.java.employee.model.Employee;
import main.java.team.model.Team;
import main.java.team.model.TeamDAOImpl;

public class EmployeeWindow {
	
	public static JFrame frame = new JFrame();
	private Employee employee;
	private String action;
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JTextField salaryTextField;
	private JComboBox<Team> teamComboBox;
	private JTextField projectComboBox;

	/**
	 * Constructor
	 * @param windowAction
	 * @param selectRow
	 */
	public EmployeeWindow(String windowAction, Employee selectRow) {
		this.employee = selectRow;
		this.action = windowAction;
		initWindow();
		initContent();
	}

	private void initContent() {
		JPanel panelLabel = new JPanel();
		JPanel panelField = new JPanel();
		JLabel nameLabel = new JLabel("Name:");
		nameTextField =  new JTextField(employee == null ? "" : employee.getName());
		nameTextField.setPreferredSize(new Dimension(200, 24));

		JLabel surnameLabel = new JLabel("Surname:");
		surnameTextField =  new JTextField(employee == null ? "" : employee.getSurname());
		surnameTextField.setPreferredSize(new Dimension(200, 24));

		JLabel salaryLabel = new JLabel("Salary:");
		salaryTextField =  new JTextField(employee == null ? "" : String.valueOf(employee.getSalary()));
		salaryTextField.setPreferredSize(new Dimension(200, 24));

		JLabel teamLabel = new JLabel("Team:");
		List<Team> teams = TeamDAOImpl.getInstance().getAllTeams();
		teamComboBox = new JComboBox<>();
		for (Team team : teams) {
			teamComboBox.addItem(team);
		}
		
		//Set correct selected item - I had problem with different references but for now it's working correctly
		for (int i = 0; i < teamComboBox.getItemCount(); i++) {
			if (employee == null || employee.getTeam() == null) {
				break;
			}
			
			Team itemFromList = teamComboBox.getModel().getElementAt(i);
			if (employee.getTeam().getName().equals(itemFromList.getName())) {
				teamComboBox.setSelectedIndex(i);
				break;
			}
		}
		//when I changed combo box with team there need to be another change with project combo box
		teamComboBox.addActionListener(new EmployeeListener(this));
		
		JLabel projectLabel = new JLabel("Project (only for show):");
		Team teamFromComboBox = ((Team) teamComboBox.getSelectedItem());
		projectComboBox =  new JTextField(teamFromComboBox.getProject() != null ? teamFromComboBox.getProject().getName() : "");
		projectComboBox.setPreferredSize(new Dimension(200, 24));
		projectComboBox.setEnabled(false);
		
		JPanel panelBottom = new JPanel();
		JButton okBtn = new JButton(getAction());
		okBtn.addActionListener(new EmployeeListener(this));
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new EmployeeListener(this));

		Dimension prefSizeLabel = new Dimension(150, 15);
		Dimension prefSizeComboBox = new Dimension(150, 25);
		
		nameLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(nameLabel);
		surnameLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(surnameLabel);
		salaryLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(salaryLabel);
		teamLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(teamLabel);
		projectLabel.setPreferredSize(prefSizeLabel);
		panelLabel.add(projectLabel);

		nameTextField.setPreferredSize(prefSizeComboBox);
		panelField.add(nameTextField);
		surnameTextField.setPreferredSize(prefSizeComboBox);
		panelField.add(surnameTextField);
		salaryTextField.setPreferredSize(prefSizeComboBox);
		panelField.add(salaryTextField);
		teamComboBox.setPreferredSize(prefSizeComboBox);
		panelField.add(teamComboBox);
		projectComboBox.setPreferredSize(prefSizeComboBox);
		panelField.add(projectComboBox);
		
		panelBottom.add(okBtn);
		panelBottom.add(cancelBtn);
		frame.add(panelLabel, BorderLayout.NORTH);
		frame.add(panelField, BorderLayout.CENTER);
		frame.add(panelBottom, BorderLayout.SOUTH);
	}

	private void initWindow() {
		frame = new JFrame();
		frame.setTitle("Employee Window - " + getAction());
		frame.setSize(900, 150);
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
	 * Get surname field
	 * @return surnameField
	 */
	public JTextField getSurnameField() {
		return surnameTextField;
	}

	/**
	 * Get salary field
	 * @return salaryField
	 */
	public JTextField getSalaryField() {
		return salaryTextField;
	}

	/**
	 * Get team field
	 * @return teamField
	 */
	public JComboBox<Team> getTeamField() {
		return teamComboBox;
	}

	/**
	 * Get project field
	 * @return projectField
	 */
	public JTextField getProjectField() {
		return projectComboBox;
	}
	
}
