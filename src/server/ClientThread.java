package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.security.sasl.AuthenticationException;

import essentials.SimpleLog;

public class ClientThread extends Thread {

	SimpleLog log;
	SSLSocket socket;
	BufferedReader reader;
	DataOutputStream writer;

	String clientName, password, permissionsGroup;

	int id;

	public void run() {

		boolean goOn = true;

		// Configure log
		log = Server.getLog();

		log.info("Client " + id + " connected");

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
				if (Server.getPermissions().getSetting(clientName) != null) {
					permissionsGroup = Server.getPermissions().getSetting(clientName);
				} else {
					log.error("Error occurred while reading permissions group of client " + id + "!");
					goOn = false;
				}

				if (goOn) {

					log.info("Successful authentication of client " + id);

					String[] allowedCommands = Server.getPermissions().getArray(permissionsGroup);

					// Receiving and managing commands
					while (goOn) {

						// Receiving command
						String request = null;
						try {
							request = reader.readLine();
							log.info("Client " + id + " sent command '" + request + "'");
						} catch (IOException e) {
							log.error("Error occurred while receiving data from client " + id);
							log.logStackTrace(e);
							goOn = false;
						}

						if (goOn) {

							String[] command = request.split(" ");

							// Checking for permission
							boolean included = false;
							for (String s : allowedCommands)
								if (s.equals(command[0])) {
									included = true;
									break;
								}

							if (included) {

								// Executing command
								switch (command[0]) {
								case "disconnect":
									goOn = false;
									break;
								case "reload":
									Commands.reload(id, command);
									break;
								default:
									log.warning("Client " + id + " sent unimplemented command (' + " + command
											+ "') with permission!");
									break;
								}
							} else {
								log.warning(
										"Client " + id + " tried to execute '" + command + "' (without permission)!");
							}
						}
					}
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
	}

	void authenticateSession() throws IOException {

		// Receive name and password
		clientName = reader.readLine();
		password = reader.readLine();

		// Checking password
		if (Server.getPasswords().getSetting(clientName) == null)
			throw new AuthenticationException("No entry of '" + clientName + "' in '"
					+ Server.getSettings().getSetting("Server.getPasswords()Path") + "'!");
		if (!Server.getPasswords().getSetting(clientName).equals(password))
			throw new AuthenticationException("Wrong password!");
	}

	public ClientThread(SSLSocket s, int i) {
		socket = s;
		id = i;
	}
}