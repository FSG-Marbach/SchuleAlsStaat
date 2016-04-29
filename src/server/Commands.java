package server;

import essentials.SimpleLog;

/**
 * @author Felix Beutter
 */

public abstract class Commands {

	// Reloading server properties
	static void reload(int id, String[] command) {

		SimpleLog log = Server.getLog();

		if (!(command.length < 2)) {

			for (int i = 0; i < command.length - 1; i++) {

				boolean b = false;
				if (command[i + 1] != null) {
					switch (command[i + 1]) {
					case "all":
						Server.getPasswords().reload();
						Server.getPermissions().reload();
					case "passwords":
						Server.getPasswords().reload();
						break;
					case "permissions":
						Server.getPermissions().reload();
						break;
					default:
						b = true;
					}
				} else {
					b = true;
				}

				if (b)
					log.warning("Client " + id + " sent invalid argument: '" + command[i + 1] + "' in command: '"
							+ getAssembledStringArray(command) + "'!");
			}
		} else {
			log.warning("Client " + id + " sent invalid command: '" + getAssembledStringArray(command) + "'!");
		}
	}

	/**
	 * Assembles a String Array into one String, where the parts are separated
	 * by space
	 * 
	 * @param array
	 *            The String Array, which will be assembled
	 * @return The assembled String
	 */
	static String getAssembledStringArray(String[] array) {

		String string = "";
		for (int i = 0; i < array.length; i++) {

			if (!(i == array.length - 1))
				string = string + array[i] + " ";
			else
				string = string + array[i];
		}
		return string;
	}
}
