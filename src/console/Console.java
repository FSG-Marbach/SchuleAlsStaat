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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

	Connection connection;
	JFrame frame;
	GridBagLayout layout = new GridBagLayout();
	JPanel panel, buttonBackground;
	JTextArea textArea;
	JTextField textField;
	Settings settings;

	public Console() {

		settings = new Settings(new File("res\\client\\settings.properties"), null, false, new SimpleLog());
		connection = new Connection(new SimpleLog(), settings);
		try {
			connection.connect();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't connect to server", "Error occurred",
					JOptionPane.ERROR_MESSAGE);
		}

		frame = new JFrame("Citizen Data Management System Console");
		frame.setLayout(layout);

		panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setLayout(layout);
		Essentials.addComponent(frame, layout, panel, 0, 0, 1, 1, 1, 1, new Insets(0, 0, 0, 0));

		textArea = new JTextArea(30, 120);
		textArea.setBackground(Color.black);
		textArea.setForeground(Color.white);
		textArea.setCaretColor(Color.WHITE);
		textArea.setFont(new Font("Monospaced", 0, 12));
		textArea.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		Essentials.addComponent(panel, layout, scrollPane, 0, 0, 1, 1, 1, 1, new Insets(0, 0, 0, 0));

		textField = new JTextField();
		textField.setBackground(Color.black);
		textField.setForeground(Color.white);
		textField.setCaretColor(Color.WHITE);
		textField.setFont(new Font("Monospaced", 0, 12));
		textField.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 5, Color.black));
		textField.setPreferredSize(new Dimension(0, 30));
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {

					String input = textField.getText();
					textField.setText("");
					textArea.append(input);
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
		Essentials.addComponent(panel, layout, textField, 0, 1, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		while (true) {

			String output = connection.readLine();
			textArea.append("\n" + output);
		}
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error while setting LookAndFeel! Default Java LookAndFeel will be used...");
		}

		new Console();
	}
}