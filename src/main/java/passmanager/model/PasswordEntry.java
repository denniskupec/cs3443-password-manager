package passmanager.model;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javafx.scene.image.Image;
import passmanager.Util;

/**
 * Reflects the 'entries' table.
 * All setter methods return true on success or false on failure.
 */
@DatabaseTable(tableName = "entries")
public class PasswordEntry {

	@DatabaseField(generatedId = true)
	int id;
	
	@DatabaseField(columnName = "salt", dataType = DataType.BYTE_ARRAY)
	byte[] salt;
	
	@DatabaseField(columnName = "updated_at")
	Date updated_at;
	
	@DatabaseField(columnName = "title")
	String title;
	
	@DatabaseField(columnName = "username")
	String username;
	
	@DatabaseField(columnName = "password", dataType = DataType.BYTE_ARRAY)
	byte[] password;
	
	@DatabaseField(columnName = "url")
	String url;
	
	@DatabaseField(columnName = "favicon", dataType = DataType.BYTE_ARRAY, canBeNull = true)
	byte[] favicon;
	
	@DatabaseField(columnName = "note", dataType = DataType.LONG_STRING, canBeNull = true)
	String note;
	
	/**
	 * Default constructor. Required for ORM. Intentionally does nothing.
	 */
	public PasswordEntry() {
		// intentionally left blank
	}
	
	/**
	 * Constructor to conveniently add a new entry. 
	 * @param title
	 * @param username
	 * @param password
	 * @param url
	 * @param note
	 */
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
		if (salt == null) {
			new Random().nextBytes(this.salt);
		}
		else {
			this.salt = salt;
		}
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
	
	public Image getFavicon() {
		if (favicon == null) {
			return null;
		}
		
		return new Image(new ByteArrayInputStream(favicon));
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
	
	
	@Override
	public int hashCode() {
		return title.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		
		return id == ((PasswordEntry) other).getId();
	}
}
