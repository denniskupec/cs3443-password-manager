package passmanager;

<<<<<<< HEAD
import passmanager.model.Settings;
import java.util.Arrays;
import java.nio.charset.Charset;
import java.sql.SQLException;
=======
import java.util.Arrays;
import java.nio.charset.Charset;
import java.sql.SQLException;
import passmanager.model.Settings;
>>>>>>> develop

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
<<<<<<< HEAD
	 * Attempts to login with the given credential. Returns a boolean value depending on if it's valid.
=======
	 * Attempts to verify the given login password. Returns FALSE if the password is incorrect.
>>>>>>> develop
	 * @return boolean
	 */
	public boolean login() {	
		return Arrays.equals(password, settings.getPassword());
	}
	
	/**
<<<<<<< HEAD
	 * Attempts to register a new user with the given credential
=======
	 * Attempts to register a new master password with the one given. 
>>>>>>> develop
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
