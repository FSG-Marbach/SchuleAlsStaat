package accessControl;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import essentials.SimpleLog;

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
	private JButton btn_login, btn_locknumber;
	private JTextArea jta_information;
	private JScrollPane scp_information;
	private static JTextField txf_userid;
	private int width, height;
	ImageIcon defaultpicture;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");

	public Gui() {

	}

	public void restoreDefaults() {
		lbl_name.setText("Johannes");
		lbl_class.setText("9E");
		lbl_attendance.setText("10h");
		jta_information.setText("Keine Informationen verfügbar...");
		txf_userid.setText("");
		txf_userid.setEditable(true);
		btn_login.setEnabled(false);
		lbl_image.setIcon(defaultpicture);
		btn_locknumber.setText("Schülerinformationen Abrufen");
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

	public boolean fillGaps(String name, String classLevel, String attendance, ImageIcon studentpicture,
			String information) {

		try {
			lbl_name.setText(name);
			lbl_class.setText(classLevel);
			lbl_attendance.setText(attendance);
			jta_information.setText(information);

			if (studentpicture != null && Main.allowstudentpictures == true) {
				studentpicture.setImage(studentpicture.getImage().getScaledInstance(250, 350, Image.SCALE_DEFAULT));
				lbl_image.setIcon(studentpicture);

			} else {
				defaultpicture.setImage(defaultpicture.getImage().getScaledInstance(250, 350, Image.SCALE_DEFAULT));
				lbl_image.setIcon(defaultpicture);

			}
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

			fnt_title = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
			fnt_normal = new Font("Arial Rounded MT Bold", 0, 24);

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

			lbl_attendanceTitle = new JLabel("Bisherige Anwesenheitszeit:");
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
			btn_login.setEnabled(false);

			btn_locknumber = new JButton("Schülerinformationen Abrufen");
			btn_locknumber.setFont(fnt_normal);
			btn_locknumber.addActionListener(this);
			btn_locknumber.setContentAreaFilled(true);

			txf_userid = new JTextField();
			txf_userid.setFont(fnt_title);
			txf_userid.addKeyListener(this);
			txf_userid.requestFocus(true);
			txf_userid.setMargin(new Insets(5, 10, 5, 5));

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

			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, lbl_nameTitle, 0, 0, 2, 1, 0, 0,
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

			pnl_informationLeftSide.setBorder(BorderFactory.createLineBorder(clr_background, 15));

			pnl_imagePlace = new JPanel();
			pnl_imagePlace.setLayout(new BorderLayout());
			pnl_imagePlace.setBackground(clr_background);

			defaultpicture = new ImageIcon(Main.path + "emptyprofilpic2.png");
			defaultpicture.setImage(defaultpicture.getImage().getScaledInstance(250, 350, Image.SCALE_DEFAULT));
			defaultpicture.setImage(defaultpicture.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH));
			lbl_image.setIcon(defaultpicture);

			pnl_imagePlace.add(lbl_imageTitle, BorderLayout.NORTH);
			pnl_imagePlace.add(lbl_image, BorderLayout.CENTER);

			Essentials.addComponent(pnl_informationLeftSide, gridbaglayout, pnl_imagePlace, 2, 0, 1, 6, 0, 1,
					new Insets(0, 5, 5, 5));

			pnl_informationRightSide = new JPanel();
			pnl_informationRightSide.setLayout(new BorderLayout());
			pnl_informationRightSide.setBackground(clr_background);

			pnl_informationRightSide.setBorder(BorderFactory.createLineBorder(clr_background, 15));
			pnl_informationRightSide.add(lbl_informationTitle, BorderLayout.NORTH);
			pnl_informationRightSide.add(scp_information, BorderLayout.CENTER);

			pnl_input = new JPanel();
			pnl_input.setLayout(gridbaglayout);
			pnl_input.setBackground(clr_background);

			Essentials.addComponent(pnl_input, gridbaglayout, txf_userid, 0, 0, 1, 1, 1, 1, new Insets(15, 15, 15, 15));
			Essentials.addComponent(pnl_input, gridbaglayout, btn_locknumber, 1, 0, 1, 1, 1, 1,
					new Insets(15, 15, 15, 15));
			Essentials.addComponent(pnl_input, gridbaglayout, btn_login, 2, 0, 1, 1, 1, 1, new Insets(15, 15, 15, 15));

			// pnl_input.setLayout(new BorderLayout());
			// pnl_input.add(txf_userid, BorderLayout.WEST);
			// pnl_input.add(btn_locknumber, BorderLayout.CENTER);
			// pnl_input.add(btn_login, BorderLayout.EAST);

			pnl_serverinformation = new JPanel();
			pnl_serverinformation.setLayout(gridbaglayout);
			pnl_serverinformation.setBackground(Color.green);

			pnl_header.setBounds(0, 0, width, height / 7 * 2 - height / 11);
			pnl_informationLeftSide.setBounds(0, height / 7 * 2 - height / 11, width / 3 * 2, height / 7 * 4);
			pnl_informationRightSide.setBounds(width / 3 * 2, height / 7 * 2 - height / 11, width / 3, height / 7 * 4);
			pnl_input.setBounds(0, height / 7 * 6 - height / 11, width, height / 7);
			pnl_serverinformation.setBounds(0, height / 7 * 7 - height / 11, width, height / 11);

			fnt_header = new Font("Arial Rounded MT Bold", Font.BOLD, 40);

			lbl_header = new JLabel("Citizen Data Managment System - Zugangskontrolle");
			lbl_header.setFont(fnt_header);
			lbl_header.setForeground(Color.black);
			lbl_header.setHorizontalAlignment(SwingConstants.CENTER);
			lbl_header.setVerticalAlignment(SwingConstants.CENTER);

			pnl_header.add(lbl_header, BorderLayout.CENTER);

			panel.add(pnl_header);
			panel.add(pnl_informationLeftSide);
			panel.add(pnl_informationRightSide);
			panel.add(pnl_input);
			panel.add(pnl_serverinformation);

			frame.add(panel);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			txf_userid.requestFocus(true);
		} catch (Exception e) {
			return false;
		}
		return true;
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
				JOptionPane.showMessageDialog(null, "The Student ID field is empty.", "Error while logging in/out!",
						JOptionPane.ERROR_MESSAGE);

			} else {
				String studentid = txf_userid.getText();

				//getCitizenState [id] = returns "inside" / "outside";
				//setCitizenLogoutPoint [id] = setLogoutTimePoint, attached, seperated with ";"
				//setCitizenLoginPoint [id] = setLoginTimePoint, attached, seperated with ";"
				
				Main.connection.writeLine("getCitizenState " + studentid);
				String state = Main.connection.readLine();
				
				if(state.equals("inside")){
					Main.connection.writeLine("setCitizenLogoutPoint " + studentid);
				}else if(state.equals("outside")){
					Main.connection.writeLine("setCitizenLoginPoint " + studentid);
				}

				restoreDefaults();
			}
			txf_userid.requestFocus();
		}
		if (e.getSource() == btn_locknumber && !btn_locknumber.getText().contains("Reset")) {
			if (txf_userid.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "The Student ID field is empty.", "Error while logging in/out!",
						JOptionPane.ERROR_MESSAGE);

			} else {
				String studentid = txf_userid.getText();

				btn_login.setEnabled(true);
				txf_userid.setEditable(false);
				btn_locknumber.setText("Reset Schülerinfo");

				// getCitizenName [id] = [name des schülers]
				// getCitizenLoginPoints [id] = [liste mit semikolon getrennt,
				// wann einlogzeiten waren]
				// getCitizenLogoutPoints [id] = [liste mit semikolon getrennt,
				// wann auslogzeiten waren]
				// getCitizenClass [id] = [klasse des schülers]
				// getInformations = [liefet wichtige informationen zurück]
				// getTodaysDate = System.currentTimeMillis()

				Main.connection.writeLine("getCitizenName " + studentid);
				String name = Main.connection.readLine();

				Main.connection.writeLine("getCitizenLoginPoints " + studentid);
				String[] loginarray = Main.connection.readLine().split(";");

				Main.connection.writeLine("getCitizenLogoutPoints " + studentid);
				String[] logoutarray = Main.connection.readLine().split(";");

				Main.connection.writeLine("getCitizenClass " + studentid);
				String classLevel = Main.connection.readLine();

				Main.connection.writeLine("getTodaysDate");
				long currentmillis = Long.parseLong(Main.connection.readLine());

				Timestamp todaytimestamp = new Timestamp(currentmillis);
				Date today = new Date(todaytimestamp.getTime());

				String attendance = calculateAttendanceTime(studentid, loginarray, logoutarray, dateFormat, today);

				Main.connection.writeLine("getInformations");
				String information = Main.connection.readLine();

				fillGaps(name, classLevel, attendance, null, information);
			}
		} else if (e.getSource() == btn_locknumber && btn_locknumber.getText().contains("Reset")) {
			restoreDefaults();
		}
	}

	public static String calculateAttendanceTime(String studentid, String[] loginarray, String[] logoutarray,
			SimpleDateFormat dateFormat, Date today) {

		long diffMillis = 0;

		try {
			for (int i = 0; i < logoutarray.length; i++) {

				Timestamp logintimestamp = new Timestamp(Long.parseLong(loginarray[i]));
				Timestamp logouttimestamp = new Timestamp(Long.parseLong(logoutarray[i]));
				Date from = new Date(logintimestamp.getTime());
				Date to = new Date(logouttimestamp.getTime());

				if (today.getDate() == from.getDate()) {
					diffMillis = diffMillis + to.getTime() - from.getTime();
				}
			}

		} catch (Exception e) {
			return "";
		}

		diffMillis = diffMillis / 1000;
		
		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int seconds = (int) (diffMillis % SECONDS_IN_A_MINUTE);
		int totalMinutes = (int) (diffMillis / SECONDS_IN_A_MINUTE);
		int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
		int hours = totalMinutes / MINUTES_IN_AN_HOUR;

//		System.out.println(hours + "h " + minutes + "min " + seconds + "s");

		return hours + "h " + minutes + "min " + seconds + "s";

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}