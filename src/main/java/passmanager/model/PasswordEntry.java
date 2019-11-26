package passmanager.model;

import java.util.Date;
import java.util.Random;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import passmanager.Util;

/**
 * Reflects the 'entries' table.
 * All setter methods return true on success or false on failure.
 */
@DatabaseTable(tableName = "entries")
public class PasswordEntry {

	@DatabaseField(generatedId = true)
	int id;
	
	@DatabaseField(columnName = "salt", dataType=DataType.BYTE_ARRAY)
	byte[] salt;
	
	@DatabaseField(columnName = "updated_at")
	Date updated_at;
	
	@DatabaseField(columnName = "title")
	String title;
	
	@DatabaseField(columnName = "username")
	String username;
	
	@DatabaseField(columnName = "password", dataType=DataType.BYTE_ARRAY)
	byte[] password;
	
	@DatabaseField(columnName = "url")
	String url;
	
	@DatabaseField(columnName = "favicon", dataType=DataType.BYTE_ARRAY)
	byte[] favicon;
	
	@DatabaseField(columnName = "note")
	String note;
	
	public PasswordEntry() {
		// intentionally left blank
	}
	
	public PasswordEntry(String title, String username, byte[] password, String url, String note) {
		this.title = title;
		this.username = username;
		this.url = url;
		
		if (note != null) {
			this.note = note;
		}
		
		setPassword(password);
		setSalt(null);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
	public void setSalt(byte[] salt) {
		Random rand = new Random();
		rand.nextBytes(this.salt);
	}
	
	public Date getUpdatedAt() {
		return updated_at;
	}
	
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = (updated_at == null) ? new Date() : updated_at;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(byte[] password) {
		this.password = Util.sha256(password);
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public byte[] getFavicon() {
		return favicon;
	}
	
	public void setFavicon(byte[] favicon) {
		this.favicon = favicon;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
