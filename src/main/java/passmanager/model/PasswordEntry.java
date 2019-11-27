package passmanager.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.scene.image.Image;

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
	
	@DatabaseField(columnName = "updated_at", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd'T'HH:mm:ss")
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
		setFavicon(null);
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
	 * Getter for 'salt'
	 * @return byte[]
	 */
	public byte[] getSalt() {
		return salt;
	}
	
	/**
	 * Setter for 'salt'
	 * Passing null as the parameter will generate bytes for you.
	 * @param byte[] salt
	 */
	public void setSalt(byte[] salt) {
		if (salt == null) {
			new Random().nextBytes(this.salt);
		}
		else {
			this.salt = salt;
		}
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
	 * Passing null as the parameter will use the current timestamp.
	 * @param Date updated_at
	 */
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = (updated_at == null) ? new Date() : updated_at;
	}
	
	/**
	 * Getter for 'title'
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for 'title'
	 * @param String title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for 'username'
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setter for 'username'
	 * @param String username
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * Getter for 'url'
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Setter for 'url'
	 * @param String url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Getter for 'favicon'
	 * @return Image 
	 */
	public Image getFavicon() {
		if (favicon == null) {
			return null;
		}
		
		return new Image(new ByteArrayInputStream(favicon));
	}
	
	/**
	 * Setter for 'favicon'
	 * Passing null as the parameter will attempt to fetch one from the web.
	 * @param byte[] favicon
	 */
	public void setFavicon(byte[] favicon) {
		if (favicon != null) {
			this.favicon = favicon;
			return;
		}
		
		int n = 0;
		byte[] tmpBuffer = new byte[2048];
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		/* fetching a favicon image */
		try {
			URL imgUrl = new URL("https://icon.ptmx.dev/icon?size=50&url=" + url);
			
			try (InputStream is = imgUrl.openStream()) {
			
				while ( (n = is.read(tmpBuffer)) > 0 ) {
					output.write(tmpBuffer, 0, n);
				}
				
				output.flush();
			}
			
			this.favicon = output.toByteArray();
		}
		catch (IOException e) {
			/* favicon wasn't found, not connected to the internet, etc. 
			 * We don't store the default one in the database, since it would just be wasted space. 
			 * A null value also makes it easy to retry later. */
			this.favicon = null;
		}
	}
	
	/**
	 * Setter for 'note'
	 * @return String
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Getter for 'note'
	 * @param String note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * ORM Required
	 */
	@Override
	public int hashCode() {
		return title.hashCode();
	}
	
	/**
	 * ORM Required
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		
		return id == ((PasswordEntry) other).getId();
	}

}
