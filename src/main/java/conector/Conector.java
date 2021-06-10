/*
 * @author Daniel Mijens Tutor
 */
package conector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connect the database in the cloud with the app in Java.
 */
public class Conector {
	
	/** The prop. */
	Properties prop = new Properties();

	/**
	 * Instantiates a new conector.
	 */
	public Conector() {
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the my SQL connection.
	 *
	 * @return the my SQL connection
	 */
	public Connection getMySQLConnection() {
		try {

			// Indicates which driver is going to be used.
			Class.forName(prop.getProperty(MySQLConstants.DRIVER));

			try {
				// Creates the connection based on the obtained URL.
				return DriverManager.getConnection(getURL());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getURL() {
		return new StringBuilder().append(prop.getProperty(MySQLConstants.URL_PREFIX))
				.append(prop.getProperty(MySQLConstants.URL_HOST)).append(":")
				.append(prop.getProperty(MySQLConstants.URL_PORT)).append("/")
				.append(prop.getProperty(MySQLConstants.URL_SCHEMA)).append("?user=")
				.append(prop.getProperty(MySQLConstants.USER)).append("&password=")
				.append(prop.getProperty(MySQLConstants.PASSWD)).append("&useSSL=")
				.append(prop.getProperty(MySQLConstants.URL_SSL)).toString();
	}
}