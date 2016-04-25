package server;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import essentials.Log;

/**
 * @author Felix Beutter
 */

public class Server {

	final int PORT = 443;
	final String logFilePath = "C://Users//Felix//Desktop//log.txt";

	static Log log;

	public Server() throws IOException {

		//Create log file
		log = new Log(new File(logFilePath), true, true);

		//TODO Configure SSLServer
		SSLServerSocket serverSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(PORT);

		log.info("Successfully created server on port " + PORT);
		log.info("Waiting for clients...");

		while(true) {
		
			SSLSocket client = (SSLSocket) serverSocket.accept();
			new ClientThread(client).start();
		}	
	}
	
	static Log getLog() {
		return log;
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
