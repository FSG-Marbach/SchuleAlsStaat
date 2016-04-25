package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import essentials.SimpleLog;

public class ClientThread extends Thread {

	SimpleLog log;

	SSLSocket socket;
	BufferedReader reader;
	DataOutputStream writer;

	int id;
	
	public void run() {

		// Configure log
		log = Server.getLog();

		try {
			initializeSession();
		} catch (IOException e) {
			log.error("Error occurred while initializing session!");
			log.logStackTrace(e);
		}
		
		//TODO Implement authentication + log message
	}

	void initializeSession() throws IOException {

		// Initialize reader and writer
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new DataOutputStream(socket.getOutputStream());
	}

	public ClientThread(SSLSocket s, int i) {
		socket = s;
		id = i;
	}
}