package clientLibrary;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
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
	final static String path = "res\\client\\";

	SSLSocket socket;
	DataOutputStream writer;
	BufferedReader reader;
	SimpleLog log;
	Settings settings;
	boolean connected = false;

	final int EVERYTHING_IS_AWESOME = 0;
	final int CONNECTION_TIMED_OUT = 1;
	final int AUTHENTICATION_FAILED = 2;
	final int WRONG_IP_PORT = 3;

	/**
	 * Shows a GUI to input necessary information to connect to a server and
	 * authenticate. Then it will connect and authenticate
	 * 
	 * @throws IOException
	 */
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
							.createSocket();
					socket.connect(
							new InetSocketAddress(settings.getSetting("ip"),
									Integer.parseInt(settings
											.getSetting("port"))), Integer
									.parseInt(settings.getSetting("timeout")));

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
				} catch (SocketTimeoutException e) {
					String[] buttons = { "Retry", "Close" };
					int response = JOptionPane
							.showOptionDialog(
									null,
									"Connection to server timed out\nRetry or ask developer for help",
									"SocketTimeoutException",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE, null, buttons,
									buttons[0]);
					if (response == 1) {
						log.info("Connection timed out: Program terminated by user");
						System.exit(0);
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

	/**
	 * Connect to a server and authenticate.
	 * 
	 * @param ip
	 *            The IP to connect to
	 * @param port
	 *            The port the server runs on
	 * @param user
	 *            The user used to log in to
	 * @param passwd
	 *            The password for the user
	 * @param trustStore
	 *            The Path of the trustStore
	 * @param trustStorePasswd
	 *            The password of the trustStore
	 * @return
	 * @throws IOException
	 */
	public int connect(String ip, int port, String user, String passwd,
			String trustStore, String trustStorePasswd) throws IOException {

		settings.setSetting("ip", ip);
		settings.setSetting("port", String.valueOf(port));
		settings.setSetting("password", passwd);
		System.setProperty("javax.net.ssl.trustStore", trustStore);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePasswd);
		try {
			socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket();
			socket.connect(new InetSocketAddress(settings.getSetting("ip"),
					Integer.parseInt(settings.getSetting("port"))), Integer
					.parseInt(settings.getSetting("timeout")));

			writer = new DataOutputStream(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			if (authenticate()) {
				log.info("Succesfully connected to "
						+ settings.getSetting("ip") + ":"
						+ settings.getSetting("port"));
				connected = true;

				return (EVERYTHING_IS_AWESOME);
			} else {
				log.error("Authenticatin failed");
				return (AUTHENTICATION_FAILED);
			}
		} catch (SocketTimeoutException e) {
			log.warning("Connection timed out");
			return (CONNECTION_TIMED_OUT);
		} catch (ConnectException e) {
			log.warning("Connection not possible, wrong ip or port");
			return (WRONG_IP_PORT);
		}

	}

	private boolean authenticate() {

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

	/**
	 * Read in one line from the connection
	 * 
	 * @return The read line
	 */
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			log.error("Reading failed");
			log.logStackTrace(e);
		}
		return null;
	}

	/**
	 * Writes one line of text to the server
	 * 
	 * @param text
	 *            The text to be sent
	 * @return success
	 */
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

	/**
	 * The unsafe option to use the Connection. The is no settings file and you
	 * can't use the GUI. You have to specify all parameters by yourself or use
	 * the hardcoded values.
	 * 
	 * You might wonder why this is called the "unsafe method" even though there
	 * is no other "safe" way of connecting. Well, there was a safe way, but it
	 * has been removed, because a fellow developer of mine was simply to stupid
	 * to use it. One of the main reasons he never manages to use my code
	 * correctly is, that he never reads my Javadoc-comments. He will certainly
	 * never read this xD
	 * 
	 * @param log
	 *            The log to log to
	 */
	public Connection(SimpleLog log) {
		this.log = log;
	}

	/**
	 * 
	 * @return The Settings object
	 */
	public Settings getSettings() {
		return settings;
	}

	public static void main(String[] args) throws IOException {

		SimpleLog log = new SimpleLog(new File(path + "log.txt"), true, true);
		Properties props = new Properties();
		props.setProperty("port", "3746");
		props.setProperty("ip", "127.0.0.1");
		props.setProperty("truststore", "res/client/client.truststore");
		props.setProperty("truststorePassword", "123456");
		props.setProperty("timeout", "1000");

		Connection f = new Connection(log);
		f.connect();
		f.writeLine("reload all");
		System.out.println(f.readLine());
	}

}
