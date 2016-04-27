package clientLibrary;

import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Connection {
	
	final int PORT = 443;
	
	void connect() throws IOException {
		SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
                .createSocket("localhost", PORT);
	}
	
	public Connection() {
		
	}
}
