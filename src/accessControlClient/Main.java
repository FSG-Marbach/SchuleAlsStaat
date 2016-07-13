package accessControlClient;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * @author Johannes Groﬂ
 */
public class Main {

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.initialize();

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
