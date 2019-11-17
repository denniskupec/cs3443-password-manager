package passmanager.controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import passmanager.Settings;
import passmanager.Util;
import passmanager.interfaces.Initializable;
import java.util.logging.Logger;
import java.util.Arrays;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

/**
 * Handles the settings window.
 */
public class SettingsController extends BaseController implements Initializable {
	private final static Logger Log = Util.getLogger(ListController.class);
	
	@FXML CheckBox hidePasswords;
	@FXML CheckBox autolock;
	@FXML HBox autolockMinsBox;
	@FXML TextField autolockMins;
	@FXML PasswordField oldPassword;
	@FXML PasswordField newPassword;
	@FXML PasswordField newPasswordConfirm;
	@FXML Button savePassword;
	@FXML Button cancel;
	@FXML Label errorMsg;
	@FXML Label errorMinutes;
	
	private Settings settings = new Settings();
	
	/**
	 * Automatically called by FXMLLoader
	 */
	@Override
	public void initialize() {
		/* load previously set values and reflect them in the view */
		if (settings.getAutolock()) {
			autolockMins.setText(String.valueOf(settings.getAutolockMins()));
			autolockMinsBox.setVisible(true);
			autolock.setSelected(true);
		}
		 
		hidePasswords.setSelected(settings.getHidePasswords());
		 
		/* register value change listeners and event handlers for inputs */
		autolockMins.textProperty().addListener(this::minutesChangeListener);
		 
		hidePasswords.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (!settings.setHidePasswords(newValue)) {
				Log.warning("Failed to update hide_passwords setting");
				showErrorMessage("An error occured.");
			}
		});
		 
		autolock.selectedProperty().addListener((observable, oldValue, newValue) -> {
			autolockMinsBox.setVisible(newValue ? true : false);
			if (!settings.setAutolock(newValue)) {
				Log.warning("Failed to update autolock setting");
				showErrorMessage("An error occured.");
			}
		});
		
		savePassword.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onSavePassword);
		cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onCancel);
		
		oldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			savePassword.setDisable(false);
			cancel.setDisable(false);
		});
	}

	/**
	 * Runs when the minutes input is changed. Updates the database if it's a valid integer or shows an error message
	 * @param event
	 */
	private void minutesChangeListener(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		try {
			int minutes = Integer.parseInt(newValue);
			if (!settings.setAutolockMins(minutes)) {
				Log.warning("Failed to update autolock_minutes setting");
				showErrorMessage("An error occured.");
			}
			errorMinutes.setVisible(false);
		}
		catch (NumberFormatException e) {
			/* the input isn't an integer, so clear it and show a message */
			errorMinutes.setVisible(true);
			autolockMins.clear();
		}
	}
	
	/**
	 * Runs when the save button is pressed.
	 * @param event
	 */
	private void onSavePassword(MouseEvent event) {
		/* checks if the oldPassword matches what's in the database */
		byte[] hashed = Util.sha256(oldPassword.getText().getBytes());
		if (!Arrays.equals(hashed, settings.getPassword())) {
			showErrorMessage("Old password doesn't match.");
			onCancel(null);
			return;
		}
		
		/* newPassword and newPasswordConfirm must be equal */
		if (!newPassword.getText().equals(newPasswordConfirm.getText())) {
			showErrorMessage("Both passwords must match.");
			onCancel(null);
			return;
		}
		
		/* commit */
		if (!settings.setPassword(newPassword.getText())) {
			Log.warning("Failed to set new password!");
			showErrorMessage("An error occured.");
		}
		else {
			showSuccessMessage("Password changed!");
			onCancel(null);
		}
	}
	
	/**
	 * Reverts any changes when the cancel button is pressed.
	 * Clears password fields, hides messages and disables buttons.
	 * @param event
	 */
	private void onCancel(MouseEvent event) {
		newPassword.clear();
		newPasswordConfirm.clear();
		oldPassword.clear();
		savePassword.setDisable(true);
		cancel.setDisable(true);
		
		if (event != null) {
			errorMsg.setVisible(false);
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
