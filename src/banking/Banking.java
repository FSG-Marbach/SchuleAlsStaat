package banking;

import java.io.File;
import java.util.Properties;

import clientLibrary.Connection;
import essentials.Settings;
import essentials.SimpleLog;

public class Banking {

	final String PATH = "res\\banking\\";
	final int PORT = 3746;
	static Connection connection;

	public Banking() {

		Properties defaultValues = new Properties();
		defaultValues.setProperty("port", String.valueOf(PORT));
		defaultValues.setProperty("truststore", "client.truststore");
		defaultValues.setProperty("truststorePassword", "123456");
		Settings settings = new Settings(new File(PATH + "settings.properties"), defaultValues, false, new SimpleLog());

		connection = new Connection(new SimpleLog(), settings);
		connection.connect();
		
//		String usertype = connection.getUsername().getType();
		GUI gui = new GUI();
//		if(usertype == "director"){
//			gui.enableOnlyAsDirector();
//		}
	}

	public static void main(String[] args) {
		new Banking();
	}
}
