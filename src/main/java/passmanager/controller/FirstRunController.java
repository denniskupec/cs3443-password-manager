package passmanager.controller;

import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.fxml.*;
import passmanager.*;
import passmanager.interfaces.Initializable;

/**
 * Handles the first launch of the application, and in cases where the database doesnt exist.
 */
public class FirstRunController extends BaseController implements Initializable {
	private final static Logger Log = Util.getLogger(FirstRunController.class);
	
	@FXML PasswordField password;
	@FXML PasswordField passwordConfirm;
	@FXML Button save;
	@FXML Label errorMsg;
	
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		password.textProperty().addListener(this::onTextChanged);
		passwordConfirm.textProperty().addListener(this::onTextChanged);
	}

	/**
	 * Runs when the save button is pressed
	 * @param event
	 */
	@FXML
	public void onSave(Event event) {
		Log.fine("Saving new password.");
		
		errorMsg.setVisible(false);
		
		if (!password.getText().equals(passwordConfirm.getText())) {
			errorMsg.setText("Both passwords do not match. They are case-sensitive.");
			errorMsg.setVisible(true);
			return;
		}
		
		Authenticator auth = new Authenticator(password.getText());
		auth.register();
		
		loadScene("/layout/login.fxml");
	}
	
	/**
	 * Value change listener for both password fields in this view. 
	 * @param observable
	 * @param oldValue
	 * @param newValue
	 */
	private void onTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		if (password.getText().isEmpty() || passwordConfirm.getText().isEmpty()) {
			save.setDisable(true);
			return;
		}
		
		if (!password.getText().equals(passwordConfirm.getText()) && !passwordConfirm.getText().isEmpty()) {
			save.setDisable(true);
			showErrorMessage("Both passwords need to match. They are case-sensitive.");
		}
		
		if (password.getText().equals(passwordConfirm.getText())) {
			showSuccessMessage("Passwords match!");
			save.setDisable(false);
		}
	}
	
	/**
	 * Sets errorMsg text, makes it red, and sets it to visible.
	 * @param message
	 */
	private void showErrorMessage(String message) {
		errorMsg.setTextFill(Color.web("#D70A0A"));
		errorMsg.setText(message);
		errorMsg.setVisible(true);
	}
	
	/**
	 * Sets errorMsg text, makes it green, and sets it to visible.
	 * @param message
	 */
	private void showSuccessMessage(String message) {
		errorMsg.setTextFill(Color.web("#24B424"));
		errorMsg.setText(message);
		errorMsg.setVisible(true);
	}
	
}
