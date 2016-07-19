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

	Database database;

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
											// case "getCitizenName":
											// send(Commands.getCitizenName(id,
											// command, log, database));
											// break;

											case "getCitizenName":
												send(database.getCitizenName(command[1]), command);
												break;
											case "getCitizenPic":
												send(database.getCitizenPic(command[1]), command);
												break;
											case "getCitizenClass":
												send(database.getCitizenClass(command[1]), command);
												break;
											case "getCitizenCheckinTimes":
												send(database.getCitizenCheckinTimes(command[1]), command);
												break;
											case "getCitizenCheckoutTimes":
												send(database.getCitizenCheckoutTimes(command[1]), command);
												break;
											case "getCitizenInformation":
//												send(database.getCitizenInformation(command[1]), command);
												break;
											case "getTodaysDate":
												writer.writeBytes("" + System.currentTimeMillis());
												break;
											case "getCitizenState":
												if (database.getCitizenCheckinTimes(command[1]).length() > database
														.getCitizenCheckoutTimes(command[1]).length()) {
													writer.writeBytes("inside");
												} else {
													writer.writeBytes("outside");
												}
												break;
											case "setCitizenCheckinTimes":
												database.setCitizenCheckinTimes(command[1],
														database.getCitizenCheckinTimes(
																command[1] + System.currentTimeMillis() + ";"));
												break;
											case "setCitizenCheckoutTimes":
												database.setCitizenCheckoutTimes(command[1],
														database.getCitizenCheckoutTimes(
																command[1] + System.currentTimeMillis() + ";"));
												break;
											case "getCitizenExchangeVolume":
												writer.writeBytes(database.getCitizenExchangeVolume(command[1]));
												break;
//											case "getCitizenHasReceivedBasicSecurity":
////												if (database.getCitizenHasReceivedBasicSecurity(command[1])
//														.equals("true")) {
//													writer.writeBytes("true");
//												} else {
//													writer.writeBytes("false");
//												}
//												break;
											case "reciveBasicSecurity":
												// TODO Überweise Grundsicherung
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

	private void send(String s, String[] c) {

		if (s == null)
			log.warning("SQL Exception. Command was " + c[0] + " " + c[1]);
		if (s.equals(""))
			log.warning("Empty cell detected. Command was " + c[0] + " " + c[1]);
		try {
			writer.writeBytes(s);
		} catch (IOException e) {
			log.error("IOException while sending reply to client");
			log.logStackTrace(e);
		}
	}

	public ClientThread(SSLSocket socket, int id, SimpleLog log, Settings settings, Settings passwords,
			Settings permissions, Database database) {

		this.socket = socket;
		this.id = id;
		this.log = log;
		this.passwords = passwords;
		this.permissions = permissions;
		this.settings = settings;
		this.database = database;
	}
}
