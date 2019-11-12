package passmanager.controller;

import java.util.logging.Logger;
import passmanager.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController extends BaseController {
	
	private final static Logger Log = Util.getLogger(LoginController.class);
	
	@FXML private Button unlock;
	@FXML private PasswordField password;
	@FXML private Label errorMsg;
	
	@FXML
	public void doLogin(Event event) {
		errorMsg.setVisible(false);
		
		if (password.getText().isEmpty()) {
			password.setText("");
			errorMsg.setText("Enter your password to unlock.");
			errorMsg.setVisible(true);
			return;
		}
		
		Authenticator auth = new Authenticator(password.getText());
		if (!auth.login()) {
			password.setText("");
			errorMsg.setText("Invalid password. Try again.");
			errorMsg.setVisible(true);
		}

		if (auth.login()) {
			Log.info("Authenticated!");
		
			loadScene("/layout/listview.fxml");
		}
	}
	
}
