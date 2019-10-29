package passmanager;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticator {
	
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
				stmt.setBlob(0, pb);
				
			ResultSet rs = stmt.executeQuery();
			
			return rs.rowUpdated();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void logout() {
		// TODO
	}
	
	public boolean register() {
		// TODO
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
	 * @param String
	 */
	public void setPassword(String newPassword) {
		this.password = newPassword.getBytes();
	}
	
	/**
	 * @param byte[]
	 */
	public void setPassword(byte[] newPassword) {
		this.password = newPassword;
	}
	
	/**
	 * Calculates the SHA256 sum to obfuscate the password in the database.
	 * @param byte[]	array of bytes
	 * @return byte[]
	 */
	private static byte[] sha256(byte[] input) {
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
