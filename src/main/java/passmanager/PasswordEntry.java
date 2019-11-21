package passmanager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 * Reflects the 'entries' table.
 * All setter methods return true on success or false on failure.
 */
public class PasswordEntry extends BaseModel {
	private final static Logger Log = Util.getLogger(PasswordEntry.class);
	private final String tableName = "entries";
	
	/**
	 * Row ID this instance represents.
	 */
	private int id;
	
	/**
	 * Constructor
	 * @param id		entry ID this instance represents
	 */
	public PasswordEntry(int id) {
		this.id = id;
	}
	
	/**
	 * Constructor to create a new entry.
	 * @param title
	 * @param username
	 * @param password
	 * @param url
	 * @param note
	 */
	public PasswordEntry(String title, String username, byte[] password, String url, String note) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("INSERT INTO entries (title, username, password, url, note) VALUES (?, ?, ?, ?, ?)")) {
				stmt.setString(1, title);
				stmt.setString(2, username);
				stmt.setBytes(3, Util.sha256(password));
				stmt.setString(4, url);
				stmt.setString(5, note);
				
				//TODO: what happens when this fails? exception or error message?
				if (stmt.executeUpdate() == 0) {
					Log.warning("DB entry returned 0");
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the first entry in the database, or returns null on error
	 * @return PasswordEntry
	 */
	public static PasswordEntry first() {
		try (Connection db = Database.connect()) {
			try (ResultSet rs = db.createStatement().executeQuery("SELECT id FROM entries ORDER BY ASC id LIMIT 1")) {
				return new PasswordEntry(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Gets then next entry ID, and returns a new PasswordEntry object. If it doesn't exist, null is returned.
	 * @return PasswordEntry
	 */
	public PasswordEntry next() {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("SELECT id FROM entries WHERE id>? ORDER BY ASC id LIMIT 1")) {
				stmt.setInt(1, id);
				
				if (!stmt.execute()) {
					return null;
				}
				
				ResultSet rs = stmt.getResultSet();
				return new PasswordEntry(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Gets the previous entry ID, and returns a new PasswordEntry object. If it doesn't exist, null is returned.
	 * @return PasswordEntry
	 */
	public PasswordEntry previous() {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("SELECT id FROM entries WHERE id<? ORDER BY id DESC LIMIT 1")) {
				stmt.setInt(1, id);
				
				if (!stmt.execute()) {
					return null;
				}
				
				ResultSet rs = stmt.getResultSet();
				return new PasswordEntry(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Getter for 'id'
	 * There is no setter for this field.
	 * @return int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for 'salt'
	 * There is no setter for this field.
	 * @return Byte[8]
	 */
	public Byte[] getSalt() {
		return this.<Byte[]>select("salt");
	}
	
	/**
	 * Getter for 'updated_at'
	 * @return Date
	 */
	public Date getUpdatedAt() {
		return Database.parseDate( this.<String>select("updated_at") );
	}
	
	/**
	 * Getter for 'title'
	 * @return String
	 */
	public String getTitle() {
		return this.<String>select("title");
	}
	
	/**
	 * Setter for 'title'
	 * @param newTitle
	 * @return boolean
	 */
	public boolean setTitle(String newTitle) {
		return this.<String>update("title", newTitle);
	}
	
	/**
	 * Getter for 'username'
	 * @return String
	 */
	public String getUsername() {
		return this.<String>select("username");
	}
	
	/**
	 * Setter for 'username'
	 * @param newTitle
	 * @return boolean
	 */
	public boolean setUsername(String newUsername) {
		return this.<String>update("username", newUsername);
	}
	
	
	/**
	 * Getter for 'password'
	 * @return Byte[]
	 */
	public Byte[] getPassword() {
		return this.<Byte[]>select("password");
	}
	
	/**
	 * Setter for 'password'
	 * @param newPassword
	 * @return boolean - True on success, false on failure.
	 */
	public boolean setPassword(Byte[] newPassword) {
		return this.<Byte[]>update("password", newPassword);
	}
	
	/**
	 * Getter for 'url'
	 * @return String
	 */
	public String getUrl() {
		return this.<String>select("url");
	}
	
	/**
	 * Setter for 'url'
	 * @param newUrl
	 * @return Boolean - True on success, false on failure.
	 */
	public boolean setUrl(String newUrl) {
		return this.<String>update("url", newUrl);
	}
	
	/**
	 * Gets the favicon image from the database. Returns null on failure.
	 * @return Image
	 */
	public Image getFavicon() {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("SELECT favicon FROM entries WHERE id=? LIMIT 1")) {
				stmt.setInt(1, id);
				
				if (!stmt.execute()) {
					return null;
				}
				
				ResultSet rs = stmt.getResultSet();
				
				InputStream is = rs.getBinaryStream(1);
				if (rs.wasNull()) {
					//TODO: use a default "question mark" image for an empty favicon
					return null;
				}
				
				return new Image(is);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Fetches a good favicon from the internet, and saves it in the database.
	 * @return boolean - True on success, false on failure.
	 */
	public boolean setFavicon(URL url) {
		try {
			URL web = new URL("https://icon.ptmx.dev/icon?url=" + url.getHost() + "&size=50");
			try (
				InputStream is = web.openStream();
				Connection db = Database.connect();
				PreparedStatement stmt = db.prepareStatement("UPDATE entries SET favicon=? WHERE id=?");
				) {
				
				stmt.setBinaryStream(1, is);
				stmt.setInt(2, id);
				
				if (!stmt.execute()) {
					return false;
				}
				
				return true;
			}
		}
		catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Getter for 'note'
	 * @return String
	 */
	public String getNote() {
		return this.<String>select("note");
	}
	
	/**
	 * Setter for 'note
	 * @param newNote
	 * @return boolean - True on success, false on failure.
	 */
	public boolean setNote(String newNote) {
		return this.<String>update("note", newNote);
	}
	
}
