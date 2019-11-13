package passmanager.controller;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Handles the settings window.
 */
public class SettingsController extends BaseController {
	
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
	
	/**
	 * Runs when any checkbox or control, other than the password fields, are changed.
	 * It saves the options in the background, without needing to have a seperate save button.
	 * @param event
	 */
	@FXML
	public void onChange(ActionEvent event) {
		if (event.getSource().equals(hidePasswords)) {
			if (hidePasswords.isSelected()) {
				hidePasswordsboolean = true;
				savePassword.setDisable(false);
				cancel.setDisable(false);
			}
			else {
				hidePasswordsboolean = false;
				savePassword.setDisable(false);
				cancel.setDisable(false);
			}
			
		}
		
		if (event.getSource().equals(autolock)) {
			
		}
		
		if (event.getSource().equals(autolockMins)) {
			
		}
	}
	
	/**
	 * Runs when the user starts typing their old password.
	 * @param event
	 */
	@FXML
	public void onPasswordEntry(ActionEvent event) {
		savePassword.setDisable(false);
		cancel.setDisable(false);
	}
	
	/**
	 * Runs when the save button is pressed.
	 * @param event
	 */
	@FXML
	public void onPasswordSave(ActionEvent event) {
		Stage stage = (Stage) savePassword.getScene().getWindow();
		hidePasswords.setSelected(hidePasswords.isSelected());
		savePassword.setDisable(true);
		cancel.setDisable(true);
		stage.close();
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

}
