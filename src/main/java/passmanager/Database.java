package passmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.FileNotFoundException;

public class Database {

	/**
	 * handle to the 
	 */
	private Connection conn;

	Database() throws FileNotFoundException {
		App.class.getResource("/storage.");
	}

	public void close() throws SQLException {
		this.conn.close();
	}

}
