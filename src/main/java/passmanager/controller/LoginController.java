package passmanager.controller;

import java.util.logging.Logger;
import passmanager.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Handles the login process
 */
public class LoginController extends BaseController {
	
	private final static Logger Log = Util.getLogger(LoginController.class);
	
	@FXML private Button unlock;
	@FXML private PasswordField password;
	@FXML private Label errorMsg;
	
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
		loadScene("/layout/listview.fxml");
	}
}
