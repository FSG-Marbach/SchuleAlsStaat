package clientLibrary;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import essentials.SimpleLog;

public class Connection {

	final int PORT = 443;
	final String path = "res\\client";

	void connect() throws IOException {

		SimpleLog log = new SimpleLog(new File(path + "log.txt"), true, true);

		// Properties props = new Properties();
		// props.setProperty("port", "3746");
		// props.setProperty("ip", "127.0.0.1");
		//
		// Settings settings = new Settings(
		// new File(path + "settings.properties"), false, log);

		// SimpleLog log = new SimpleLog();

		ConnectionDialog.showConnectionDialog();

		SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
				.createSocket("localhost", PORT);

	}

	public Connection() {
		try {
			connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Connection();
	}

}
