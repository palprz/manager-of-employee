package main.java.launch;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import main.java.Paths;

public class Launcher {
	
	private static Logger log = Logger.getLogger(Launcher.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure(Paths.PATH_LOG4J_CFG);
		log.debug("Launch project");
		MainModel window = new MainModel();
	}
	
}