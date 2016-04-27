/**
 * 
 */
package ClientLibrary;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.stage.FileChooser;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * @author Maximilian
 *
 */
public class ConnectionDialog extends JFrame {

	private JPanel contentPane;
	private JTextField txtCertPath;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionDialog frame = new ConnectionDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
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
		txtCertPath.setText("C:/CDMS");
		txtCertPath.setBounds(89, 40, 209, 20);
		contentPane.add(txtCertPath);
		txtCertPath.setColumns(10);

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FileChooser fc = new FileChooser();

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

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "127.0.0.1" }));
		comboBox.setBounds(89, 9, 118, 20);
		contentPane.add(comboBox);
	}
}
