package essentials;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This 'Essentials'-class contains some quite useful methods.
 * 
 * @author Felix Beutter
 * @author Maximilian von Gaisberg
 * @version 1.0.0
 */
public class Essentials {

	/**
	 * The 'addComponent' method adds a component to a container using the
	 * GridBagLayout.
	 * 
	 * @param container
	 *            Container to which the component will be added
	 * @param layout
	 *            Used GridBagLayout object
	 * @param component
	 *            Component which will be added to the container
	 * @param x
	 *            x coordinate of the component in the grid
	 * @param y
	 *            y coordinate of the component in the grid
	 * @param width
	 *            width of the component
	 * @param height
	 *            height of the component
	 * @param weightx
	 *            x weighting of the component
	 * @param weighty
	 *            y weighting of the component
	 * @param insets
	 *            insets object which declares the distances around the
	 *            component
	 * @return boolean if false, exception occurred
	 */
	public static boolean addComponent(Container container,
			GridBagLayout layout, Component component, int x, int y, int width,
			int height, double weightx, double weighty, Insets insets) {

		try {

			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = x;
			constraints.gridy = y;
			constraints.gridwidth = width;
			constraints.gridheight = height;
			constraints.weightx = weightx;
			constraints.weighty = weighty;
			constraints.insets = insets;
			layout.setConstraints(component, constraints);
			container.add(component);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * The 'getHashMapObjects' method returns a Object[] which contains the keys
	 * of the given HashMap.
	 * 
	 * @param hashmap
	 *            HashMap<String, Object> object
	 * @return Object[] which contains HashMap data
	 */
	public static Object[] getHashMapObjects(HashMap<String, Object> hashmap) {

		Object[] objects = new Object[0];

		Set<String> keys = hashmap.keySet();
		for (String s : keys) {

			Object[] array = new Object[objects.length + 1];
			System.arraycopy(objects, 0, array, 0, objects.length);
			array[objects.length] = s;
			objects = array;
		}

		return objects;
	}

	/**
	 * The 'log' method writes a string (with a timestamp) into a file.
	 * 
	 * @param text
	 *            The String, that will be written to the file
	 * @param file
	 *            The file where the text will be saved to
	 * @param printTimestamp
	 *            If true, there will be a timestamp in front of the text
	 * @return boolean if false, exception occurred
	 */
	public static boolean log(String text, File file, boolean printTimestamp) {

		try {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd.MM.yyyy hh:mm:ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file, true);

			if (printTimestamp)
				fileWriter.append((CharSequence) simpleDateFormat
						.format(timestamp) + " " + text + "\n");
			else
				fileWriter.append(text + "\n");

			fileWriter.close();
			System.out.println(simpleDateFormat.format(timestamp) + " " + text);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Writes a String to the end of a file. The 'printStringToFile' method
	 * writes a String to the end of a file.
	 * 
	 * @param text
	 *            The String, that will be written to the file
	 * @param file
	 *            The file where the text will be saved to
	 * @return boolean if false, exception occurred
	 */
	public static boolean printStringToFile(String text, File file) {

		try {

			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.append(text + "\n");
			fileWriter.close();
			System.out.println("Wrote '" + text + "' into '" + file.getPath()
					+ "'");
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Reads a given file and returns the text
	 * 
	 * @param file
	 *            The file you want to read
	 * 
	 * @return The content of the file
	 */
	public static String readFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				file.getAbsolutePath()));
		StringBuilder sb = new StringBuilder();
		try {

			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
		} finally {
			br.close();
		}

		return sb.toString();

	}

	/**
	 * Counts the number of lines of the given file. The 'countFileLines' method
	 * counts the number of lines of the given file.
	 * 
	 * @param file
	 *            The file, which lines will be counted
	 * @return amount of lines
	 * @throws IOException
	 */
	public static int countFileLines(File file) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

	/**
	 * Puts files in an uncompressed zip folder
	 * 
	 * @param zipFile
	 *            The target zip-folder
	 * @param containingFiles
	 *            The files to put in the zip-folder
	 * @throws IOException
	 */
	public static boolean zip(File zipFile, File[] containingFiles)
			throws IOException {

		if (zipFile.exists()) {
			System.err.println("Zip file already exists, please try another");
			return false;
		}
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		int bytesRead;
		byte[] buffer = new byte[1024];
		CRC32 crc = new CRC32();
		for (int i = 0, n = containingFiles.length; i < n; i++) {
			File file = containingFiles[i];
			if (!file.exists()) {
				zos.close();
				throw new FileNotFoundException("Couldn't find file "
						+ file.getAbsolutePath());
			}
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			crc.reset();
			while ((bytesRead = bis.read(buffer)) != -1) {
				crc.update(buffer, 0, bytesRead);
			}
			bis.close();
			bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(file.getName());
			entry.setMethod(ZipEntry.STORED);
			entry.setCompressedSize(file.length());
			entry.setSize(file.length());
			entry.setCrc(crc.getValue());
			zos.putNextEntry(entry);
			while ((bytesRead = bis.read(buffer)) != -1) {
				zos.write(buffer, 0, bytesRead);
			}
			bis.close();
		}
		zos.close();
		return true;
	}

	/**
	 * Puts files in a zip-folder and compresses them
	 * 
	 * @param files
	 *            The files to put into the zip-file
	 * @param target
	 *            The path of the target zip-file
	 * @throws IOException
	 */
	public static void zipAndCompress(String target, String[] files)
			throws IOException {
		byte b[] = new byte[512];
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(target));
		for (int i = 0; i < files.length; i++) {
			InputStream in = new FileInputStream(files[i]);
			ZipEntry e = new ZipEntry(new File(files[i]).getName());
			zout.putNextEntry(e);
			int len = 0;
			while ((len = in.read(b)) != -1) {
				zout.write(b, 0, len);
			}
			zout.closeEntry();
			in.close();
		}
		zout.close();

	}

	/**
	 * Sends HTTP requests to a Webserver and fetch the answer A method to send
	 * HTTP requests to a Webserver and fetch the answer
	 * 
	 * @param url
	 *            The URL you want to send a request to
	 * @return The answer from that URL
	 * @throws IOException
	 */
	public static String sendHTTPRequest(URL url) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String answer = "";
		String line = "";
		while (null != (line = br.readLine())) {
			answer = answer + line + "\n";
		}
		return answer;
	}

