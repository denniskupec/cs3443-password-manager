package passmanager.model;

import java.util.Date;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Reflects the 'settings' table
 * All setter functions return true or false, depending on success or failure.
 */
@DatabaseTable(tableName = "settings")
public class Settings {

	@DatabaseField(generatedId = true)
	int id;
	
	@DatabaseField(columnName = "password", dataType = DataType.BYTE_ARRAY)
	byte[] password;
	
	@DatabaseField(columnName = "updated_at")
	Date updated_at;
	
	@DatabaseField(columnName = "last_login_at")
	Date last_login_at;
	
	@DatabaseField(columnName = "autolock", dataType = DataType.BOOLEAN)
	boolean autolock;
	
	@DatabaseField(columnName = "autolock_minutes")
	int autolock_minutes;
	
	@DatabaseField(columnName = "hide_passwords", dataType = DataType.BOOLEAN)
	boolean hide_passwords;
	
	public Settings() {
		// intentionally left blank
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id - the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the password
	 */
	public byte[] getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	/**
	 * @return the updated_at
	 */
	public Date getUpdatedAt() {
		return updated_at;
	}
	
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = (updated_at == null) ? new Date() : updated_at;
	}
	
	/**
	 * @return the last_login_at
	 */
	public Date getLastLoginAt() {
		return last_login_at;
	}
	
	/**
	 * @param last_login_at the last_login_at to set
	 */
	public void setLastLoginAt(Date last_login_at) {
		this.last_login_at = (last_login_at == null) ? new Date() : last_login_at;
	}
	
	/**
	 * @return the autolock
	 */
	public boolean isAutolock() {
		return autolock;
	}
	
	/**
	 * @param autolock the autolock to set
	 */
	public void setAutolock(boolean autolock) {
		this.autolock = autolock;
	}
	
	/**
	 * @return the autolock_minutes
	 */
	public int getAutolockMinutes() {
		return autolock_minutes;
	}
	
	/**
	 * @param autolock_minutes the autolock_minutes to set
	 */
	public void setAutolockMinutes(int autolock_minutes) {
		this.autolock_minutes = autolock_minutes;
	}
	
	/**
	 * @return the hide_passwords
	 */
	public boolean isHidePasswords() {
		return hide_passwords;
	}
	
	/**
	 * @param hide_passwords the hide_passwords to set
	 */
	public void setHidePasswords(boolean hide_passwords) {
		this.hide_passwords = hide_passwords;
	}
	
}
