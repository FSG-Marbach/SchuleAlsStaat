package server;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import essentials.Settings;
import essentials.SimpleLog;

/**
 * @author Felix Beutter
 */

public class Server {

	static SimpleLog log;
	static Settings settings, passwords, permissions;
	SSLServerSocket serverSocket;

	final String version = "0.1.2";
	String keystore, password, permissionsPath, passwordsPath;

	int port = 3746;
	int id = 0;

	public Server() {

		// Create log file
		log = new SimpleLog(new File("log.txt"), true, true);

		String message = "Started 'Citizen Data Management System' server | Version: " + version;

		String s = "";
		for (int i = 0; i < message.length(); i++)
			s = s + "=";

		log.info(s);
		log.info(message);
		log.info(s);

		// Initializing settings
		Properties defaultValues = new Properties();
		defaultValues.setProperty("port", String.valueOf(port));
		defaultValues.setProperty("keystore", "server.keystore");
		defaultValues.setProperty("password", "123456");
		defaultValues.setProperty("permissionsPath", "permissions.properties");
		defaultValues.setProperty("passwordsPath", "passwords.properties");
		settings = new Settings(new File("settings.properties"), defaultValues, false, log);
		settings.setComment("Syntax: [Key]=[Value]", true);

		// Setting variables
		try {
			port = Integer.parseInt(settings.getSetting("port"));
		} catch (NumberFormatException e) {
			settings.setSetting("port", String.valueOf(port));
			log.error("Error occurred while reading port from settings.properties! Default port (" + port
					+ ") will be used...");
		}

		keystore = settings.getSetting("keystore");
		password = settings.getSetting("password");
		passwordsPath = settings.getSetting("passwordsPath");
		permissionsPath = settings.getSetting("permissionsPath");

		// Creating/checking local files
		if (keystore == null || !new File(keystore).exists()) {
			log.fatal("Missing keystore file! Couldn't create server. Shutting down...");
			System.exit(1);
		}

		// Initializing client passwords
		passwords = new Settings(new File(passwordsPath), new Properties(), false, log);
		passwords.setComment("Syntax: [Client name]=[Password]", true);

		// Initializing client permissions
		permissions = new Settings(new File(permissionsPath), new Properties(), false, log);
		permissions.setComment(
				"Syntax: [Client name]=[Permissions group] or [Permissions group]=[Array of permissions]", true);

		// Configuring SSLServer
		System.setProperty("javax.net.ssl.keyStore", keystore);
		System.setProperty("javax.net.ssl.keyStorePassword", password);

		// Creating SSLServer
		try {
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			serverSocket = (SSLServerSocket) factory.createServerSocket(port);
		} catch (IOException e) {
			log.fatal("Fatal error occurred while creating server! Shutting down...");
			log.logStackTrace(e);
			System.exit(1);
		}

		log.info("Successfully created CDMS server on port " + port);
		log.info("Waiting for clients...");

		// Accepting clients
		while (true) {

			SSLSocket client = null;
			try {
				client = (SSLSocket) serverSocket.accept();
			} catch (IOException e) {
				log.error("Error occurred while accepting a client (id: " + id + ")! Closing socket...");
				log.logStackTrace(e);
			}
			if (client != null)
				new ClientThread(client, id).start();
			id++;
		}
	}

	static SimpleLog getLog() {
		return log;
	}

	static Settings getSettings() {
		return settings;
	}

	static Settings getPasswords() {
		return passwords;
	}

	static void setPasswords(Settings s) {
		passwords = s;
	}

	static Settings getPermissions() {
		return permissions;
	}

	static void setPermissions(Settings s) {
		permissions = s;
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
