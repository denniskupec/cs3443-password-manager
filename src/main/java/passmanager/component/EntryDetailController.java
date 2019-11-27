package passmanager.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import passmanager.interfaces.Initializable;

public class EntryDetailController implements Initializable {

	@FXML ImageView favicon;
	@FXML Label title;
	@FXML TextField website;
	@FXML TextField username;
	@FXML TextField passwordPlain;
	@FXML PasswordField passwordMasked;
	@FXML Button toggleHide;
	@FXML TextArea notes;
	@FXML Label lastUpdateLabel;
	
	
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		toggleHide.addEventHandler(ActionEvent.ACTION, event -> setMasked(!isMasked()));
	}

	/**
	 * A masked password is a hidden password.
	 * @return boolean
	 */
	public boolean isMasked() {
		return passwordMasked.isVisible();
	}
	
	/**
	 * Hide or show the password in plain text.
	 * @param boolean value
	 */
	public void setMasked(boolean value) {
		passwordPlain.setVisible(!value);
		passwordPlain.setDisable(value);
		passwordMasked.setVisible(value);
		passwordMasked.setDisable(!value);
		toggleHide.setText(value ? "Show" : "Hide");
	}
	
	/**
	 * When not in edit mode, fields can only be copied from.
	 * @return boolean
	 */
	public boolean isReadOnly() {
		return username.isEditable();
	}
	
	/**
	 * Set read-only mode. No edits allowed, only able to copy fields.
	 * @param value
	 */
	public void setReadOnly(boolean value) {
		website.setEditable(value);
		username.setEditable(value);
		passwordPlain.setEditable(value);
		passwordMasked.setEditable(value);
		notes.setEditable(value);
	}
	
}
