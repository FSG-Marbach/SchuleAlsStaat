package accessControlClient;

import java.io.File;

import clientLibrary.Connection;
import essentials.SimpleLog;

/**
 * @author Johannes Groﬂ
 */

public class Main {

	static SimpleLog log;
	final static String path = "res\\client\\";
	public static File logfile = new File(path + "accesscontrollclientlog.txt");
	public static boolean allowstudentpictures = true;
	public static Connection connection = new Connection(log);
	
	public static void main(String[] args) {
	
		log = new SimpleLog(logfile, true, true);
		log.log("Startet  'Citizen Data Management System' accesscontrollclient");
		log.log("allow studentpictures: " + allowstudentpictures);

		Gui gui = new Gui();
		log.log("Initialize GUI...");
		gui.initialize();
		log.log("GUI initialized");
		
	}

}
