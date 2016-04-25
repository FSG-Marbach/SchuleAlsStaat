/**
 * 
 */
/**
 * 
 */
package essentials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * 
 * Various ways of safely deleting files.
 * 
 * @author Maximilian
 *
 */
public class FileUtils {

	File file;
	Random rand;

	public FileUtils(File f) {
		file = f;
		rand = new Random();
	}

	/**
	 * Will overwrite the file with random bytes. .wipe() is probably better and
	 * more efficient, but I'm not sure if it works. Takes 23 seconds for one
	 * iteration over a 50MB file.
	 * 
	 * @param times
	 *            Number of iterations
	 * @throws IOException
	 */

	public boolean shred(int times) {
		long totalBytes = file.length();
		long shreddedBytes = 0;
		int length = 0;
		while (totalBytes > shreddedBytes) {
			if ((totalBytes - shreddedBytes) <= (2 ^ 31)) {
				length = (int) (totalBytes - shreddedBytes);

			}
			length = (2 ^ 31);
			shreddedBytes += length;
			byte[] content = new byte[(int) length];
			FileOutputStream out;
			for (int i = 0; i < times; i++) {

				rand.nextBytes(content);

				try {
					out = new FileOutputStream(file);
					out.write(content);
					out.close();
				} catch (IOException e) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * Will overwrite the file with random bytes. Is probably better then
	 * .shred, but .shred works for sure, this doesn't. Takes 23 seconds for one
	 * iteration over a 50MB file.
	 * 
	 * @param times
	 *            Number of iterations
	 */
	public boolean wipe(int times) {
		for (int i = 0; i < times; i++) {
			try {
				RandomAccessFile rwFile = new RandomAccessFile(file, "rw");
				try {
					FileChannel rwChannel = rwFile.getChannel();
					int numBytes = (int) rwChannel.size();
					MappedByteBuffer buffer = rwChannel.map(
							FileChannel.MapMode.READ_WRITE, 0, numBytes);
					buffer.clear();
					byte[] randomBytes = new byte[numBytes];
					rand.nextBytes(randomBytes);
					buffer.put(randomBytes);
					buffer.force();
					// will already write to the disk
				} finally {
					rwFile.close();
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Will change random bytes to random values. Really slow! Takes 20 minutes
	 * for one iteration over a 50MB file.
	 * 
	 * @param howLong
	 *            How long the file should burn. 1 will burn approximately half
	 *            the file.
	 * 
	 */
	public boolean burn(float howLong) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file.getPath(), "rw");
			for (int i = 0; i < (int) (raf.length() * howLong); i++) {
				raf.seek((int) (Math.random() * ((raf.length()) + 1)));
				byte[] b = new byte[1];
				rand.nextBytes(b);
				raf.write(b);
			}
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	/**
	 * Deletes a file in the normal way. Insecure! Use secureDelete()!
	 */
	public boolean delete() {
		if (!file.exists()) {
			return true;
		}

		if (!file.isDirectory())
			return file.delete();

		String[] list = file.list();
		for (int i = 0; i < list.length; i++) {
			if (!new FileUtils(new File(file.getPath() + File.separator
					+ list[i])).delete())
				return false;
		}

		return file.delete();
	}

	/**
	 * Overwrite the file with a given byte Takes 13 seconds for one iteration
	 * over a 50MB file.
	 * 
	 * @param data
	 *            The data to write in the file
	 * 
	 */
	public boolean overwriteWith(byte data) {
		long length = file.length();
		byte[] content = new byte[(int) length];
		for (byte b : content) {
			b = data;
		}

		FileOutputStream out;

		try {
			out = new FileOutputStream(file);
			out.write(content);
			out.close();
		} catch (IOException e) {
			return false;
		}

		return true;

	}

	/**
	 * Deletes files or directories safely, by overwriting them multiple times
	 * in different ways. Takes 800ms per MB
	 * 
	 * @return False if something went wrong
	 */
	public boolean secureDelete() {

		boolean worked = true;

		if (file.isDirectory()) {
			for (File c : file.listFiles())
				if (!new FileUtils(c).secureDelete())
					worked = false;
		} else {
			byte[] b = new byte[1];
			rand.nextBytes(b);
			if (!shred(1))
				worked = false;
			if (!overwriteWith(b[0]))
				worked = false;
			if (!wipe(1))
				worked = false;
		}
		if (!delete())
			worked = false;
		return worked;

	}

	public boolean create(long length) throws IOException {

		byte[] b = new byte[1];

		FileOutputStream out = new FileOutputStream(file);
		for (long i = 0; i < length; i++) {
			out.write(b);
		}
		out.close();

		return true;

	}
}
