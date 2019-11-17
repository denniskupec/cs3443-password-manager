package passmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Reflects the 'settings' table
 */
public class Settings {
	private final static Logger Log = Util.getLogger(Settings.class);
	
	/**
	 * A generic select method. Not type safe and is probably a bad idea.
	 * Works for this use case.
	 * @param columnName	name of the column to fetch
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	private <T> T select(String columnName) {
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("SELECT " + columnName + " FROM settings");
			return (T) rs.getObject(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Setter for 'hide_passwords'
	 * @param newValue
	 * @return boolean
	 */
	public boolean setHidePasswords(boolean newValue) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET hide_passwords=?")) {
				stmt.setBoolean(1, newValue);
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Getter for 'hide_passwords'
	 * @return boolean
	 */
	public boolean getHidePasswords() {
		return this.<Integer>select("hide_passwords") > 0;
	}

	/**
	 * Setter for 'autolock_minutes'
	 * @param minutes
	 * @return boolean
	 */
	public boolean setAutolockMins(int minutes) {
		if (minutes < 0) {
			minutes = 0;
		}
		
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET autolock_minutes=?")) {
				stmt.setInt(1, minutes);
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Getter for 'autolock_minutes'
	 * Returns 0 for disabled autolock.
	 * @return int
	 */
	public int getAutolockMins() {
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("SELECT autolock_minutes FROM settings");
			return rs.getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Setter for 'autolock'
	 * @return boolean
	 */
	public boolean setAutolock(boolean newValue) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET autolock=?")) {
				stmt.setBoolean(1, newValue);
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Getter for 'autolock'
	 * @return boolean
	 */
	public boolean getAutolock() {
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("SELECT autolock FROM settings");
			return rs.getBoolean(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Setter for 'password'
	 * Returns true or false, depending on the update result.
	 * @param newPassword
	 * @return	boolean		
	 */
	public boolean setPassword(String newPassword) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET password=?, updated_at=datetime()")) {
				stmt.setBytes(1, Util.sha256(newPassword.getBytes()));
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Gets the hashed password. Not very useful unless unlocking.
	 * @return byte[]		hashed password
	 */
	public byte[] getPassword() {
		try (Connection db = Database.connect();) {
			ResultSet rs = db.createStatement().executeQuery("SELECT password FROM settings");
			return rs.getBytes(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Setter for 'last_login_at'
	 * @param date			null for default value
	 * @return boolean
	 */
	public boolean setLastLogin(Date date) {
		if (date == null) {
			date = new Date();
		}

		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET last_login_at=?")) {
				stmt.setDate(1, (java.sql.Date) date);
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Getter for 'last_login_at'
	 * @return Date
	 */
	public Date getLastLogin() {
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("SELECT last_login_at FROM settings");
			return (Date) rs.getDate(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
