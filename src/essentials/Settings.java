package essentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * 
 * A simple class for loading and saving settings to XML files
 * 
 * @author Maximilian von Gaisberg
 *
 */
public class Settings {
	File file;
	Properties settings = new Properties();
	SimpleLog log;

	/**
	 * Use this to read in the settings file
	 * 
	 * 
	 * @param file
	 *            The file to be read
	 * @param defaultValues
	 *            The values that should be used if the file is broken
	 * @param log
	 *            The log that should be logged to
	 */

	public Settings(File file, Properties defaultValues, SimpleLog log) {
		this.file = file;
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

			log.fatal("FileNotFoundException while reading " + filename
					+ e.getMessage());
			log.logStackTrace(e);
			System.exit(1);

		} catch (IOException e) {
			log.fatal("IOException while reading" + filename + e.getMessage());
			log.logStackTrace(e);
			System.exit(1);
		}
	}

	public String getSetting(String key) {
		return settings.getProperty(key);
	}

	public void setSetting(String key, String value) {
		settings.setProperty(key, value);
		try {
			settings.storeToXML(new FileOutputStream(this.file), null);
		} catch (IOException e) {
			log.fatal("IOException while saving settings" + e.getMessage());
			log.logStackTrace(e);
			System.exit(1);
		}
	}
}