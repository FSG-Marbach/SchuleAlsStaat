package accessControlClient;

import java.io.File;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import clientLibrary.Connection;
import essentials.SimpleLog;

/**
 * @author Johannes Groﬂ
 */

public class Main {

	static SimpleLog log;
	final static String path = "res\\client\\";
	public static File logfile = new File(path + "accesscontrollclientlog.txt");
	public static boolean allowstudentpictures = true;
	public static Connection connection = new Connection(log);
	
	public static void main(String[] args) {
	
		log = new SimpleLog(logfile, true, true);
		log.log("Startet  'Citizen Data Management System' accesscontrollclient");
		log.log("allow studentpictures: " + allowstudentpictures);

		Gui gui = new Gui();
		log.log("Initialize GUI...");
		gui.initialize();
		log.log("GUI initialized");

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Problem beim Erstellen des Barcodelisteners aufgetreten, bitte informieren Sie einen zust‰ndigen Administrator.",
					"Barcodelistenerfehler", JOptionPane.ERROR_MESSAGE);
			System.err.println("There was a problem registering the barcodescannerlisterner.");
			System.err.println(ex.getMessage());
		}

		GlobalScreen.getInstance().addNativeKeyListener(new Scannerlistener());
		
	}

}
