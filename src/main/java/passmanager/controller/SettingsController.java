package passmanager.controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import passmanager.Database;
import passmanager.Util;
import passmanager.interfaces.Initializable;
import passmanager.model.Settings;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

/**
 * Handles the settings window.
 */
public class SettingsController extends BaseController implements Initializable {

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
	@FXML Label labelLastUpdated;
	
	Settings settings = null;
	Dao<Settings, Integer> settingsDao = Database.getDao(Settings.class);
	
	/**
	 * Automatically called by FXMLLoader
	 */
	@Override
	public void initialize() {
		try {
			settings = settingsDao.queryForId(1);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		/* load previously set values and reflect them in the view */
		if (settings.isAutolock()) {
			autolockMins.setText(String.valueOf(settings.getAutolockMinutes()));
			autolockMinsBox.setVisible(true);
			autolock.setSelected(true);
		}
		 
		hidePasswords.setSelected(settings.isHidePasswords());
		
		Date updatedAt = settings.getUpdatedAt();
		labelLastUpdated.setText(updatedAt == null ? "Never" : DateFormat.getInstance().format(updatedAt));
		 
		/* register value change listeners and event handlers for inputs */
		autolockMins.textProperty().addListener(this::minutesChangeListener);
		 
		hidePasswords.selectedProperty().addListener((observable, oldValue, newValue) -> settings.setHidePasswords(newValue));
		 
		autolock.selectedProperty().addListener((observable, oldValue, newValue) -> {
			autolockMinsBox.setVisible(!autolockMinsBox.isVisible());
			settings.setAutolock(newValue);
			
			try {
				settingsDao.update(settings);
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
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
			settings.setAutolockMinutes(minutes);
			errorMinutes.setVisible(false);
			
			settingsDao.update(settings);
		}
		catch (SQLException | NumberFormatException e) {
			/* the input isn't an integer, so show an error message */
			showErrorMessage("An error occured.");
			errorMinutes.setVisible(true);
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
		settings.setPassword(newPassword.getText().getBytes());
		try {
			settingsDao.update(settings);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		showSuccessMessage("Password changed!");
		onCancel(null);
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
