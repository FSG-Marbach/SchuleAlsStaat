package clientLibrary;

import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import essentials.SimpleLog;

public class Connection {

	final int PORT = 443;

	void connect() throws IOException {
		SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
				.createSocket("localhost", PORT);
		SimpleLog log = new SimpleLog();
		ConnectionDialog frame = new ConnectionDialog();
		frame.setVisible(true);

	}

	public Connection() {

	}
}
