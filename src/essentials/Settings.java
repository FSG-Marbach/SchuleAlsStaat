/**
 * 
 */
package essentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * @author Maximilian von Gaisberg
 *
 */
public class Settings {

	Properties settings = new Properties();

	public Settings(File file, Properties defaultValues, SimpleLog log) {
		String filename = file.getName();
		log.debug("Reading " + filename);
		try {
			if (file.canRead()) {

				try {
					settings.loadFromXML(new FileInputStream(file));
					log.info("Settings loaded from " + filename);
				} catch (InvalidPropertiesFormatException e) {
					log.error("Wrong properties format in " + filename
							+ " Resetting " + filename + " to default values.");
					settings = defaultValues;
					settings.storeToXML(new FileOutputStream(file), null);
				}

			} else {
				if (file.exists()) {
					log.fatal("Can't read " + filename);
					System.exit(1);
				} else {
					log.warning(filename
							+ " doesn't exist! Using default settings");
					file.createNewFile();
					settings = defaultValues;
					settings.storeToXML(new FileOutputStream(file), null);
				}
			}

		} catch (FileNotFoundException e) {

			log.error("FileNotFoundException while reading " + filename
					+ e.getMessage());
			System.exit(1);

		} catch (IOException e) {
			log.error("IOException while reading" + filename + e.getMessage());
			System.exit(1);
		}
	}

	public String getSetting(String key) {
		return settings.getProperty(key);
	}

	public void setSetting(String key, String value) {
		settings.setProperty(key, value);
	}
}