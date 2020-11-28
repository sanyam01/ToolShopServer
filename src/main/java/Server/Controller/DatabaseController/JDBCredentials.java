package Server.Controller.DatabaseController;

/**
 * Interface contains credentials to make connection to MySql database
 * 
 * @author Neha Singh
 *
 */
public interface JDBCredentials {

	// JDBC driver name and database URL
//	   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";

	// Database credentials
	static final String USERNAME = "root";
	static final String PASSWORD = "San@0103";

}
