package accessControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import clientLibrary.Connection;
import essentials.Settings;
import essentials.SimpleLog;

/**
 * @author Johannes Groﬂ
 */

public class Main {

	final static String path = "res\\accesscontroll\\";

	public static File logfile = new File(path + "log.txt");
	public static File settingfile = new File(path + "setting.properties");
	public static boolean allowstudentpictures = false;
	public static Connection connection;

	static SimpleLog log;
	static Settings settings;
	static FileInputStream in;
	static String ip;
	static int port;
	static String user;
	static String passwd;
	static String trustStore;
	static String trustStorePasswd;

	public static void main(String[] args) {

		log = new SimpleLog(logfile, true, true);
		log.log("Startet  'Citizen Data Management System' accesscontrollclient");

		Properties defaultValues = new Properties();
		defaultValues.setProperty("allowstudentpictures", "false");
		defaultValues.setProperty("port", "3746");
		defaultValues.setProperty("truststore", "res/client/client.truststore");
		defaultValues.setProperty("trustStorePasswd", "123456");
		defaultValues.setProperty("password", "123456");
		defaultValues.setProperty("user", "root");
		defaultValues.setProperty("ip", "127.0.0.1");
		settings = new Settings(settingfile, defaultValues, false, log);

		if (settings.getSetting("allowstudentpictures").equals("true")) {
			allowstudentpictures = true;
		} else {
			allowstudentpictures = false;
		}
		ip = (String) settings.getSetting("ip");
		port = Integer.parseInt(settings.getSetting("port"));
		user = (String) settings.getSetting("user");
		passwd = (String) settings.getSetting("password");
		trustStore = (String) settings.getSetting("allowstudentpictures");
		trustStorePasswd = (String) settings.getSetting("truststorePassword");


//		try {
//			connection.connect(ip, port, user, passwd, trustStore, trustStorePasswd);
//		} catch (IOException e) {
//			log.logStackTrace(e);
//		}

		Gui gui = new Gui();
		log.log("Initialize GUI...");
		gui.initialize();
		log.log("GUI initialized");

	}

}
