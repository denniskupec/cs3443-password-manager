package passmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Reflects the 'entries' table
 */
public class PasswordEntry {
	private final static Logger Log = Util.getLogger(PasswordEntry.class);
	
	private int id;
	
	/**
	 * Constructor
	 * @param id		entry ID this instance represents
	 */
	public PasswordEntry(int id) {
		this.id = id;
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
	
	
	public Byte[] getPassword() {
		return this.<Byte[]>select("password");
	}
	
	public boolean setPassword(Byte[] newPassword) {
		return this.<Byte[]>update("password", newPassword);
	}
	
	public String getUrl() {
		return this.<String>select("url");
	}
	
	public boolean setUrl(String newUrl) {
		return this.<String>update("url", newUrl);
	}
	
	public String getNote() {
		return this.<String>select("note");
	}
	
	public boolean setNote(String newNote) {
		return this.<String>update("note", newNote);
	}
	
	
	/**
	 * A generic select method. Not type safe and is probably a bad idea.
	 * Works for this use case.
	 * @param columnName	name of the column to fetch
	 * @return T
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
	 * A generic update method. Also probably a bad idea.
	 * @return boolean
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
