package clientLibrary;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

import essentials.Essentials;
import essentials.Settings;
import essentials.SimpleLog;

public class Connection {

	final int PORT = 443;
	final String path = "res\\client";

	public void connect() throws IOException {

		SimpleLog log = new SimpleLog(new File(path + "log.txt"), true, true);

		Properties props = new Properties();
		props.setProperty("port", "3746");
		props.setProperty("ip", "127.0.0.1");

		Settings settings = new Settings(
				new File(path + "settings.properties"), props, false, log);

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
		dialog.showConnectionDialog(
				Integer.parseInt(settings.getSetting("port")), ips);

		SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
				.createSocket("localhost", PORT);

	}

	public Connection() {

	}

	public static void main(String[] args) throws IOException {
		Connection f = new Connection();
		f.connect();
	}

}
