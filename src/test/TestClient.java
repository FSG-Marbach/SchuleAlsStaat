package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestClient {

	public TestClient() throws Exception {

		System.setProperty("javax.net.ssl.trustStore", "client.truststore");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");

		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 3746);

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

		writer.writeBytes("root\n");
		writer.writeBytes("123456\n");
		writer.writeBytes("disconnect\n");

		socket.close();
	}

	public static void main(String[] args) throws Exception {
		new TestClient();
	}
}