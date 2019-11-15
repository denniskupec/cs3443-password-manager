package passmanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import passmanager.Database;
import passmanager.Settings;
import passmanager.Util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
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
	int hidepass;
	File file;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadSettings();
			if(hidepass == 1)
			{
				hidePasswords.setSelected(true);
			}
			else
			{
				hidePasswords.setSelected(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void loadSettings() throws SQLException {
		String sql = "SELECT hide_passwords FROM settings";
		Connection conn = Database.connect();
		ResultSet rs = conn.createStatement().executeQuery(sql);
		rs.next();
		hidepass = rs.getInt(1);
		rs.close();
		conn.close();
	}
	/**
	 * Runs when any checkbox or control, other than the password fields, are changed.
	 * It saves the options in the background, without needing to have a seperate save button.
	 * @param event
	 */
	@FXML
	public void onChange(ActionEvent event) throws Exception{
		if (event.getSource().equals(hidePasswords)) {
			if (hidePasswords.isSelected()) {
				updateHidePasswords(1);
				savePassword.setDisable(false);
				cancel.setDisable(false);
			}
			else {
				updateHidePasswords(0);
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
	 * used to update the value of hide_passwords in the database
	 * 
	 * @param hide
	 * @throws Exception
	 */
	public void updateHidePasswords(int hide) throws Exception{
		Connection connection = Database.connect();
		String sql = "UPDATE settings " + "SET hide_passwords = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, hide);
		ps.executeUpdate();
		ps.close();
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
	public void onPasswordSave(ActionEvent event) throws Exception {
		Stage stage = (Stage) savePassword.getScene().getWindow();
		hidePasswords.setSelected(hidePasswords.isSelected());
		savePassword.setDisable(true);
		cancel.setDisable(true);
		Log.info("Settings updated");
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
