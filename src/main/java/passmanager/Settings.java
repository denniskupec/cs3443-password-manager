package passmanager;

import java.util.logging.Logger;

/**
 * Reflects the 'settings' table in the database.
 */
public class Settings {
	private final static Logger Log = Util.getLogger(Settings.class);
	
	
	public void setAutolock(boolean newValue) {
		
	}
	
	public boolean getAutolock() {
		return false;
	}
	
	
	public void setAutolockMins(int minutes) {
		
	}
	
	public int getAutolockMins() {
		return 0;
	}
	
	
	public void setPassword(String newPassword) {
		
	}
	
	public String getPassword() {
		return "";
	}
	
	
	
	

}
