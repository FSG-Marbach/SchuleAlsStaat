package server;

import java.io.File;
import java.util.Properties;

import essentials.Settings;

public abstract class Commands {

	static void reload(int id, String[] command) {

		if (command[1] != null) {
			switch (command[1]) {
			case "all":
				Server.getPasswords().reload();
				Server.getPermissions().reload();
				break;
			case "passwords":
				Server.getPasswords().reload();
				break;
			case "permissions":
				Server.getPermissions().reload();
				break;
			default:
				Server.getLog().warning("Client " + id + " sent invalid command: '" + command[1] + "'!");
			}
		} else {
			Server.getLog().warning("Client " + id + " sent invalid command: '" + command[1] + "'!");
		}
	}
}
