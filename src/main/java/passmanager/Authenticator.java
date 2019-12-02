package passmanager;

import java.util.Arrays;
import java.nio.charset.Charset;
import java.sql.SQLException;
import passmanager.model.Settings;

public class Authenticator {

	byte[] password;
	Settings settings;
	
	public Authenticator(String password) {
		this(password.getBytes(Charset.forName("UTF-8")));
	}
	
	public Authenticator(byte[] password) {
		this.password = Util.sha256(password);
		
		try {
			settings = Database.getDao(Settings.class).queryForId(1);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Attempts to verify the given login password. Returns FALSE if the password is incorrect.
	 * @return boolean
	 */
	public boolean login() {	
		return Arrays.equals(password, settings.getPassword());
	}
	
	/**
	 * Attempts to register a new master password with the one given. 
	 */
	public void register() {
		Settings settings = new Settings();
		settings.setId(1);
		settings.setPassword(password);
		settings.setUpdatedAt(null);
		
		try {			
			Database.getDao(Settings.class).create(settings);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
