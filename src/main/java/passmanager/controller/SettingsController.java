package passmanager.controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import passmanager.Database;
import passmanager.Settings;
import passmanager.Util;
import passmanager.interfaces.Initializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	
	@Override
	public void initialize() {
		int minutes = settings.getAutolockMins();
		if (minutes > 0) {
			autolockMinsBox.setVisible(true);
			autolockMins.setText(String.valueOf(minutes));
		}
		 
		hidePasswords.setSelected(settings.getHidePasswords());
		 
		/* register change listeners */
		autolockMins.textProperty().addListener(this::minutesChangeListener);
		 
		hidePasswords.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (!settings.setHidePasswords(newValue)) {
				Log.warning("Failed to update hide_passwords setting");
				showErrorMessage("An error occured.");
			}
		});
		 
		autolock.selectedProperty().addListener((observable, oldValue, newValue) -> {
			autolockMinsBox.setVisible(newValue ? true : false);
		});
		
		savePassword.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onSavePassword);
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
			errorMinutes.setVisible(true);
			autolockMins.clear();
		}
	}
	
	/**
	 * Runs when the save button is pressed.
	 * @param event
	 */
	@FXML
	public void onSavePassword(MouseEvent event) {
		if (!newPassword.getText().equals(newPasswordConfirm.getText())) {
			showErrorMessage("Both passwords must match.");
			newPassword.clear();
			newPasswordConfirm.clear();
		}
	}
	
	/**
	 * Reverts any changes when the cancel button is pressed.
	 * @param event
	 */
	@FXML
	public void onCancel(ActionEvent event) {
		newPassword.clear();
		newPasswordConfirm.clear();
		oldPassword.clear();
		savePassword.setDisable(true);
		cancel.setDisable(true);
		errorMsg.setVisible(false);
	}
	
	/**
	 * Sets errorMsg text and sets it to visible.
	 * @param message
	 */
	private void showErrorMessage(String message) {
		errorMsg.setText(message);
		errorMsg.setVisible(true);
	}
}
