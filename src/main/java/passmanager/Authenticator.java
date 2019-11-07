package passmanager;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticator {
	
	private final static Logger Log = Util.getLogger(Authenticator.class);
	
	/**
	 * true = authenticated
	 * false = otherwise
	 */
	private boolean status = false;
	
	private byte[] password;

	Authenticator(String password) {
		this.password = password.getBytes(Charset.forName("UTF-8"));
	}
	
	Authenticator(byte[] password) {
		this.password = password;
	}
	
	/**
	 * true = success, false = invalid credential
	 * @return boolean
	 */
	public boolean login() {
		try (Connection db = Database.connect()) {
			Blob pb = db.createBlob();
				pb.setBytes(1, password);

			PreparedStatement stmt = db.prepareStatement("UPDATE settings SET last_login_at=datetime() WHERE password=?");
				stmt.setBlob(1, pb);
				stmt.execute();
				
			return stmt.getUpdateCount() > 0;
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void logout() {
		this.status = false;
	}
	
	/**
	 * Attempts to register a new user with the given credential
	 * @return boolean		true = success, false = failure (already exists, etc.)
	 */
	public boolean register() {
		
		try (Connection db = Database.connect()) {
			ResultSet rs = db.createStatement().executeQuery("SELECT COUNT() FROM settings LIMIT 1");
			
			if (rs.getInt(0) > 0) {
				// TODO: better handling of this error
				return false;
			}
			
			Blob pb = db.createBlob();
				pb.setBytes(1, this.sha256(this.password));
			
			PreparedStatement stmt = db.prepareStatement("INSERT INTO settings (password, updated_at, last_login_at) VALUES (?, datetime(), datetime())");
				stmt.setBlob(1, pb);
				stmt.execute();
				
			if (stmt.getUpdateCount() != 1) {
				Log.log(Level.SEVERE, "Failed to insert new row.");
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	/**
	 * true = authenticated, false = otherwise
	 * @return boolean
	 */
	public boolean getStatus() {
		return this.status;
	}
	
	/**
	 * @param newPassword
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword.getBytes();
	}
	
	/**
	 * @param newPassword
	 */
	public void setPassword(byte[] newPassword) {
		this.password = newPassword;
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
