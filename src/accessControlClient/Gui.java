package accessControlClient;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import essentials.Essentials;

/**
 * 
 * @author Johannes Groﬂ
 *
 */

public class Gui {

	private JFrame frame;
	private JPanel panel, pnl_header, pnl_informationLeftSide, pnl_informationRightSide, pnl_input,
			pnl_serverinformation;
	private JLabel lbl_header, lbl_name, lbl_nameTitle, lbl_class, lbl_classTitle, lbl_attendance, lbl_attendanceTitle,
			lbl_state, lbl_stateTitle, lbl_informationTitle, lbl_information;
	private Color clr_background;
	private Font fnt_header, fnt_title, fnt_normal;
	private GridBagLayout gridbaglayout;
	private int width, height;

	// Later not used only for Tests
	private JPanel rest;

	public void restoreDefaults() {
		lbl_name.setText("");
		lbl_class.setText("");
		lbl_attendance.setText("");
		lbl_state.setText("");
		lbl_information.setText("");
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
			if (state) {
				lbl_state.setForeground(Color.GREEN);
				lbl_state.setText("Ja");
			} else {
				lbl_state.setForeground(Color.RED);
				lbl_state.setText("Nein");
			}
			lbl_information.setText(information);
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

			clr_background = new Color(10, 240, 240);
			fnt_header = new Font("Arial Rounded MT Bold", Font.BOLD, 20);
			fnt_title = new Font("Arial Rounded MT Bold", Font.BOLD, 14);
			fnt_title = new Font("Arial Rounded MT Bold", 0, 14);
			gridbaglayout = new GridBagLayout();

			frame = new JFrame();
			frame.setSize(width, height);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setLayout(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setUndecorated(true);

			lbl_header = new JLabel("Citizen Data Managment System - Zugangskontrollclient");
			lbl_header.setFont(fnt_header);
			lbl_header.setForeground(Color.black);

			lbl_name = new JLabel("");
			lbl_name.setFont(fnt_normal);

			lbl_nameTitle = new JLabel("Name:");
			lbl_nameTitle.setFont(fnt_title);

			lbl_class = new JLabel("");
			lbl_class.setFont(fnt_normal);

			lbl_classTitle = new JLabel("Klasse:");
			lbl_classTitle.setFont(fnt_title);

			lbl_attendance = new JLabel("");
			lbl_attendance.setFont(fnt_normal);

			lbl_attendanceTitle = new JLabel("Bissherige Anwesenheitszeit:");
			lbl_attendanceTitle.setFont(fnt_title);

			lbl_state = new JLabel("");
			lbl_state.setFont(fnt_normal);

			lbl_stateTitle = new JLabel("Person ist anwesend:");
			lbl_stateTitle.setFont(fnt_title);

			lbl_informationTitle = new JLabel("Wichtige Informationen:");
			lbl_informationTitle.setFont(fnt_title);

			lbl_information = new JLabel("");
			lbl_information.setFont(fnt_normal);

			panel = new JPanel();
			panel.setLayout(gridbaglayout);
			panel.setBounds(0, 0, width, height);
			panel.setBackground(clr_background);

			/**
			 * The 'pnl_header' is at the top
			 * 
			 * @add 'lbl_header'
			 */
			pnl_header = new JPanel();
			pnl_header.setLayout(gridbaglayout);
			pnl_header.setBackground(Color.MAGENTA);

			/**
			 * The 'pnl_informationLeftSide' is on the leftside, gets 2/3 of the
			 * width and is in the middle under the 'pnl_header'
			 * 
			 * @add 'lbl_nameTitle' The title over the student¥s name
			 * 
			 * @add 'lbl_name' shows the name of the student
			 * 
			 * @add 'lbl_classTitle' The title over the student¥s classlevel
			 * 
			 * @add 'lbl_class' shows the classlevel of the student
			 *
			 * @add 'lbl_attendanceTitle' The title over the student¥s
			 *      attendance
			 * 
			 * @add 'lbl_attendance' shows the attendance time of the student
			 * 
			 * @add 'lbl_stateTitle' The title over the student¥s state
			 * 
			 * @add 'lbl_state' shows the state of the student (in-/outside the
			 *      state)
			 * 
			 */

			// TODO Add Image
			pnl_informationLeftSide = new JPanel();
			pnl_informationLeftSide.setLayout(gridbaglayout);
			pnl_informationLeftSide.setBackground(Color.cyan);

			/**
			 * The 'pnl_informationLeftSide' is on the leftside, gets 1/3 of the
			 * width and is in the middle under the 'pnl_header'
			 * 
			 * @add 'lbl_informationTitle' The title over the messages
			 * 
			 * @add 'lbl_information' shows important messages
			 */
			pnl_informationRightSide = new JPanel();
			pnl_informationRightSide.setLayout(gridbaglayout);
			pnl_informationRightSide.setBackground(Color.PINK);

			// TODO Textfield for serialnumber
			// TODO Button to accept serialnumber
			// TODO Button to log the student in/out
			pnl_input = new JPanel();
			pnl_input.setLayout(gridbaglayout);
			pnl_input.setBackground(Color.gray);

			// TODO Add Server Informations
			pnl_serverinformation = new JPanel();
			pnl_serverinformation.setLayout(new FlowLayout());
			pnl_serverinformation.setBackground(clr_background);

			Essentials.addComponent(panel, gridbaglayout, pnl_header, 0, 0, 2, 2, 0, 0, new Insets(0, 0, 0, 0));
			Essentials.addComponent(panel, gridbaglayout, pnl_informationLeftSide, 0, 3, 2, 9, 0, 0,
					new Insets(0, 0, 0, 0));
			Essentials.addComponent(panel, gridbaglayout, pnl_informationRightSide, 0, 11, 1, 9, 0, 0,
					new Insets(0, 0, 0, 0));
			Essentials.addComponent(panel, gridbaglayout, pnl_input, 0, 19, 3, 2, 0, 0, new Insets(0, 0, 0, 0));
			Essentials.addComponent(panel, gridbaglayout, pnl_serverinformation, 0, 21, 3, 1, 0, 0,
					new Insets(0, 0, 0, 0));

			frame.add(panel);
			frame.setVisible(true);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Gui() {

	}

}
