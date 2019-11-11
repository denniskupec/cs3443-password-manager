package passmanager.controller;

import java.util.logging.Logger;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.fxml.*;
import passmanager.*;

public class FirstRunController {
	
	private final static Logger Log = Util.getLogger(FirstRunController.class);
	
	@FXML PasswordField password;
	@FXML PasswordField passwordConfirm;
	@FXML Button save;
	@FXML Label errorMsg;

	@FXML
	public void onSave(Event event) {
		Log.fine("Saving new password.");
		
		errorMsg.setVisible(false);
		
		if (!password.getText().equals(passwordConfirm.getText())) {
			errorMsg.setText("Both passwords do not match. It is case-sensitive.");
			errorMsg.setVisible(true);
			return;
		}
		
		if (password.getText().isEmpty()) {
			errorMsg.setText("Enter a password above.");
			errorMsg.setVisible(true);
			return;
		}
		
		Authenticator auth = new Authenticator(password.getText());
		
		if (!auth.register()) {
			errorMsg.setText("Error encountered while saving. Try again.");
			errorMsg.setVisible(true);
			return;
		}
	}
}
