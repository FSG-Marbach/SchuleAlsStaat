package test;

import java.io.OutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestClient {

	final static String pathToStores = "C:\\CDMS";
	final static String truststoreFile = "client.truststore";
	final static String passwd = "123456";

	final static String theServerName = "localhost";
	final static int theServerPort = 5678;

	void doClientSide() throws Exception {

		SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket sslSocket = (SSLSocket) sslsf.createSocket(theServerName, theServerPort);

		OutputStream sslOS = sslSocket.getOutputStream();
		sslOS.write("Hello SSL Server\n".getBytes());
		sslOS.flush();
		sslSocket.close();
	}

	public static void main(String[] args) throws Exception {

		String trustFilename = pathToStores + "\\" + truststoreFile;

		System.setProperty("javax.net.ssl.trustStore", trustFilename);
		System.setProperty("javax.net.ssl.trustStorePassword", passwd);

		new TestClient().doClientSide();
	}
}