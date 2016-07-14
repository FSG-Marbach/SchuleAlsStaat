package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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

						log.info("Successful authentication of client " + id + " (Name: " + clientName + ")");

						try {
							writer.writeBytes("Successful authentication\n");

							// Reading permissions group
							if (permissions.getSetting(clientName) != null) {

								String[] allowedCommands = permissions.getArray(clientName);

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
										if (Arrays.asList(allowedCommands).contains(command[0])) {

											// Executing command
											switch (command[0]) {
											case "reload":
												writer.writeBytes(
														Commands.reload(id, command, log, passwords, permissions)
																+ "\n");
												break;
											default:
												log.warning("Client " + id + " sent not implemented command ('"
														+ request + "') with permission!");
												writer.writeBytes("Invalid command\n");
												break;
											}
										} else {
											log.warning("Client " + id + " tried to execute '" + request
													+ "' without permission (which may not be implemented)!");
											writer.writeBytes("Invalid command\n");
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
						} catch (IOException e) {
							log.warning("Couldn't sent authentication message ('Successful authentication') to client "
									+ id + "!");
							log.logStackTrace(e);
						}
					} else {

						log.warning("Authentication of client " + id + " failed! (Wrong password)");

						try {
							writer.writeBytes("Authentication failed\n");
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
						writer.writeBytes("Authentication failed\n");
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
			log.debug("Closed socket of client " + id);
		} catch (IOException e) {
			log.warning("Error occurred while closing socket of client  " + id);
			log.logStackTrace(e);
		}

		log.info("Client " + id + " disconnected");
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
