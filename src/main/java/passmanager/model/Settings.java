package passmanager.model;

import java.util.Date;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Reflects the 'settings' table
<<<<<<< HEAD
<<<<<<< HEAD
 * All setter functions return true or false, depending on success or failure.
=======
>>>>>>> develop
=======
>>>>>>> develop
 */
@DatabaseTable(tableName = "settings")
public class Settings {

	@DatabaseField(generatedId = true)
	int id;
	
	@DatabaseField(columnName = "password", dataType = DataType.BYTE_ARRAY)
	byte[] password;
	
	@DatabaseField(columnName = "updated_at", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd'T'HH:mm:ss")
	Date updated_at;
	
	@DatabaseField(columnName = "last_login_at", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd'T'HH:mm:ss")
	Date last_login_at;
	
	@DatabaseField(columnName = "autolock", dataType = DataType.BOOLEAN)
	boolean autolock;
	
	@DatabaseField(columnName = "autolock_minutes", defaultValue = "0")
	int autolock_minutes;
	
	@DatabaseField(columnName = "hide_passwords", dataType = DataType.BOOLEAN)
	boolean hide_passwords;
	
	public Settings() {
		/* Intentionally left blank */
	}
	
	/**
	 * Getter for 'id'
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter for 'id'
	 * @param int id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for 'password'
	 * @return byte[]
	 */
	public byte[] getPassword() {
		return password;
	}
	
	/**
	 * Setter for 'password'
	 * @param byte[] password
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	/**
	 * Getter for 'updated_at'
	 * @return Date 
	 */
	public Date getUpdatedAt() {
		return updated_at;
	}
	
	/**
	 * Setter for 'updated_at'
	 * @param Date updated_at
	 */
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = (updated_at == null) ? new Date() : updated_at;
	}
	
	/**
	 * Getter for 'last_login_at'
	 * @return Date
	 */
	public Date getLastLoginAt() {
		return last_login_at;
	}
	
	/**
	 * Setter for 'last_login_at'
	 * @param Date last_login_at
	 */
	public void setLastLoginAt(Date last_login_at) {
		this.last_login_at = (last_login_at == null) ? new Date() : last_login_at;
	}
	
	/**
	 * Getter for 'autolock'
	 * @return boolean
	 */
	public boolean isAutolock() {
		return autolock;
	}
	
	/**
	 * Setter for 'autolock'
	 * @param boolean autolock
	 */
	public void setAutolock(boolean autolock) {
		this.autolock = autolock;
	}
	
	/**
	 * Getter for 'autolock_minutes'
	 * @return int 
	 */
	public int getAutolockMinutes() {
		return autolock_minutes;
	}
	
	/**
	 * Setter for 'autolock_minutes'
	 * @param int autolock_minutes
	 */
	public void setAutolockMinutes(int autolock_minutes) {
		this.autolock_minutes = autolock_minutes;
	}
	
	/**
	 * Getter for 'hide_passwords'
	 * @return the hide_passwords
	 */
	public boolean isHidePasswords() {
		return hide_passwords;
	}
	
	/**
	 * Setter for 'hide_passwords'
	 * @param boolean hide_passwords
	 */
	public void setHidePasswords(boolean hide_passwords) {
		this.hide_passwords = hide_passwords;
	}
	
}
