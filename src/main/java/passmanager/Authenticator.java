package passmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.Arrays;
import javafx.scene.control.TextInputControl;
import java.nio.charset.Charset;

public class Authenticator {
	private final static Logger Log = Util.getLogger(Authenticator.class);
	
	private Settings settings = new Settings();
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
	 * Attempts to login with the given credential. Returns a boolean value depending on if it's valid.
	 * @return boolean
	 */
	public boolean login() {
		if (Arrays.equals(Util.sha256(password), settings.getPassword())) {
			return true;
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
			
			/* all of this could probably be moved into the Settings model */
			try (PreparedStatement stmt = db.prepareStatement("INSERT INTO settings (password, updated_at, last_login_at) VALUES (?, datetime(), datetime())")) {
				stmt.setBytes(1, Util.sha256(this.password));
				return stmt.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
