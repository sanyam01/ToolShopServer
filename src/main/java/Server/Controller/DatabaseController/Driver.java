package Server.Controller.DatabaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class creates jdbc connection with Mysql database
 * 
 * @author Neha Singh
 *
 */
public class Driver implements JDBCredentials {

	// Attributes
	private Connection myConn;// Object of type connection from the JDBC class that deals with connecting to
								// the database

	public Driver() {

		try {

			setMyConn(DriverManager.getConnection(DB_URL, USERNAME, PASSWORD));
			System.out.println("Connected to DB...");

		} catch (SQLException e) {
			System.out.println("Unable to connect to DB");
			e.printStackTrace();
		}
	}

	public Connection getMyConn() {
		return myConn;
	}

	public void setMyConn(Connection myConn) {
		this.myConn = myConn;
	}

}
