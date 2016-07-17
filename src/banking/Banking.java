package banking;

import java.io.File;
import java.util.Properties;

import clientLibrary.Connection;
import essentials.Settings;
import essentials.SimpleLog;

public class Banking {

	final String PATH = "res\\banking\\";
	final int PORT = 3746;
	
	public Banking() {
		
		Properties defaultValues = new Properties();
		defaultValues.setProperty("port", String.valueOf(PORT));
		defaultValues.setProperty("truststore", "client.truststore");
		defaultValues.setProperty("truststorePassword", "123456");
		Settings settings = new Settings(new File(PATH + "settings.properties"), defaultValues, false, new SimpleLog());
		
		Connection connection = new Connection(new SimpleLog(), settings);
		connection.connect();
		
		new GUI();
	}
	
	public static void main(String[] args) {
		new Banking();
	}
}
