package console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import clientLibrary.Connection;
import essentials.Essentials;
import essentials.Settings;
import essentials.SimpleLog;

public class Console {

	final String PATH = "res\\console\\";
	final int PORT = 3746;

	String username = "", ip = "";
	int port = 0;

	public Console() throws IOException {

		Properties defaultValues = new Properties();
		defaultValues.setProperty("port", String.valueOf(PORT));
		defaultValues.setProperty("truststore", "client.truststore");
		defaultValues.setProperty("truststorePassword", "123456");
		Settings settings = new Settings(
				new File(PATH + "settings.properties"), defaultValues, false,
				new SimpleLog());

		final Connection connection = new Connection(new SimpleLog(), settings);
		connection.connect();

		username = connection.getUsername();
		ip = connection.getIp();
		port = connection.getPort();

		GridBagLayout layout = new GridBagLayout();

		JFrame frame = new JFrame("Citizen Data Management System Console");
		frame.setLayout(layout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setLayout(layout);
		Essentials.addComponent(frame, layout, panel, 0, 0, 1, 1, 1, 1,
				new Insets(0, 0, 0, 0));

		String loginMessage = "Successful authentication of user '" + username
				+ "' on CDMS server running on '" + ip + "/" + port;
		String s = "";
		for (int i = 0; i <= loginMessage.length(); i++)
			s = s + "=";
		loginMessage = s + "\n" + loginMessage + "'\n" + s + "\n";

		final JTextArea textArea = new JTextArea(loginMessage, 30, 100);
		textArea.setBackground(Color.black);
		textArea.setForeground(Color.white);
		textArea.setCaretColor(Color.WHITE);
		textArea.setFont(new Font("Consolas", 0, 14));
		textArea.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		Essentials.addComponent(panel, layout, scrollPane, 0, 0, 2, 1, 1, 1,
				new Insets(0, 0, 0, 0));

		JTextField label = new JTextField(username + ">");
		label.setEditable(false);
		label.setFont(new Font("Consolas", 0, 14));
		label.setBackground(Color.white);
		label.setForeground(Color.black);
		label.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 0,
				Color.white));
		Essentials.addComponent(panel, layout, label, 0, 1, 1, 1, 0, 0,
				new Insets(0, 0, 0, 0));

		final JTextField textField = new JTextField();
		textField.setBackground(Color.white);
		textField.setForeground(Color.black);
		textField.setCaretColor(Color.black);
		textField.setFont(new Font("Consolas", 0, 14));
		textField.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 10,
				Color.white));
		textField.setPreferredSize(new Dimension(0, 30));
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					String input = textField.getText();
					textField.setText("");
					textArea.append(username + "> " + input + "\n");
					connection.writeLine(input);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		Essentials.addComponent(panel, layout, textField, 1, 1, 1, 1, 1, 0,
				new Insets(0, 0, 0, 0));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		while (true) {
			String output = connection.readLine();
			textArea.append(output + "\n");
		}
	}

	public static void main(String[] args) throws IOException {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out
					.println("Error while setting LookAndFeel! Default Java LookAndFeel will be used...");
		}

		new Console();
	}
}