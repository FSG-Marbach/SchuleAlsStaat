package accessControlClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import essentials.Essentials;

/**
 * 
 * @author Johannes Groß
 *
 */

public class Gui implements KeyListener, ActionListener {

	private JFrame frame;
	private JPanel panel, pnl_header, pnl_informationLeftSide, pnl_informationRightSide, pnl_input,
			pnl_serverinformation, pnl_imagePlace;
	private JLabel lbl_header, lbl_name, lbl_nameTitle, lbl_class, lbl_classTitle, lbl_attendance, lbl_attendanceTitle,
			lbl_informationTitle, lbl_image, lbl_imageTitle;
	private Color clr_background, clr_backgroundHeader;
	private Font fnt_header, fnt_title, fnt_normal;
	private GridBagLayout gridbaglayout;
	private JButton btn_login;
	private JTextArea jta_information;
	private JScrollPane scp_information;
	private static JTextField txf_userid;
	private int width, height;

	public void restoreDefaults() {
		lbl_name.setText("");
		lbl_class.setText("");
		lbl_attendance.setText("");
		jta_information.setText("");
		txf_userid.setText("");
	}

	/**
	 * The 'fillGaps' method allows the input data to appear on the user
	 * interface
	 * 
	 * @param name
	 *            Student's name to be displayed
	 * @param classLevel
	 *            Student's classlevel to be displayed
	 * @param attendance
	 *            Existing attendance of the student to be displayed
	 * @param state
	 *            true if the student is inside the state, false if he/she is
	 *            outside
	 * @param information
	 *            Important messages to the student at the accesspoint
	 * @return boolean if false, exception occurred
	 *
	 */

