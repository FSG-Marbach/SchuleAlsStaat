package accessControl;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import essentials.SimpleLog;

public class TestTimeStamp {
	static String loginzeit = "";
	static String logoutzeit = "";
	static Timestamp time = new Timestamp(System.currentTimeMillis());

	public static void main(String args[]) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");

		JButton trigger = new JButton("Login");
		trigger.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				time = new Timestamp(System.currentTimeMillis());
				System.out.println(dateFormat.format(time));
				if (trigger.getText().contains("in")) {
					loginzeit = loginzeit + dateFormat.format(time) + ";";
					trigger.setText("Logout");
				} else {
					logoutzeit = logoutzeit + dateFormat.format(time) + ";";
					trigger.setText("Login");
				}
			}
		});

		JButton calculate = new JButton("Auswerten");
		calculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Login: " + loginzeit);
				System.out.println("Logout: " + logoutzeit);

				String[] loginarray = loginzeit.split(";");
				String[] logoutarray = logoutzeit.split(";");

				// for (int i = 0; i < logoutarray.length; i++) {
				// System.out.print("Anwesend von " + loginarray[i] + " bis " +
				// logoutarray[i]);
				//
				// try {
				// Date from = dateFormat.parse(loginarray[i]);
				// Date to = dateFormat.parse(logoutarray[i]);
				//
				// long diffMillis = to.getTime() - from.getTime();
				// long diffDays = diffMillis / (1000 * 60 * 60 * 24);
				// long diffHours = diffMillis / (1000 * 60 * 60);
				// long diffMinutes = diffMillis / (1000 * 60);
				// long diffSeconds = diffMillis / (1000);
				//
				// System.out.print(" anwesende Zeit: Tage: " + diffDays + "
				// Stunden:" + diffHours + " Minuten: " + diffMinutes + ":" +"
				// Sekunden: "+ diffSeconds + "\n");
				// } catch (Exception ex) {
				//
				// }
				// }
				time = new Timestamp(System.currentTimeMillis());
				Date today = new Date(time.getTime());

				calculateAttendanceTime("1", loginarray, logoutarray, dateFormat, today);

			}
		});

		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(100, 50);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(trigger);
		frame.add(calculate);
		frame.pack();
		frame.setVisible(true);

	}

	public static String calculateAttendanceTime(String studentid, String[] loginarray, String[] logoutarray,
			SimpleDateFormat dateFormat, Date today) {

		long diffMillis = 0;

		try {
			for (int i = 0; i < logoutarray.length; i++) {

				Date from = dateFormat.parse(loginarray[i]);
				Date to = dateFormat.parse(logoutarray[i]);

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

}
