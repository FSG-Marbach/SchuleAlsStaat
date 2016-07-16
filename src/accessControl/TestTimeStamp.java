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

				calculateAttendanceTime("1", loginarray, logoutarray, dateFormat);

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
			SimpleDateFormat dateFormat) {
		long diffMillis = 0;
		long diffDays = 0;
		long diffHours = 0;
		long diffMinutes = 0;
		long diffSeconds = 0;

		String wholeTime = "";
		for (int i = 0; i < logoutarray.length; i++) {

			try {
				Date from = dateFormat.parse(loginarray[i]);
				Date to = dateFormat.parse(logoutarray[i]);

				diffMillis = to.getTime() - from.getTime();
				diffDays = diffMillis / (1000 * 60 * 60 * 24);
				diffHours = diffMillis / (1000 * 60 * 60);
				diffMinutes = diffMillis / (1000 * 60);
				diffSeconds = diffMillis / (1000);

				// log.log(studentid + " attendance: " + diffHours + "h " +
				// diffMinutes + "min " + diffSeconds + "s");
				wholeTime = wholeTime + diffHours + ":" + diffMinutes + ":" + diffSeconds + ";";
			} catch (Exception ex) {
				// log.logStackTrace(ex);
				System.out.print(ex);
				return "Fehler bei der Berechnung";
			}
		}
		String[] attendanceTimes = wholeTime.split(";");
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		for(int i=0; i<attendanceTimes.length;i++){
			hours = Integer.parseInt(attendanceTimes[i].substring(0, attendanceTimes[i].indexOf(":")));
			System.out.println("Gesamte Stunde: " + hours);
			minutes = Integer.parseInt(attendanceTimes[i].substring(attendanceTimes[i].indexOf(":"), attendanceTimes[i].lastIndexOf(":")));
			System.out.println("Gesamte Minuten: " + minutes);
			hours = Integer.parseInt(attendanceTimes[i].substring(0, attendanceTimes[i].length()));
			System.out.println("Gesamte Sekunden: " + seconds);
		}

		System.out.println(wholeTime);
		return diffHours + "h " + diffMinutes + "min";

	}

}
