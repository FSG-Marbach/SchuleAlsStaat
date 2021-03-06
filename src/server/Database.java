/**
 * 
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	public Database(String connection, SimpleLog log) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Drive");
		this.connection = DriverManager.getConnection(connection);
		this.log = log;
	}

	/**
	 * Get the value of a Bank Account
	 * 
	 * @param sban
	 * @return Either the value or null
	 */
	public Integer getBankAccountValue(String sban) {
		String query = "SELECT value FROM BankAccounts WHERE sban = '" + sban + "'";
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
		String query = "SELECT comment FROM BankAccounts WHERE sban = '" + sban + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
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
		String query = "SELECT type FROM BankAccounts WHERE sban = '" + sban + "'";
		try {

			return connection.createStatement().executeQuery(query).getInt(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	/**
	 * Create new BankAccount with given values
	 * 
	 * @param sban
	 * @param PIN
	 * @param value
	 * @param comment
	 * @param type
	 * @return
	 */
	public boolean createBankAccount(String sban, int value, String comment, int type) {
		String query = "INSERT INTO BankAccounts(sban, value, comment, type) VALUES ('" + sban + "', '" + value + "', '"
				+ comment + "', '" + type + "');";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	/**
	 * Set the bank account value
	 * 
	 * @param sban
	 * @param value
	 * @return
	 */
	public boolean setBankAccountValue(String sban, int value) {
		String query = "UPDATE BankAccounts SET value ='" + value + "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	/**
	 * Set the bank account comment
	 * 
	 * @param sban
	 * @param value
	 * @return
	 */
	public boolean setBankAccountComment(String sban, String comment) {
		String query = "UPDATE BankAccounts SET comment ='" + comment + "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	/**
	 * Set the bank account type
	 * 
	 * @param sban
	 * @param value
	 * @return
	 */
	public boolean setBankAccountType(String sban, String type) {
		String query = "UPDATE BankAccounts SET type ='" + type + "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	/**
	 * Set the bank account users
	 * 
	 * @param sban
	 * @param value
	 * @return
	 */
	public String getBankAccountUsers(String sban) {
		String query = "SELECT users FROM BankAccounts WHERE sban = '" + sban + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getUsersBankAccounts(String id) {
		String query = "SELECT bankAccounts FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public boolean setUserBankAccounts(String sbans, String id) {
		String users2 = "";
		String query = "UPDATE Citizens SET bankAccounts ='" + sbans + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	/**
	 * Set the bank account users
	 * 
	 * @param sban
	 * @param value
	 * @return
	 */
	public boolean setBankAccountUsers(String sban, String users) {
		String users2 = "";
		users2 = users2.substring(0, users2.length() - 1);
		String query = "UPDATE BankAccounts SET users ='" + users2 + "' WHERE sban = '" + sban + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean writeToLog(String timestamp, String userType, String user, String client, String message) {
		String query = "INSERT INTO Log(timestamp, user, userType, client, message) VALUES ('" + timestamp + "', '"
				+ userType + "', '" + user + "', '" + client + "', '" + message + "');";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;

		}
	}

	public ResultSet readLog(String userType, String userid) {
		String query = "SELECT timestamp, user, userType, client, message FROM Log WHERE userType = '" + userType
				+ "' AND user = '" + userid + "'";
		try {

			return connection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenName(String id) {
		String query = "SELECT name FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenPic(String id) {
		String query = "SELECT pic FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenClass(String id) {
		String query = "SELECT class FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenExchangeVolume(String id) {
		String query = "SELECT exchangeVolume FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenInformation(String id) {
		String query = "SELECT informations FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenCheckinTimes(String id) {
		String query = "SELECT checkinTimes FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public String getCitizenCheckoutTimes(String id) {
		String query = "SELECT checkoutTimes FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public boolean setCitizenCheckinTimes(String id, String checkinTimes) {
		String query = "UPDATE Citizens SET checkinTimes ='" + checkinTimes + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setCitizenCheckoutTimes(String id, String checkoutTimes) {
		String query = "UPDATE Citizens SET checkoutTimes ='" + checkoutTimes + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setCitizenPic(String id, String pic) {
		String query = "UPDATE Citizens SET pic ='" + pic + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setCitizenName(String id, String pic) {
		String query = "UPDATE Citizens SET pic ='" + pic + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setCitizenClass(String id, String className) {
		String query = "UPDATE Citizens SET className ='" + className + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean setCitizenExchangeVolume(String id, String exchangeVolume) {
		String query = "UPDATE Citizens SET exchangeVolume ='" + exchangeVolume + "' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean createCitizen(String name, String id, String pic, String className, String exchangeVolume) {
		String query = "INSERT INTO Citizens(name, id, pic, class, exchangeVolume) VALUES ('" + name + "', '" + id
				+ "', '" + pic + "', '" + className + "', '" + exchangeVolume + "');";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public String getCitizenHasReceivedBasicSecurity(String id) {
		String query = "SELECT basicSecurity FROM Citizens WHERE id = '" + id + "'";
		try {

			return connection.createStatement().executeQuery(query).getString(1);
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return null;
		}
	}

	public boolean payBasicSecurity(String id) {
		String basicSecurity = Server.settings.getSetting("basicSecurityBankAccount");
		if (basicSecurity == null) {
			log.error("No basic Security Bank Account found");
		}
		if (getBankAccountValue(basicSecurity) >= Integer.parseInt(Server.settings.getSetting("basicSecurityAmount"))) {
			setBankAccountValue(basicSecurity, getBankAccountValue(basicSecurity)
					- Integer.parseInt(Server.settings.getSetting("basicSecurityAmount")));
			setBankAccountValue(id,
					getBankAccountValue(id) - (-Integer.parseInt(Server.settings.getSetting("basicSecurityAmount"))));
		}

		String query = "UPDATE Citizens SET basicSecurity = 'true' WHERE id = '" + id + "'";
		try {

			connection.createStatement().executeQuery(query);
			return true;
		} catch (SQLException e) {
			log.error("Couldn't process " + query);
			log.logStackTrace(e);
			return false;
		}
	}

	public boolean addUser(String bankAccount, String user) {
		setBankAccountUsers(bankAccount, getBankAccountUsers(bankAccount) + "," + user);
		return setUserBankAccounts(user, getUsersBankAccounts(user) + "," + bankAccount);
	}

	public boolean removeUser(String bankAccount, String user) {
		setBankAccountUsers(bankAccount, getBankAccountUsers(bankAccount).replace("," + user, ""));
		return setUserBankAccounts(user, getUsersBankAccounts(user).replace("," + bankAccount, ""));
	}

	public boolean depositMartinis(String id, String amount) {

		return setBankAccountValue(id, getBankAccountValue(id) + (Integer.parseInt(amount)));

	}

	public boolean depositEuros(String id, String amount) {
		return depositMartinis(id, String.valueOf(Integer.parseInt(amount) * 10));
	}

	public boolean payMartinis(String id, String amount) {
		if (getBankAccountValue(id) >= Integer.parseInt(amount))
			return setBankAccountValue(id, getBankAccountValue(id) - Integer.parseInt(amount));
		else
			return false;
	}

	public String payEuros(String id, String amount) {
		payMartinis(id, amount);
		int m = Integer.parseInt(amount);
		m = (m * (1 - Integer.parseInt(Server.settings.getSetting("tax")) / 100)) / 10;
		return String.valueOf(m);
	}

}