	/**
	 * Downloads a file from a url and save it on the computer
	 * 
	 * @param url
	 *            The URL of the file
	 * @param saveFile
	 *            The path where the file should be saved
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static boolean downloadFileFromURL(URL url, File saveFile)
			throws IOException, FileNotFoundException {

		HttpURLConnection c;

		c = (HttpURLConnection) url.openConnection();

		c.connect();

		BufferedInputStream in = new BufferedInputStream(c.getInputStream());

		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				saveFile));
		byte[] buf = new byte[256];
		int n = 0;
		while ((n = in.read(buf)) >= 0) {
			out.write(buf, 0, n);
		}
		out.flush();
		out.close();

		return true;
	}

	/**
	 * Copies a BufferedImage.
	 * 
	 * @param image
	 *            The image that should be copied
	 * 
	 * @returns The copied image
	 */
	public static BufferedImage copyBufferedImage(BufferedImage image) {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	/**
	 * Search for all IPs in the local network unsing the arp -a command. ONly
	 * works on Windows
	 * 
	 * @return An String array containing the IPs
	 * @throws IOException
	 */
	public static String[] searchIPs() throws IOException {
		String[] line = null;
		String[] ips = null;
		String answer = "";

		Process p = Runtime.getRuntime().exec("arp -a");
		InputStream is = p.getInputStream();
		int c;
		while ((c = is.read()) != -1) {
			// System.out.print((char) c );
			answer = answer + (char) c;
		}

		line = answer.split(Pattern.quote("\n"));
		int length = line.length;
		String[] line2 = new String[length];
		ips = new String[line.length - 3];
		for (int i = 3; i < line.length; i++) {
			line2[i - 2] = line[i];
			ips[i - 3] = line[i].substring(0, 17).trim();

		}
		return ips;

	}

}
