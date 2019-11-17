package passmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.Random;
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
				Files.createFile(dbFile.toPath());
				Database.setup(dbFile);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return DriverManager.getConnection("jdbc:sqlite:" + dbFile.getPath());
	}
	
	/**
	 * Used to initialize the sqlite database with the proper schema.
	 * @param dbFile		file to setup
	 */
	private static void setup(File dbFile) {	
		try (	
			Connection db = Database.connect();
			Statement stmt = db.createStatement();
		) {
			stmt.addBatch("PRAGMA encoding = 'UTF-8'");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS settings (" + 
					"id               INTEGER PRIMARY KEY," + 
					"password         BLOB NOT NULL," + 
					"updated_at       TEXT NOT NULL," + 
					"last_login_at    TEXT NOT NULL," + 
					"autolock_minutes INTEGER DEFAULT 0," + 
					"hide_passwords   INTEGER DEFAULT 1)");
			
			stmt.addBatch("CREATE TABLE IF NOT EXISTS entries (" + 
					"id         INTEGER PRIMARY KEY," + 
					"salt       BLOB NOT_NULL," +
					"updated_at TEXT NOT NULL," + 
					"title      TEXT NOT NULL," + 
					"username   TEXT," + 
					"password   BLOB NOT NULL," + 
					"url        TEXT," + 
					"favicon    BLOB," + 
					"note       TEXT)");
			
			stmt.executeBatch();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generates 8 random bytes for record salting. 
	 * @return byte[8]
	 */
	public static byte[] newSalt() {
		byte[] s = new byte[8];
		
		Random rand = new Random();
		rand.nextBytes(s);
		
		return s;
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
