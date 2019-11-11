package passmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Database {

	private final static Logger Log = Util.getLogger(Database.class);

	/**
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection connect() throws SQLException {
		File dbFile = App.getStoragePath("storage.sqlite3").toFile();

		/* First time running the application, database deleted, etc. 
		 * This copies the database template file. */
		if (!dbFile.exists()) {
			Log.info("Initializing database.");
			
			try {
				// TODO: probably a good idea to encrypt the database, since it contains passwords and other secrets. leaving it alone until everything else works.
				Files.createDirectories(dbFile.toPath().getParent());
				Files.copy(App.getResource("storage.sqlite3"), dbFile.toPath());
				
				Files.createFile(dbFile.toPath());
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
	}
	
	/**
	 * Only checks if the database was copied already. 
	 * true = exists, false = doesn't exist
	 * @return boolean
	 */
	public static boolean exists() {
		return App.getStoragePath("storage.sqlite3").toFile().exists();
	}
	
	/**
	 * Deletes the database from the disk. Don't use this, unless you're doing tests or something...
	 * @return boolean		true = success
	 */
	public static boolean deleteFromDisk() {
		return App.getStoragePath("storage.sqlite3").toFile().delete();
	}

}
