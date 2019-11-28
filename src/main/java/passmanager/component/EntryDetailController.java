package passmanager.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import passmanager.Database;
import passmanager.Util;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;

public class EntryDetailController implements Initializable {

	@FXML GridPane gridpane;
	@FXML VBox noSelection;
	@FXML Pane details;
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
	
	@FXML HBox editControls;
	@FXML Button deleteButton;
	@FXML Button cancelEdit;
	@FXML Button saveButton;
	
	PasswordEntry item;
	Runnable callCancel, callDelete, callEdit, callSave;

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
	
	
	public void setCancelCallback(Runnable callback) {
		this.callCancel = callback;
	}
	
	public void setDeleteCallback(Runnable callback) {
		this.callDelete = callback;
	}
	
	public void setEditCallback(Runnable callback) {
		this.callEdit = callback;
	}
	
	
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		/* action hides/shows passwords */
		toggleHide.setOnMouseClicked(event -> setMasked(!isMasked()));
		
		/* when in edit mode, persist to database */
		saveButton.setOnMouseClicked(this::onSaveEdit);
		
		/* button will enter edit mode */
		editButton.setOnMouseClicked(event -> {
			setEditMode(true);
			if (callEdit != null) {
				callEdit.run();
			}
		});
		
		/* discard changes and revert back to read-only mode */
		cancelEdit.setOnMouseClicked(event -> {
			setEditMode(false);
			if (callCancel != null) {
				callCancel.run();
			}
		});
		
		/* deletes the current entry and refreshes the view */
		deleteButton.setOnMouseClicked(event -> {
			setEditMode(false);
			if (callDelete != null) {
				callDelete.run();
			}
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
		boolean nullData = (data == null);
		
		noSelection.setVisible(nullData);
		editButton.setDisable(nullData);
		details.setVisible(!nullData);
		title.setVisible(!nullData);
		
		if (nullData) {
			return;
		}
		
		item = data;
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
		
		toggleHide.setText(value ? "Hide" : "Show");
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
		website.setEditable(!value);
		username.setEditable(!value);
		passwordPlain.setEditable(!value);
		notes.setEditable(!value);
	}
	
	/**
	 * Gets the editable status for the details pane.
	 * @return boolean
	 */
	public boolean isEditMode() {
		return editControls.isVisible();
	}
	
	/**
	 * Sets the view to editable mode.
	 * @param boolean value
	 */
	public void setEditMode(boolean value) {
		if (value) {
			toggleHide.setDisable(true);
			setReadOnly(false);
			setMasked(false);
			
			addNewButton.setVisible(false);
			editButton.setVisible(false);
			editControls.setVisible(true);
			deleteButton.setVisible(true);
		}
		else {
			toggleHide.setDisable(false);
			setReadOnly(true);
			setMasked(true);
			
			addNewButton.setVisible(true);
			editButton.setVisible(true);
			editControls.setVisible(false);
			deleteButton.setVisible(false);
		}
	}

	
	/**
	 * Called when the edits to the selected model should be saved to the database.
	 * @param MouseEvent event
	 */
	protected void onSaveEdit(MouseEvent event) {
		setEditMode(false);

		item.setTitle(title.getText());
		item.setUsername(username.getText());
		item.setPassword(passwordPlain.getText().getBytes());
		item.setNote(notes.getText());
		item.setUrl(website.getText());
		item.setUpdatedAt(null);
		
		try {
			CreateOrUpdateStatus status = Database.getDao(PasswordEntry.class).createOrUpdate(item);
			if (!status.isUpdated()) {
				System.out.println("Failed to update item entry.");
				throw new SQLException();
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (callCancel != null) {
				callCancel.run();
			}
		}
	}


}
