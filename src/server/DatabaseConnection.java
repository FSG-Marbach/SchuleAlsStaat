/**
 * 
 */
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Maximilian A pretty useless class. It only exists, because Felix
 *         thinks, writing one line of code is faster than writing an other line
 *         of code
 *
 */
public class DatabaseConnection {
	private Connection connection;

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
	public DatabaseConnection(String connection) throws ClassNotFoundException,
			SQLException {

		Class.forName("com.mysql.jdbc.Drive");
		this.connection = DriverManager.getConnection(connection);
	}

	/**
	 * 
	 * @param query
	 *            The query to be executed
	 * @return The ResultSet
	 * @throws SQLException
	 *             If something went wrong
	 */
	public ResultSet executeQuery(String query) throws SQLException {

		return connection.createStatement().executeQuery(query);

	}

}
