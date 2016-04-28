package server;

import java.io.File;
import java.util.Properties;

import essentials.Settings;

public abstract class Commands {

	static void reload(int id, String[] command) {

		if (command[1] != null) {
			switch (command[1]) {
			case "all":
				reloadPasswords();
				reloadPermissions();
				break;
			case "passwords":
				reloadPasswords();
				break;
			case "permissions":
				reloadPermissions();
				break;
			default:
				Server.getLog().warning("Client " + id + " sent invalid command: '" + command[1] + "'!");
			}
		} else {
			Server.getLog().warning("Client " + id + " sent invalid command: '" + command[1] + "'!");
		}
	}

	static void reloadPasswords() {
		Settings passwords = new Settings(new File(Server.getSettings().getSetting("passwordsPath")), new Properties(),
				false, Server.getLog());
		Server.setPasswords(passwords);

		Server.getLog().info("Reloaded passwords");
	}

	static void reloadPermissions() {
		Settings permissions = new Settings(new File(Server.getSettings().getSetting("permissionsPath")),
				new Properties(), false, Server.getLog());
		Server.setPasswords(permissions);

		Server.getLog().info("Reloaded permissions");
	}
}
