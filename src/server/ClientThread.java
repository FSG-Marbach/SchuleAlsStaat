package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import essentials.Settings;
import essentials.SimpleLog;

public class ClientThread extends Thread {

	SimpleLog log;
	SSLSocket socket;
	BufferedReader reader;
	DataOutputStream writer;
	Settings settings, passwords, permissions;

	String clientName, password, permissionsGroup;
	int id;

	public void run() {

		log.info("Client " + id + " connected");

		// Initialize session
		try {

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new DataOutputStream(socket.getOutputStream());

			// Receive client name and password
			try {
				clientName = reader.readLine();
				password = reader.readLine();

				if (passwords.getSetting(clientName) != null) {

					// Syndicate password
					if (passwords.getSetting(clientName).equals(password)) {

						// Reading permissions group
						if (permissions.getSetting(clientName) != null) {

							permissionsGroup = permissions.getSetting(clientName);
							String[] allowedCommands = permissions.getArray(permissionsGroup);

							// Receiving and managing commands
							boolean b = true;
							while (b) {

								// Receiving command
								String request = null;
								try {
									request = reader.readLine();
									log.info("Client " + id + " sent command '" + request + "'");

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
											b = false;
											break;
										case "reload":
											Commands.reload(id, command, log, passwords, permissions);
											break;
										case "shutdown":
											Commands.shutdown(id, command);
											break;
										default:
											log.warning("Client " + id + " sent unimplemented command ('" + request
													+ "') with permission!");
											break;
										}
									} else {
										log.warning("Client " + id + " tried to execute '" + request
												+ "' without permission!");
									}

								} catch (IOException e) {
									log.error("Error occurred while receiving data from client " + id);
									log.logStackTrace(e);
									b = false;
								}
							}

						} else {
							log.error("Error occurred while reading permissions group of client " + id + "!");
						}
					} else {

						log.warning("Authentication of client " + id + " failed! (Wrong password)");

						try {
							writer.writeBytes("Wrong password");
						} catch (IOException e) {
							log.warning(
									"Couldn't sent authentication message ('Wrong password') to client " + id + "!");
							log.logStackTrace(e);
						}
					}
				} else {

					log.warning("Authentication of client " + id + " failed! (No entry of '" + clientName + "' in '"
							+ settings.getSetting("passwordsPath") + ")");

					try {
						writer.writeBytes("Invalid name");
					} catch (IOException e) {
						log.warning("Couldn't sent authentication message ('Invalid name') to client " + id + "!");
						log.logStackTrace(e);
					}
				}

			} catch (IOException e) {
				log.error("Error occurred while receiving name or password!");
				log.logStackTrace(e);
			}

		} catch (IOException e) {
			log.error("Error occurred while initializing session in client " + id + "!");
			log.logStackTrace(e);
		}

		// Closing socket
		try {
			log.debug("Closing socket of client " + id);
			socket.close();
			log.info("Closed socket of client " + id);
		} catch (IOException e) {
			log.warning("Error occurred while closing socket of client  " + id);
			log.logStackTrace(e);
		}
	}

	public ClientThread(SSLSocket socket, int id, SimpleLog log, Settings settings, Settings passwords,
			Settings permissions) {
		this.socket = socket;
		this.id = id;
		this.log = log;
		this.passwords = passwords;
		this.permissions = permissions;
		this.settings = settings;
	}
}
