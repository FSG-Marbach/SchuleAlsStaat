/**
 * 
 */
package essentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Maximilian
 *
 */
public class Security {

	/**
	 * Encrypts file with the AES-Algorithm
	 * 
	 * @param inputFile
	 *            The path of the file to encrypt
	 * 
	 * @param key
	 *            A 128 bit (16 byte) key to encrypt the file
	 * @param outputFile
	 *            The path were the encrypted file should be saved
	 * @return False if an Exception occurred
	 */
	public static boolean encrypt(String inputFile, String key, File outputFile) {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		} catch (NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Encrypts strings with the AES-Algorithm
	 * 
	 * @param input
	 *            The string to encrypt
	 * @param key
	 *            A 128 bit (16 byte) key to encrypt the file
	 *
	 * @return False if an Exception occurred
	 */
	public static String encrypt(String input, String key) {
		byte[] outputBytes;
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] inputBytes = input.getBytes();

			outputBytes = cipher.doFinal(inputBytes);

		} catch (NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException e) {
			e.printStackTrace();
			return "";
		}

		return new String(outputBytes);

	}

	/**
	 * Decrypts files with the AES-Algorithm
	 * 
	 * @param inputFile
	 *            The path of the file to decrypt
	 * @param key
	 *            A 128 bit (16 byte) key to decrypt the file
	 * @param outputFile
	 *            The path were the decrypted file should be saved
	 * @return False if an Exception occurred
	 */
	public static boolean decrypt(File inputFile, String key, File outputFile) {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		} catch (NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Decrypts strings with the AES-Algorithm
	 * 
	 * @param input
	 *            The string to decrypt
	 * @param key
	 *            A 128 bit (16 byte) key to decrypt the file
	 *
	 * @return False if an Exception occurred
	 */
	public static String decrypt(String key, String input) {

		byte[] outputBytes;
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] inputBytes = input.getBytes();

			outputBytes = cipher.doFinal(inputBytes);

		} catch (NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException e) {
			e.printStackTrace();
			return "";
		}

		return new String(outputBytes);

	}

}