	public boolean fillGaps(String name, String classLevel, Integer attendance, Boolean state, String information) {

		try {
			lbl_name.setText(name);
			lbl_class.setText(classLevel);
			lbl_attendance.setText(attendance.toString() + " h");
			jta_information.setText(information);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * The 'fillInformations' method allows the input data to appear on the user
	 * interface
	 * 
	 * @param information
	 *            Important messages to the student at the accesspoint
	 * @return boolean if false, exception occurred
	 *
	 */

	public boolean fillInformations(String information) {
		try {
			jta_information.setText(information);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean setStudentId(String id) {
		try {

			id = id.replace("<", "");
			id = id.replace(">", "");
			txf_userid.setText(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * The 'initialize' method creates and initialize every Object and add them
	 * in the gridbaglayout on the main panel on the frame
	 * 
	 * @return boolean returns false if an exception occurred
	 **/

	public boolean initialize() {

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

			width = 1366;
			height = 760;

			fnt_title = new Font("Arial Rounded MT Bold", Font.BOLD, 22);
			fnt_normal = new Font("Arial Rounded MT Bold", 0, 18);

			clr_background = new Color(240, 240, 240);
			clr_backgroundHeader = new Color(180, 180, 180);

			gridbaglayout = new GridBagLayout();

			frame = new JFrame();
			frame.setSize(width, height);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setUndecorated(true);

			lbl_name = new JLabel("");
			lbl_name.setFont(fnt_normal);
			lbl_name.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_nameTitle = new JLabel("Name:");
			lbl_nameTitle.setFont(fnt_title);
			lbl_nameTitle.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_class = new JLabel("");
			lbl_class.setFont(fnt_normal);
			lbl_class.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_classTitle = new JLabel("Klasse:");
			lbl_classTitle.setFont(fnt_title);
			lbl_classTitle.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_attendance = new JLabel("");
			lbl_attendance.setFont(fnt_normal);
			lbl_attendance.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_attendanceTitle = new JLabel("Bissherige Anwesenheitszeit:");
			lbl_attendanceTitle.setFont(fnt_title);
			lbl_attendanceTitle.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_informationTitle = new JLabel("Wichtige Informationen:");
			lbl_informationTitle.setFont(fnt_title);
			lbl_informationTitle.setHorizontalAlignment(SwingConstants.LEFT);

			jta_information = new JTextArea("Keine Informationen verfügbar...");
			jta_information.setFont(fnt_normal);
			jta_information.setLineWrap(true);
			jta_information.setWrapStyleWord(true);
			jta_information.setEditable(false);
			jta_information.setBackground(clr_background);
			jta_information.setSelectedTextColor(Color.black);
			jta_information.setSelectionColor(clr_background);

			scp_information = new JScrollPane(jta_information);
			scp_information.setBorder(BorderFactory.createEmptyBorder());

			lbl_imageTitle = new JLabel("Foto:");
			lbl_imageTitle.setFont(fnt_title);
			lbl_imageTitle.setHorizontalAlignment(SwingConstants.LEFT);

			lbl_image = new JLabel();
			lbl_image.setHorizontalAlignment(SwingConstants.CENTER);

			btn_login = new JButton("Aus-/Einloggen");
			btn_login.setFont(fnt_normal);
			btn_login.addActionListener(this);
			btn_login.setContentAreaFilled(true);

			txf_userid = new JTextField();
			txf_userid.setFont(fnt_normal);
			txf_userid.addKeyListener(this);
			txf_userid.requestFocus(true);

			panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(0, 0, width, height);
			panel.setBackground(clr_background);

			pnl_header = new JPanel();
			pnl_header.setLayout(new BorderLayout());
			pnl_header.setBackground(clr_backgroundHeader);

			pnl_informationLeftSide = new JPanel();
			pnl_informationLeftSide.setLayout(gridbaglayout);
			pnl_informationLeftSide.setBackground(clr_background);

			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_nameTitle, 0, 0, 2, 1, 1, 1,
					new Insets(5, 5, 5, 5), GridBagConstraints.WEST);
			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_name, 0, 1, 2, 1, 1, 1,
					new Insets(5, 5, 5, 5), GridBagConstraints.WEST);
			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_classTitle, 0, 2, 2, 1, 1, 1,
					new Insets(5, 5, 5, 5), GridBagConstraints.WEST);
			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_class, 0, 3, 2, 1, 1, 1,
					new Insets(5, 5, 5, 5), GridBagConstraints.WEST);
			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_attendanceTitle, 0, 4, 2, 1, 1, 1,
					new Insets(5, 5, 5, 5), GridBagConstraints.WEST);
			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_attendance, 0, 5, 2, 1, 1, 1,
					new Insets(5, 5, 5, 5), GridBagConstraints.WEST);

			pnl_imagePlace = new JPanel();
			pnl_imagePlace.setLayout(new BorderLayout());
			pnl_imagePlace.setBackground(clr_background);

			ImageIcon image = new ImageIcon("res/client/emptyprofilpic.png");
			image.setImage(image.getImage().getScaledInstance(250, 350, Image.SCALE_DEFAULT));
			lbl_image.setIcon(image);

			pnl_imagePlace.add(lbl_imageTitle, BorderLayout.NORTH);
			pnl_imagePlace.add(lbl_image, BorderLayout.CENTER);

			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, pnl_imagePlace, 2, 0, 1, 5, 0, 1,
					new Insets(5, 5, 5, 5));

			pnl_informationRightSide = new JPanel();
			pnl_informationRightSide.setLayout(new BorderLayout());
			pnl_informationRightSide.setBackground(clr_background);

			pnl_informationRightSide.add(lbl_informationTitle, BorderLayout.NORTH);
			pnl_informationRightSide.add(scp_information, BorderLayout.CENTER);

			pnl_input = new JPanel();
			pnl_input.setLayout(gridbaglayout);
			pnl_input.setBackground(clr_background);

			Essentials.addComponent(pnl_input, gridbaglayout, txf_userid, 0, 0, 1, 1, 1, 1, new Insets(15, 15, 15, 15));
			Essentials.addComponent(pnl_input, gridbaglayout, btn_login, 1, 0, 1, 1, 1, 1, new Insets(15, 15, 15, 15));

			pnl_serverinformation = new JPanel();
			pnl_serverinformation.setLayout(gridbaglayout);
			pnl_serverinformation.setBackground(Color.green);

			pnl_header.setBounds(0, 0, width, height / 7);
			pnl_informationLeftSide.setBounds(0, height / 7, width / 3 * 2, height / 7 * 4);
			pnl_informationRightSide.setBounds(width / 3 * 2, height / 7, width / 3, height / 7 * 4);
			pnl_input.setBounds(0, height / 7 * 5, width, height / 7);
			pnl_serverinformation.setBounds(0, height / 7 * 6, width, height / 7);

			fnt_header = new Font("Arial Rounded MT Bold", Font.BOLD, (int) pnl_header.getSize().getHeight() / 3);

			lbl_header = new JLabel("Citizen Data Managment System - Zugangskontrollclient");
			lbl_header.setFont(fnt_header);
			lbl_header.setForeground(Color.black);
			lbl_header.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_header.setVerticalAlignment(SwingConstants.CENTER);

			pnl_header.add(lbl_header, BorderLayout.CENTER);

			pnl_header.setBorder(BorderFactory.createLineBorder(clr_background));
			pnl_informationLeftSide.setBorder(BorderFactory.createLineBorder(clr_background));
			pnl_informationRightSide.setBorder(BorderFactory.createLineBorder(clr_background));
			pnl_input.setBorder(BorderFactory.createLineBorder(clr_background));
			pnl_serverinformation.setBorder(BorderFactory.createLineBorder(clr_background));

			panel.add(pnl_header);
			panel.add(pnl_informationLeftSide);
			panel.add(pnl_informationRightSide);
			panel.add(pnl_input);
			panel.add(pnl_serverinformation);

			frame.add(panel);
			frame.setVisible(true);
			txf_userid.requestFocus(true);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Gui() {

	}

	public void keyPressed(KeyEvent EVT) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			e.consume();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_login) {
			if (txf_userid.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "The Student ID field is empty.", "Error while logging in!",
						JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("Student ID: " + txf_userid.getText());
				restoreDefaults();
			}

			txf_userid.requestFocus();

		}

	}

}
