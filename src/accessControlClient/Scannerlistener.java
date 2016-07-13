package accessControlClient;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Scannerlistener implements NativeKeyListener {
	boolean write = false;
	String input = "";
	String userid = "";

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println(e.getKeyChar());
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

		input = e.getKeyChar() + "";
		System.out.println("Key: " + input);
		if (input.equals("<")) {
			write = true;
		}

		if (write == true) {
			userid = userid + input;
			System.out.println(userid);
		}

		if (input.equals(">")) {
			write = false;
			Gui.setStudentId(userid);
			System.out.println(userid);
			userid = "";
		}

	}

	public Scannerlistener() {

	}

}