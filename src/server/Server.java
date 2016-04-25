package server;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import org.apache.commons.ssl.KeyMaterial;
import org.apache.commons.ssl.SSLServer;
import org.apache.commons.ssl.TrustMaterial;

import essentials.Settings;
import essentials.SimpleLog;

/**
 * @author Felix Beutter
 */

public class Server {

	static SimpleLog log;
	Settings settings;
	SSLServer server;
	SSLServerSocket serverSocket;
	
	int PORT = 443;
	final String logFilePath = "C://CDMS//log.txt";

	int id = 0;
	
	public Server() {

		// Create log file
		log = new SimpleLog(new File(logFilePath), true, true);

		// Initialize settings
		Properties defaultValues = new Properties();
		defaultValues.setProperty("port", String.valueOf(PORT));
		defaultValues.setProperty("certificate", "C://CDMS//server.crt");
		defaultValues.setProperty("privateKey", "C://CDMS//server.key");
		defaultValues.setProperty("password", "1234");
		settings = new Settings(new File("C://CDMS//settings.xml"), defaultValues, log);

		// Setting variables
		try {
			PORT = Integer.parseInt(settings.getSetting("PORT"));
		} catch (NumberFormatException e) {
			settings.setSetting("port", String.valueOf(PORT));
			log.error("NumberFormatException while setting port! Default port (" + PORT + ") will be used...");
		}
		
		// Configuring SSLServer
		try {
			configureSSLServer();
		} catch (GeneralSecurityException | IOException e) {
			log.fatal("Fatal error occurred while configuring server! Shuting down...");
			log.logStackTrace(e);
			System.exit(1);
		}
		
		// Creating SSLServer
		try {
			serverSocket = (SSLServerSocket) server.createServerSocket(PORT);
		} catch (IOException e) {
			log.fatal("Fatal error occurred while creating server! Shuting down...");
			log.logStackTrace(e);
			System.exit(1);
		}

		log.info("Successfully created server on port " + PORT);
		log.info("Waiting for clients...");

		//Accepting clients
		while (true) {

			SSLSocket client = null;
			try {
				client = (SSLSocket) serverSocket.accept();
			} catch (IOException e) {
				log.error("Error occurred while accepting a client (id: " + id + ")! Closing socket...");
				log.logStackTrace(e);
			}
			if(client != null) new ClientThread(client, id).start();
			id++;
		}
	}
	
	void configureSSLServer() throws GeneralSecurityException, IOException {
		
		server = new SSLServer();
		
		String certificate = settings.getSetting("certificate");
		String privateKey = settings.getSetting("privateKey");
		char[] password = settings.getSetting("password").toCharArray();
		
		KeyMaterial keyMaterial = new KeyMaterial(certificate, privateKey, password);
		
		server.setKeyMaterial(keyMaterial);
		server.addTrustMaterial(TrustMaterial.TRUST_ALL);
	}

	static SimpleLog getLog() {
		return log;
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
