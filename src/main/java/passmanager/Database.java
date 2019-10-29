package passmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import java.nio.file.Files;

public class Database {

	private final static Logger Log = Util.getLogger(Database.class);
	
	/**
	 * database file
	 */
	private File dbFile;
	
	/*
	 * database connection
	 */
	private Connection conn;
	

	Database() throws SQLException {
		try {
			dbFile = new File(App.getStoragePath("storage.sqlite3").toURI());
		}
		catch (URISyntaxException e) {
			e.printStackTrace(); // probably need a more graceful way of handling this
		}
		
		/* First time running the application, database deleted, etc. 
		 * This copies the database template file. */
		if (!dbFile.exists()) {
			Log.info("Initializing database.");
			
			try {
				Files.copy(App.getResource("storage.sqlite3"), dbFile.toPath());
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
	}

	/**
	 * Closes the database connection.
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

}
