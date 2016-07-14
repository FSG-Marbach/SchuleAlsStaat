package server;

import essentials.Essentials;
import essentials.Settings;
import essentials.SimpleLog;

/**
 * @author Felix Beutter
 */

public abstract class Commands {

	// Reloading server properties
	static String reload(int id, String[] command, SimpleLog log, Settings passwords, Settings permissions) {

		if (!(command.length != 2)) {
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean b = false;
			if (command[1] != null) {
				switch (command[1]) {
				case "all":
					passwords.reload();
					permissions.reload();
					break;
				case "passwords":
					passwords.reload();
					break;
				case "permissions":
					permissions.reload();
					break;
				default:
					b = true;
				}
			} else {
				b = true;
			}

			if (b) {
				log.warning("Client " + id + " sent invalid argument: '" + command[1] + "' in command: '"
						+ Essentials.getAssembledStringArray(command) + "'!");
				return "Invalid argument: " + command[1];
			}
		} else {
			log.warning(
					"Client " + id + " sent invalid command: '" + Essentials.getAssembledStringArray(command) + "'!");
			return "Invalid command";
		}
		
		return "Reloaded properties";
	}

	static void shutdown(int id, String[] command) {

		// TODO
	}
}
