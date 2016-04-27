/**
 * 
 */
package clientLibrary;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import essentials.Essentials;

/**
 * @author Maximilian
 *
 */
public class ConnectionDialog extends JFrame {

	private JPanel contentPane;
	private JTextField txtCertPath;
	private JTextField textField_1;

	public ConnectionDialog() {

		setTitle("Verbindung");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 344, 133);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(242, 71, 89, 23);
		contentPane.add(btnCancel);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConnect.setBounds(143, 71, 89, 23);
		contentPane.add(btnConnect);

		JLabel lblZertifikatpfad = new JLabel("Zertifikatpfad:");
		lblZertifikatpfad.setBounds(10, 43, 69, 14);
		contentPane.add(lblZertifikatpfad);

		txtCertPath = new JTextField();
		txtCertPath.setText("C:\\CDMS");
		txtCertPath.setBounds(89, 40, 209, 20);
		contentPane.add(txtCertPath);
		txtCertPath.setColumns(10);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Installationsverzeichnis...");
				fc.setCurrentDirectory(new File("C:\\"));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(null);
				txtCertPath.setText(fc.getSelectedFile().getPath());

			}
		});
		button.setBounds(308, 39, 23, 23);
		contentPane.add(button);

		JLabel lblIpadresse = new JLabel("IP-Adresse:");
		lblIpadresse.setBounds(10, 12, 69, 14);
		contentPane.add(lblIpadresse);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(217, 12, 32, 14);
		contentPane.add(lblPort);

		textField_1 = new JTextField();
		textField_1.setText("15678");
		textField_1.setBounds(246, 9, 52, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		String[] ips = { "127.0.0.1" };
		try {
			ips = Essentials.searchIPs();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this,
					"IOException while retrieving IPs. Enter IP manually",
					"Error", JOptionPane.ERROR_MESSAGE);
			// log.logStackTrace(e1);
		}

		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel(ips));
		comboBox.setBounds(89, 9, 118, 20);
		contentPane.add(comboBox);
	}
}
