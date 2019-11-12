package passmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javafx.scene.control.TextInputControl;
import java.util.logging.Level;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticator {
	
	private final static Logger Log = Util.getLogger(Authenticator.class);
	
	private byte[] password;

	public Authenticator(String password) {
		this.password = password.getBytes(Charset.forName("UTF-8"));
	}
	
	public Authenticator(byte[] password) {
		this.password = password;
	}
	
	public Authenticator(TextInputControl input) {
		this.password = input.getText().getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	 * true = success, false = invalid credential
	 * @return boolean
	 */
	public boolean login() {
		try (Connection db = Database.connect()) {
			try (PreparedStatement stmt = db.prepareStatement("UPDATE settings SET last_login_at=datetime() WHERE password=?")) {
				stmt.setBytes(1, this.sha256(this.password));
				stmt.execute();
				
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void logout() {
		/* TODO: Need to cleanup and switch views back to the login screen. 
		 *	If not that, then just close the application when the user logs off. ¯\_(ツ)_/¯
		 */
	}
	
	/**
	 * Attempts to register a new user with the given credential
	 * @return boolean		true = success, false = failure (already exists, etc.)
	 */
	public boolean register() {
		try (Connection db = Database.connect()) {
			try (ResultSet rs = db.createStatement().executeQuery("SELECT COUNT(*) AS rowcount FROM settings")) {
				if (rs.next() && rs.getInt("rowcount") > 0) {
					return false;
				}
			}
			
			try (PreparedStatement stmt = db.prepareStatement("INSERT INTO settings (password, updated_at, last_login_at) VALUES (?, datetime(), datetime())")) {
				stmt.setBytes(1, this.sha256(this.password));
				stmt.execute();
				
				if (stmt.getUpdateCount() != 1) {
					return false;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param newPassword
	 */
	public boolean setPassword(String newPassword) {
		return this.setPassword(newPassword.getBytes());
	}
	
	/**
	 * @param newPassword
	 */
	public boolean setPassword(byte[] newPassword) {
		this.password = newPassword;
		
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("UPDATE settings SET password=?, updated_at=datetime() WHERE ");
			
			if (rs.next() && rs.getInt("rowcount") > 0) {
				// TODO: better handling of this error
				Log.warning("Tried to register a new user, but one already exists!");
				return false;
			}
			
			PreparedStatement stmt = db.prepareStatement("INSERT INTO settings (password, updated_at, last_login_at) VALUES (?, datetime(), datetime())");
				stmt.setBytes(1,  this.sha256(this.password));
				stmt.execute();
				
			if (stmt.getUpdateCount() != 1) {
				Log.log(Level.SEVERE, "Failed to update credentials.");
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
	
	/**
	 * Calculates the SHA256 sum to obfuscate the password in the database.
	 * @param input			array of bytes
	 * @return byte[]
	 */
	private byte[] sha256(byte[] input) {
		byte[] result = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(input);
			
			result = md.digest();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
