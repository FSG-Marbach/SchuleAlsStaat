package clientLibrary;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

import essentials.Essentials;
import essentials.Settings;
import essentials.SimpleLog;

public class Connection {

	int port = 443;
	String ip;
	final String path = "res\\client\\";
	SSLSocket socket;
	DataOutputStream writer;
	BufferedReader reader;
	SimpleLog log;
	Settings settings;
	boolean connected = false;

	public void connect() throws IOException {

		ConnectionDialog dialog = new ConnectionDialog();
		String[] ips = new String[0];
		try {
			ips = Essentials.searchIPs();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"IOException while retrieving IPs. Enter IP manually",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		boolean success = false;
		dialog.showConnectionDialog(
				Integer.parseInt(settings.getSetting("port")), ips);
		dialog.setIP(settings.getSetting("ip"));
		dialog.setPassword(settings.getSetting("password"));
		while (!success) {

			if (dialog.getButtonState() == ConnectionDialog.BUTTON_CONNECT) {

				settings.setSetting("ip", dialog.getIP());
				settings.setSetting("port", dialog.getPort());
				settings.setSetting("password", dialog.getPassword());
				System.setProperty("javax.net.ssl.trustStore",
						settings.getSetting("truststore"));
				System.setProperty("javax.net.ssl.trustStorePassword",
						settings.getSetting("truststorePassword"));
				try {
					socket = (SSLSocket) SSLSocketFactory.getDefault()
							.createSocket(
									settings.getSetting("ip"),
									Integer.parseInt(settings
											.getSetting("port")));

					writer = new DataOutputStream(socket.getOutputStream());
					reader = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					if (!authenticate()) {
						JOptionPane
								.showMessageDialog(
										null,
										"Connection not possible\nIncorrect username and password",
										"Error", JOptionPane.OK_OPTION);
						log.warning("Connection not possible, wrpng username or password");
					} else {
						success = true;
						dialog.dispose();
					}

				} catch (ConnectException e) {
					JOptionPane.showMessageDialog(null,
							"Connection not possible\nWrong IP or port",
							"Error", JOptionPane.OK_OPTION);
					log.warning("Connection not possible, wrong ip or port");
				}

			} else {
				log.info("Program terminated by user");
				dialog.dispose();
				System.exit(0);
			}
		}
		connected = true;

	}

	public boolean authenticate() {

		try {
			writer.writeBytes(settings.getSetting("user") + "\n");
			writer.writeBytes(settings.getSetting("password") + "\n");
			String result = reader.readLine();
			if (result.startsWith("Successful authentication")) {
				log.info("Authentication successful");
				return true;
			}

			else {
				log.error("Authentication failed. Return code was: " + result);
				return false;
			}

		} catch (IOException e) {
			log.logStackTrace(e);
			return false;
		}

	}

	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			log.error("Reading failed");
			log.logStackTrace(e);
		}
		return null;
	}

	public boolean writeLine(String text) {
		try {
			writer.writeBytes(text + "\n");
		} catch (IOException e) {
			log.error("Writing failed");
			log.logStackTrace(e);
			return false;
		}
		return true;
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
		String[] neccessaryKeys = { "truststore", "truststorePassword", "user" };
		if (!settings.containsKeys(neccessaryKeys)) {
			log.fatal("Neccessary keys are missing in settings file. Terminating");
			JOptionPane
					.showMessageDialog(
							null,
							"Neccessary keys are missing in the settings file. Program is being terminated.\nPlease contact system administrator",
							"Settings file corrupted", JOptionPane.OK_OPTION);
			System.exit(1);

		}

	}

	public static void main(String[] args) throws IOException {
		Connection f = new Connection();
		f.connect();
		f.writeLine("reload all");
		System.out.println(f.readLine());
	}

}
