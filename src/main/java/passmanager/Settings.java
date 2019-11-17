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
	 * A generic update method. Also probably a bad idea.
	 * @param columnName		name of the column that needs updating
	 * @param value				new value for that column
	 * @return boolean
	 */
	private <T> boolean update(String columnName, T value) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET " + columnName + "=?")) {
				stmt.setObject(1, value);
				
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Setter for 'hide_passwords'
	 * @param newValue
	 * @return boolean
	 */
	public boolean setHidePasswords(boolean newValue) {
		return this.<Boolean>update("hide_passwords", newValue);
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

		return this.<Integer>update("autolock_minutes", minutes);
	}
	
	/**
	 * Getter for 'autolock_minutes'
	 * Returns 0 for disabled autolock.
	 * @return int
	 */
	public int getAutolockMins() {
		return this.<Integer>select("autolock_minutes");
	}
	
	/**
	 * Setter for 'autolock'
	 * @return boolean
	 */
	public boolean setAutolock(boolean newValue) {
		return this.<Boolean>update("autolock", newValue);
	}
	
	/**
	 * Getter for 'autolock'
	 * @return boolean
	 */
	public boolean getAutolock() {
		return this.<Integer>select("autolock") > 0;
	}
	
	/**
	 * Setter for 'password'
	 * Returns true or false, depending on the update result.
	 * @param newPassword
	 * @return	boolean		
	 */
	public boolean setPassword(String newPassword) {
		byte[] newBytes = Util.sha256(newPassword.getBytes());
		
		return this.<byte[]>update("password", newBytes);
	}
	
	/**
	 * Gets the hashed password. Not very useful unless unlocking.
	 * @return byte[]		hashed password
	 */
	public byte[] getPassword() {
		return this.<byte[]>select("password");
	}
	
	/**
	 * Setter for 'last_login_at'
	 * @param date			null for default value
	 * @return boolean
	 */
	public boolean setLastLogin(Date date) {
		return this.<Date>update("last_login_at", date);
	}

	/**
	 * Getter for 'last_login_at'
	 * @return Date
	 */
	public Date getLastLogin() {
		return (Date) this.<java.sql.Date>select("last_login_at");
	}

}
