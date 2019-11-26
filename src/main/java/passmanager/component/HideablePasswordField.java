package passmanager.component;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

/**
 * Custom component for a password field that can be toggled between masked or visible.
 */
public class HideablePasswordField extends HBox {

	@FXML TextField password;
	@FXML PasswordField passwordMasked;
	@FXML Button toggle;
	
	public HideablePasswordField() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/component/HideablePasswordField.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		toggle.addEventHandler(ActionEvent.ACTION, this::toggle);
	}
	
	/**
	 * Toggle the password between being visible or masked.
	 */
	public void toggle(ActionEvent event) {
		password.setDisable(!password.isDisabled());
		passwordMasked.setDisable(!passwordMasked.isDisabled());
		
		password.setVisible(!password.isVisible());
		passwordMasked.setVisible(!passwordMasked.isVisible());
		
		if (toggle.getText().equalsIgnoreCase("hide")) {
			toggle.setText("Show");
		}
		else {
			toggle.setText("Hide");
		}
	}
	
	/**
	 * Set the password text.
	 */
	public void setText(String newPassword) {
		password.setText(newPassword);
		passwordMasked.setText(newPassword);
	}
	
	/**
	 * Get the password text.
	 * @return String
	 */
	public String getText() {
		return password.getText();
	}
	
}
