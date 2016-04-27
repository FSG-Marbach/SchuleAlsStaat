package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.security.sasl.AuthenticationException;

import essentials.Settings;
import essentials.SimpleLog;

public class ClientThread extends Thread {

	SimpleLog log;
	SSLSocket socket;
	BufferedReader reader;
	DataOutputStream writer;
	Settings settings, passwords, permissions;

	String clientName, password;

	int id, permissionsGroup;

	public void run() {

		boolean goOn = true;

		// Configure log
		log = Server.getLog();

		// Initialize session
		try {
			initializeSession();
		} catch (IOException e) {
			log.error("Error occurred while initializing session in client " + id + "!");
			log.logStackTrace(e);
			goOn = false;
		}

		if (goOn) {

			// Authenticate session
			try {
				authenticateSession();
			} catch (IOException e) {
				log.warning("Authentication of client " + id + " failed!");
				log.logStackTrace(e);
				goOn = false;
			}

			if (goOn) {

				// Reading permissions group
				String group = permissions.getSetting(clientName);
				if (group != null) {

					try {
						permissionsGroup = Integer.parseInt(group);
						log.info("Registered client " + id + " to permissions group " + permissionsGroup);
					} catch (NumberFormatException e) {
						log.error(
								"NumberFormatException occurred while reading permissions group of client " + id + "!");
						goOn = false;
					}
				} else {
					log.error("NullPointerException occurred while reading permissions group of client " + id + "!");
					goOn = false;
				}

				if (goOn) {

					log.info("Successful authentication and initialization of client " + id);

					// TODO
				}
			}
		}

		// Closing socket
		try {
			log.info("Closing socket of client " + id);
			socket.close();
			log.info("Closed socket of client " + id);
		} catch (IOException e) {
			log.warning("Error occurred while closing socket of client  " + id);
			log.logStackTrace(e);
		}
	}

	void initializeSession() throws IOException {

		// Initialize reader and writer
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new DataOutputStream(socket.getOutputStream());

		// Getting settings objects
		settings = Server.getSettings();
		passwords = Server.getPasswords();
		permissions = Server.getPermissions();
	}

	void authenticateSession() throws IOException {

		// Receive name and password
		clientName = reader.readLine();
		password = reader.readLine();

		// Checking password
		if (passwords.getSetting(clientName) == null)
			throw new AuthenticationException(
					"No entry of '" + clientName + "' in '" + settings.getSetting("passwordsPath") + "'!");
		if (!passwords.getSetting(clientName).equals(password))
			throw new AuthenticationException("Wrong password!");
	}

	public ClientThread(SSLSocket s, int i) {
		socket = s;
		id = i;
	}
}