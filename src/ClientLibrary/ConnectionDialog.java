/**
 * 
 */
package clientLibrary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 * @author Maximilian
 *
 */
public class ConnectionDialog {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JComboBox<String> comboBox;
	JFrame frame;
	boolean keepAlive;
	int buttonState;
	public final static int BUTTON_CANCEL = 1;
	public final static int BUTTON_CONNECT = 0;

	public ConnectionDialog() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int showConnectionDialog(int port, String[] ips) {
		keepAlive = true;

		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("Verbindung");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 314, 133);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonState = BUTTON_CANCEL;
				keepAlive = false;

			}
		});
		btnCancel.setBounds(207, 71, 89, 23);
		contentPane.add(btnCancel);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonState = BUTTON_CONNECT;
				keepAlive = false;

			}
		});
		btnConnect.setBounds(108, 71, 89, 23);
		contentPane.add(btnConnect);

		JLabel lblPassword = new JLabel("Passwort:");
		lblPassword.setBounds(10, 43, 69, 14);
		contentPane.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setText("1234");
		passwordField.setBounds(89, 40, 209, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);

		JLabel lblIpadresse = new JLabel("IP-Adresse:");
		lblIpadresse.setBounds(10, 12, 69, 14);
		contentPane.add(lblIpadresse);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(217, 12, 32, 14);
		contentPane.add(lblPort);

		textField_1 = new JTextField();
		textField_1.setText(String.valueOf(port));
		textField_1.setBounds(246, 9, 52, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		comboBox = new JComboBox<String>();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel<String>(ips));
		comboBox.setBounds(89, 9, 118, 20);
		contentPane.add(comboBox);
		frame.repaint();
		comboBox.repaint();
		comboBox.setVisible(true);
		comboBox.setSelectedIndex(0);
		while (keepAlive)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		frame.dispose();
		return buttonState;
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	public void setPassword(String certPath) {
		passwordField.setText(certPath);

	}

	public String getIP() {
		return (String) comboBox.getSelectedItem();
	}

	public void setIP(String ip) {
		comboBox.setSelectedItem(ip);
	}

	public String getPort() {
		return (String) textField_1.getText();
	}

	public void setPort(String ip) {
		textField_1.setText(ip);
	}
}
