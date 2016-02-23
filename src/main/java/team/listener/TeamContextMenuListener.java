package main.java.team.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;
import main.java.team.model.TeamDAOImpl;
import main.java.team.table.TeamTable;
import main.java.team.window.TeamWindow;

public class TeamContextMenuListener implements ActionListener {

	  private static Logger log = Logger.getLogger(TeamContextMenuListener.class);
	  private TeamTable teamTable;
	  
	  public TeamContextMenuListener(TeamTable inputData) {
		  PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);
		  this.teamTable = inputData;
	  }
	  
	  /**
	   * Handle event with action
	   */
	  public void actionPerformed(ActionEvent e) {
		  log.debug("Action event: " + e.getActionCommand());
		  switch (e.getActionCommand()) {
			  case "Add...":
				  new TeamWindow("Adding", null);
				  break;
			  case "Update...":
				  new TeamWindow("Updating", teamTable.getSelectRow());
				  break;
			  case "Remove...":
				  log.debug("Team to remove: " + teamTable.getSelectRow().toString());
				  TeamDAOImpl.getInstance().removeTeam(teamTable.getSelectRow());
				  
				  TeamTable teamTable = TeamTable.getInstance();
				  teamTable.removeRow(teamTable.getSelectRow());
				  teamTable.getTableModel().fireTableDataChanged();
				  break;
		  }
	  }
	  
}
