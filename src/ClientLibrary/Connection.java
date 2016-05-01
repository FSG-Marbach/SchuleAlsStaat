package clientLibrary;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

import essentials.Essentials;
import essentials.Settings;
import essentials.SimpleLog;

public class Connection {

	int port = 443;
	String ip = "127.0.0.1";
	final String path = "res\\client\\";
	SSLSocket socket;
	DataOutputStream writer;
	BufferedReader reader;
	SimpleLog log;
	Settings settings;

	public void connect() throws IOException {

		ConnectionDialog dialog = new ConnectionDialog();
		String[] ips = new String[0];
		try {
			ips = Essentials.searchIPs();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"IOException while retrieving IPs. Enter IP manually",
					"Error", JOptionPane.ERROR_MESSAGE);
			// log.logStackTrace(e1);
		}
		if (dialog.showConnectionDialog(
				Integer.parseInt(settings.getSetting("port")), ips) == ConnectionDialog.BUTTON_CONNECT) {

			ip = dialog.getIP();
			port = Integer.parseInt(dialog.getPort());

			System.setProperty("javax.net.ssl.trustStore",
					settings.getSetting("truststore"));
			System.setProperty("javax.net.ssl.trustStorePassword",
					settings.getSetting("truststorePassword"));

			socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(ip,
					port);

			writer = new DataOutputStream(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

		}

	}

	public boolean authenticate() {

		writer.writeBytes(settings.getSetting("user"));
		writer.writeBytes(settings.getSetting("password"));

	}

	public Connection() {
		log = new SimpleLog(new File(path + "log.txt"), true, true);

		Properties props = new Properties();
		props.setProperty("port", "3746");
		props.setProperty("ip", "127.0.0.1");
		props.setProperty("truststore", "res/client/client.truststore");
		props.setProperty("truststorePassword", "123456");

		settings = new Settings(new File(path + "settings.properties"), props,
				false, log);

	}

	public static void main(String[] args) throws IOException {
		Connection f = new Connection();
		f.connect();
	}

}
