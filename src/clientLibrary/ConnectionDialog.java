package clientLibrary;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.NumberFormatter;

import essentials.Essentials;
import essentials.Settings;
import essentials.SimpleLog;

public class ConnectionDialog {

	SimpleLog log;
	Settings settings;

	static JComboBox<String> cbxIp;
	static JFormattedTextField txfPort;
	static JTextField txfUsername;
	static JPasswordField txfPassword;
	static JButton btnAbort, btnConnect;

	static String ip, username, password;
	static int port;

	boolean connect = false;

	public ConnectionDialog(SimpleLog log, Settings settings) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(
					"Error while setting LookAndFeel in ConnectionDialog gui! Default Java LookAndFeel will be used...");
		}

		this.log = log;
		this.settings = settings;
	}

	public void showConnectionDialog() {

		GridBagLayout layout = new GridBagLayout();

		JFrame frame = new JFrame("Verbindung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(layout);

		JLabel lblIp = new JLabel("IP-Adresse:");
		Essentials.addComponent(frame, layout, lblIp, 0, 0, 1, 1, 0, 0, new Insets(5, 5, 5, 5));

		// JTextField txfIp = new JTextField(settings.getSetting("ip"));
		// Essentials.addComponent(frame, layout, txfIp, 1, 0, 1, 1, 1, 0, new
		// Insets(5, 0, 5, 5));

		String[] ips = new String[0];
		try {
			ips = Essentials.searchIPs();
			String local = String.valueOf(InetAddress.getLocalHost().getHostAddress());
			String[] ips2 = new String[ips.length + 1];
			System.arraycopy(ips, 0, ips2, 0, ips.length);
			ips2[ips.length] = local;
			ips = ips2;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "IOException while retrieving IPs. Enter IP manually", "Error",
					JOptionPane.ERROR_MESSAGE);
			ips = new String[0];
		}

		cbxIp = new JComboBox<String>(ips);
		Essentials.addComponent(frame, layout, cbxIp, 1, 0, 1, 1, 1, 0, new Insets(5, 0, 5, 5));

		JLabel lblPort = new JLabel("Port:");
		Essentials.addComponent(frame, layout, lblPort, 2, 0, 1, 1, 0, 0, new Insets(5, 0, 5, 5));

		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setAllowsInvalid(false);
		txfPort = new JFormattedTextField(formatter);
		txfPort.setText(settings.getSetting("port"));
		txfPort.setPreferredSize(new Dimension(50, 20));
		Essentials.addComponent(frame, layout, txfPort, 3, 0, 1, 1, 0, 0, new Insets(5, 0, 5, 5));

		JLabel lblUser = new JLabel("Benutzer:");
		Essentials.addComponent(frame, layout, lblUser, 0, 1, 1, 1, 0, 0, new Insets(0, 5, 5, 5));

		JTextField txfUsername = new JTextField();
		txfUsername.setPreferredSize(new Dimension(260, 20));
		Essentials.addComponent(frame, layout, txfUsername, 1, 1, 3, 1, 1, 0, new Insets(0, 0, 5, 5));

		JLabel lblPassword = new JLabel("Passwort:");
		Essentials.addComponent(frame, layout, lblPassword, 0, 2, 1, 1, 0, 0, new Insets(0, 5, 10, 5));

		txfPassword = new JPasswordField();
		txfUsername.setPreferredSize(new Dimension(260, 20));
		Essentials.addComponent(frame, layout, txfPassword, 1, 2, 3, 1, 1, 0, new Insets(0, 0, 10, 5));

		JPanel pnlButton = new JPanel();
		pnlButton.setLayout(layout);
		Essentials.addComponent(frame, layout, pnlButton, 0, 3, 4, 1, 1, 0, new Insets(0, 0, 0, 0));

		JPanel emptyPanel = new JPanel();
		Essentials.addComponent(pnlButton, layout, emptyPanel, 0, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		btnAbort = new JButton("abbrechen");
		btnAbort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Essentials.addComponent(pnlButton, layout, btnAbort, 1, 0, 1, 1, 0, 0, new Insets(0, 5, 5, 5));

		btnConnect = new JButton("verbinden");
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ip = String.valueOf(cbxIp.getSelectedItem());
				port = Integer.valueOf(txfPort.getText());
				username = txfUsername.getText();
				password = new String(txfPassword.getPassword());

				connect = true;
			}
		});
		Essentials.addComponent(pnlButton, layout, btnConnect, 2, 0, 1, 1, 0, 0, new Insets(0, 0, 5, 5));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		while (!connect) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				log.error("InterruptedException occurred!");
			}
		}
		
		frame.dispose();
	}

	static String getIp() {
		return ip;
	}

	static int getPort() {
		return port;
	}

	static String getUsername() {
		return username;
	}

	static String getPassword() {
		return password;
	}
}
