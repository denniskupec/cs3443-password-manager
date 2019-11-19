package passmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Reflects the 'entries' table.
 * All setter methods return true on success or false on failure.
 */
public class PasswordEntry {
	private final static Logger Log = Util.getLogger(PasswordEntry.class);
	
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
		return (Date) this.<java.sql.Date>select("updated_at");
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
	
	
	/**
	 * A generic select method. Not type safe and is probably a bad idea.
	 * Works for this use case.
	 * @param columnName - name of the column to fetch
	 * @return T - Type depends on the database column type
	 */
	@SuppressWarnings("unchecked")
	private <T> T select(String columnName) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("SELECT " + columnName + " FROM entries WHERE id=?")) {
				stmt.setInt(1, id);
				
				ResultSet rs = stmt.getResultSet();
				return (T) rs.getObject(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * A generic update method. Also probably a bad idea, but this is type checked.
	 * @return boolean - True on success, false on failure.
	 */
	private <T> boolean update(String columnName, T value) {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE entries SET " + columnName + "=? WHERE id=?")) {
				stmt.setObject(1, value);
				stmt.setInt(2, id);
				
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
