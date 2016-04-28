package server;

public abstract class Commands {

	// Reloading server properties	
	static void reload(int id, String[] command) {

		if (!(command.length < 2)) {

			for (int i = 0; i < command.length - 1; i++) {

				if (command[i + 1] != null) {
					switch (command[i + 1]) {
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
						Server.getLog().warning("Client " + id + " sent invalid argument: '" + command[i + 1]
								+ "' in command: '" + getCompleteCommand(command) + "'!");
					}
				} else {
					Server.getLog().warning("Client " + id + " sent invalid argument: '" + command[i + 1]
							+ "' in command: '" + getCompleteCommand(command) + "'!");
				}
			}
		} else {
			Server.getLog().warning("Client " + id + " sent invalid command: '" + getCompleteCommand(command) + "'!");
		}
	}

	static String getCompleteCommand(String[] s) {

		String request = "";
		for (int i = 0; i < s.length; i++) {
			
			if(!(i == s.length - 1)) request = request + s[i] + " ";
			else request = request + s[i];
		}

		return request;
	}
}
