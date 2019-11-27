package passmanager.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import passmanager.Util;
import passmanager.controller.BaseController;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;

public class EntryDetailController implements Initializable {

	@FXML GridPane gridpane;
	@FXML ImageView favicon;
	@FXML Label title;
	@FXML TextField website;
	@FXML TextField username;
	@FXML TextField passwordPlain;
	@FXML PasswordField passwordMasked;
	@FXML Button toggleHide;
	@FXML TextArea notes;
	@FXML Label lastUpdateLabel;
	@FXML Button addNewButton;
	@FXML Button editButton;
	@FXML HBox editConfirmBox;
	@FXML Button saveEdit;
	@FXML Button revertEdit;
	
	
	public EntryDetailController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/component/EntryDetail.fxml"));
			loader.setController(this);
			gridpane = loader.load();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		toggleHide.addEventHandler(ActionEvent.ACTION, event -> setMasked(!isMasked()));
		
		addNewButton.addEventHandler(ActionEvent.ACTION, event -> {
			BaseController.loadNewEntry("/layout/newEntries.fxml");
		});
	}
	
	/**
	 * Returns the bounding container for EntryData.
	 * @return GridPane
	 */
	public GridPane getBox() {
		return gridpane;
	}
	
	/**
	 * Populate the EntryDetail view with the correct data.
	 * @param PasswordEntry data
	 */
	public void setData(PasswordEntry data) {
		title.setText( data.getTitle() );
		website.setText( data.getUrl() );
		username.setText( data.getUsername() );
		passwordMasked.setText( new String(data.getPassword()) );
		notes.setText( data.getNote() );
		lastUpdateLabel.setText( Util.formatDate(data.getUpdatedAt()) );
		
		try {
			passwordPlain.setText( new String(data.getPassword(), "UTF-8") );
		} 
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		/* use a placeholder image for entries without a favicon set */
		if (data.getFavicon() == null) {
			favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
		}
		else {
			favicon.setImage(data.getFavicon());
		}
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
