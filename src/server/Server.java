package server;

import java.io.File;
import java.io.IOException;

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
	Settings settings;
	SSLServerSocket serverSocket;

	final String logFilePath = "C:\\CDMS\\log.txt";
	final String home = "C:\\CDMS";
	final String keystore = "server.keystore";
	final String password = "123456";
	
	int port = 5678;
	int id = 0;

	public Server() {

		// Create log file
		log = new SimpleLog(new File(logFilePath), true, true);

		//TODO Initialize settings
		
//		Properties defaultValues = new Properties();
//		defaultValues.setProperty("port", String.valueOf(port));
//		defaultValues.setProperty("home", "C:\\CDMS");
//		defaultValues.setProperty("keystore", "server.keystore");
//		defaultValues.setProperty("password", "123456");
//		settings = new Settings(new File("C:\\CDMS\\settings.xml"), defaultValues, log);

		//TODO Setting variables (port)
		
//		try {
//			port = Integer.parseInt(settings.getSetting("port"));
//		} catch (NumberFormatException e) {
//			settings.setSetting("port", String.valueOf(port));
//			log.error("NumberFormatException while setting port! Default port (" + port + ") will be used...");
//		}

		// Configuring SSLServer
		configureSSLServer();

		// Creating SSLServer
		try {
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			serverSocket = (SSLServerSocket) factory.createServerSocket(port);
		} catch (IOException e) {
			log.fatal("Fatal error occurred while creating server! Shuting down...");
			log.logStackTrace(e);
			System.exit(1);
		}

		log.info("Successfully created server on port " + port);
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

	void configureSSLServer() {

		String keystorePath = home + "\\" + keystore;
		
		System.setProperty("javax.net.ssl.keyStore", keystorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", password);
	}

	static SimpleLog getLog() {
		return log;
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
