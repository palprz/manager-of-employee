package main.java.launch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.employee.window.EmployeeWindow;
import main.java.project.window.ProjectWindow;
import main.java.team.window.TeamWindow;

class MainWindow {
	
	private static Logger log = Logger.getLogger(MainWindow.class);
	private static JFrame frame = new JFrame();
	private MainModel model;

	MainWindow(MainModel model) {
		this.model = model;
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);
	}

	/**
	 * Init view of top wrap of frame
	 */
	void initTopBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu optionMenu = new JMenu("Options");
		JMenu employeeMenu = new JMenu("Employee");
		JMenu teamMenu = new JMenu("Team");
		JMenu projectMenu = new JMenu("Project");

		JMenuItem optionItemSynch = new JMenuItem("Save into database");
		optionItemSynch.setToolTipText("Save all changes into database");
		optionItemSynch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	int popupResult = JOptionPane.showConfirmDialog (null, "Are you sure to save all data into database?", "Info", 0);
            	if(popupResult == JOptionPane.YES_OPTION){
            		model.saveAll();
            	}
            }
        });
		JMenuItem optionItemLoad = new JMenuItem("Load from database");
		optionItemLoad.setToolTipText("Load all data from database");
		optionItemLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	int popupResult = JOptionPane.showConfirmDialog (null, "Are you sure to load all data from database?", "Info", 0);
            	if(popupResult == JOptionPane.YES_OPTION){
            		model.loadAll();
            	}
            }
        });
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setToolTipText("Quit application");
		exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	log.debug("Exit application");
                System.exit(0);
            }
        });
		optionMenu.add(optionItemSynch);
		optionMenu.add(optionItemLoad);
		optionMenu.addSeparator();
		optionMenu.add(exitItem);
		menuBar.add(optionMenu);

		JMenuItem employeeItemOpenTable = new JMenuItem("Open table with employees");
		employeeItemOpenTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	model.setJTable("Employee");
            }
        });
		JMenuItem employeeItemAdd = new JMenuItem("Add employee");
		employeeItemAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	new EmployeeWindow("Adding", null);
            }
        });
		employeeMenu.add(employeeItemOpenTable);
		employeeMenu.add(employeeItemAdd);
		menuBar.add(employeeMenu);
		
		JMenuItem teamItemOpenTable = new JMenuItem("Open table with teams");
		teamItemOpenTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	model.setJTable("Team");
            }
        });
		JMenuItem teamItemAdd = new JMenuItem("Add team");
		teamItemAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	new TeamWindow("Adding", null);
            }
        });
		teamMenu.add(teamItemOpenTable);
		teamMenu.add(teamItemAdd);
		menuBar.add(teamMenu);

		JMenuItem projectItemOpenTable = new JMenuItem("Open table with projects");
		projectItemOpenTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	model.setJTable("Project");
            }
        });
		JMenuItem projectItemAdd = new JMenuItem("Add project");
		projectItemAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	new ProjectWindow("Adding", null);
            }
        });
		projectMenu.add(projectItemOpenTable);
		projectMenu.add(projectItemAdd);
		menuBar.add(projectMenu);
		
		frame.setJMenuBar(menuBar);		
	}

	/**
	 * Init view of main frame Manager of Employee
	 */
	void initWindow() {
		frame.setTitle("Manager of Employee v0.0.1");
		frame.setSize(1200, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		
	}
	
	/**
	 * Get frame from main window
	 * @return 
	 */
	static JFrame getFrame() {
		return frame;
	}
			
}
