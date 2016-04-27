package test;

import java.io.OutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestClient {

	public TestClient() throws Exception {

		System.setProperty("javax.net.ssl.trustStore", "client.truststore");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");

		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 3746);

//		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
//		
//		writer.writeBytes("TestClient\n");
//		writer.flush();
//		writer.writeBytes("123456\n");
//		writer.flush();
//		writer.writeBytes("TestCommand\n");
//		writer.flush();
		
		OutputStream sslOS = socket.getOutputStream();
		sslOS.write("TestClient\n".getBytes());
		sslOS.write("123456\n".getBytes());
		sslOS.write("TestCommand\n".getBytes());
	}

	public static void main(String[] args) throws Exception {
		new TestClient();
	}
}