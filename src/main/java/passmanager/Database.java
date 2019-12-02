package passmanager;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
>>>>>>> develop
import java.sql.SQLException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import passmanager.model.*;

/**
 * Mostly a helper class to simplify database actions. Also handles initial creation.
 */
public class Database {

	static ConnectionSource connectionSource;

	/**
	 * @return ConnectionSource
	 */
	public static ConnectionSource getSource() {
		return connectionSource;
<<<<<<< HEAD
	}
	
	/*
	 * Helper method which returns a new DAO.
	 */
	public static <T,ID> Dao<T,ID> getDao(Class<T> clazz) {
		try {
			return DaoManager.createDao(connectionSource, clazz);
=======
	}
	
	/*
	 * Helper method which returns a new DAO.
	 */
	public static <T,ID> Dao<T,ID> getDao(Class<T> clazz) {
		try {
			return DaoManager.createDao(connectionSource, clazz);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Used to initialize the sqlite database with the proper schema.
	 * @throws SQLException 
	 */
	public static void setup() throws SQLException {
		connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + Util.getStoragePath("storage.sqlite3"));

		TableUtils.createTableIfNotExists(connectionSource, Settings.class);
		TableUtils.createTableIfNotExists(connectionSource, PasswordEntry.class);
	}
	
	/**
	 * Copies a premade demo database resource instead of creating a fresh one. 
	 */
	public static void setupDemo() {
		try (InputStream resource = App.class.getResourceAsStream("/storage.sqlite3"))  {
			Files.copy(resource, Util.getStoragePath("storage.sqlite3"), StandardCopyOption.REPLACE_EXISTING);
			Database.setup();
>>>>>>> develop
		}
		catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}

	/**
<<<<<<< HEAD
	 * Used to initialize the sqlite database with the proper schema.
	 * @throws SQLException 
	 */
	public static void setup() throws SQLException {
		connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + Util.getStoragePath("storage.sqlite3"));

		TableUtils.createTableIfNotExists(connectionSource, Settings.class);
		TableUtils.createTableIfNotExists(connectionSource, PasswordEntry.class);
	}

	/**
	 * Only checks if the database was copied already. 
	 * true = exists, false = doesn't exist
=======
	 * Only checks if the database file exists on disk. 
>>>>>>> develop
	 * @return boolean
	 */
	public static boolean exists() {
		return Util.getStoragePath("storage.sqlite3").toFile().exists();
	}
	
	/**
	 * Deletes the database from the disk. Don't use this, unless you're doing tests or something...
	 * @return boolean
	 */
	public static boolean deleteFromDisk() {
		return Util.getStoragePath("storage.sqlite3").toFile().delete();
	}

	/**
	 * Closes the connection.
	 */
	public static void close() {
		try {
			connectionSource.close();
		} 
		catch (IOException e) {
<<<<<<< HEAD
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			return;
=======
			throw new RuntimeException(e);
		}
		catch (NullPointerException e) {
			/* intentionally left blank */
>>>>>>> develop
		}
	}

}
