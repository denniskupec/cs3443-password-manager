package passmanager.controller;

import java.sql.SQLException;
import java.util.logging.Logger;
import com.j256.ormlite.dao.Dao;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import passmanager.*;
import passmanager.interfaces.Initializable;
import passmanager.model.Settings;

/**
 * Handles the login process
 */
public class LoginController extends BaseController implements Initializable {
	private final static Logger Log = Util.getLogger(LoginController.class);
	
	@FXML Button unlock;
	@FXML PasswordField password;
	@FXML Label errorMsg;
	
	Dao<Settings, Integer> settingsDao = Database.getDao(Settings.class);
	Settings settings;
	
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		try {
			settings = settingsDao.queryForId(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Routine for when the unlock button is pressed. It tries to authenticate the user, then change the scene.
	 * @param event
	 */
	@FXML
	public void doLogin(Event event) {
		errorMsg.setVisible(false);
		
		// password field must have something entered
		if (password.getText().isEmpty()) {
			password.setText("");
			errorMsg.setText("Enter your password to unlock.");
			errorMsg.setVisible(true);
			return;
		}

		// attempt to login, or show an error on failure
		Authenticator auth = new Authenticator(password.getText());
		if (!auth.login()) {
			password.setText("");
			errorMsg.setText("Invalid password. Try again.");
			errorMsg.setVisible(true);
			return;
		}
		
		// only runs if login was successful
		Log.info("Authenticated!");
		loadScene("/layout/Index.fxml");
	}
}
