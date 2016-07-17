/**
 * 
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import essentials.SimpleLog;

/**
 * @author Maximilian
 *
 */
public class Database {

	private Connection connection;
	private SimpleLog log;

	/**
	 * 
	 * @param connection
	 *            A String that represents the connection. Has to be formated as
	 *            following:
	 *            jdbc:mysql://localhost/feedback?user=sqluser&password
	 *            =sqluserpw
	 * @throws ClassNotFoundException
	 *             Will be thrown if the driver couldn't be loaded
	 * @throws SQLException
	 *             Will be thrown if the connection was not possible
	 */
	public Database(String connection, SimpleLog log)
			throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Drive");
		this.connection = DriverManager.getConnection(connection);
		this.log = log;
	}

	/**
	 * Get the PIN of a Bank Account
	 * 
	 * @param sban
	 * @return Either the pin or null
	 */
	public String getBankAccountPIN(String sban) {
		String query = "SELECT pin FROM BankAccounts WHERE sban = '" + sban
				+ "'";
		try {

			return connection.createStatement().executeQuery(query)
					.getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	/**
	 * Get the value of a Bank Account
	 * 
	 * @param sban
	 * @return Either the value or null
	 */
	public Integer getBankAccountValue(String sban) {
		String query = "SELECT value FROM BankAccounts WHERE sban = '" + sban
				+ "'";
		try {

			return connection.createStatement().executeQuery(query).getInt(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	/**
	 * Get the comment of a Bank Account
	 * 
	 * @param sban
	 * @return Either the comment or null
	 */
	public String getBankAccountComment(String sban) {
		String query = "SELECT comment FROM BankAccounts WHERE sban = '" + sban
				+ "'";
		try {

			return connection.createStatement().executeQuery(query)
					.getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	/**
	 * Get the type of a Bank Account
	 * 
	 * @param sban
	 * @return Either the type or null
	 */
	public Integer getBankAccountType(String sban) {
		String query = "SELECT type FROM BankAccounts WHERE sban = '" + sban
				+ "'";
		try {

			return connection.createStatement().executeQuery(query).getInt(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public boolean createBankAccount(String sban, String PIN, int value,
			String comment, int type) {
		String query = "INSERT INTO BankAccounts(sban, pin, value, comment, type) VALUES ('"
				+ sban
				+ "', '"
				+ PIN
				+ "', '"
				+ value
				+ "', '"
				+ comment
				+ "', '" + type + "');";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setBankAccountPIN(String sban, String PIN) {
		String query = "UPDATE BankAccounts SET PIN ='" + PIN
				+ "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setBankAccountValue(String sban, int value) {
		String query = "UPDATE BankAccounts SET value ='" + value
				+ "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setBankAccountComment(String sban, String comment) {
		String query = "UPDATE BankAccounts SET comment ='" + comment
				+ "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setBankAccountType(String sban, String type) {
		String query = "UPDATE BankAccounts SET type ='" + type
				+ "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public String[] getBankAccountUsers(String sban) {
		String query = "SELECT users FROM BankAccounts WHERE sban = '" + sban
				+ "'";
		try {

			return connection.createStatement().executeQuery(query)
					.getString(1).split(",");
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public boolean setBankAccounUsers(String sban, String[] users) {
		String users2 = "";

		for (String string : users) {
			users2 = users2 + string + ",";
		}
		users2 = users2.substring(0, users2.length() - 1);
		String query = "UPDATE BankAccounts SET users ='" + users2
				+ "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}
}
