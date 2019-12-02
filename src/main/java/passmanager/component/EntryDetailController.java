package passmanager.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.stream.Collectors;
import com.j256.ormlite.dao.Dao;
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
	@FXML HBox updatedBox;
	
	@FXML HBox editControls;
	@FXML TextField editTitle;
	@FXML Button deleteButton;
	@FXML Button cancelEdit;
	@FXML Button saveButton;
	@FXML Button generateButton;
	
	PasswordEntry item = null;
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
	
	public void setSaveCallback(Runnable callback) {
		this.callSave = callback;
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
		
		/* keeps the masked and plain password text in sync */
		passwordPlain.textProperty().addListener((ob, oldValue, newValue) -> passwordMasked.setText(newValue));
		
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
		
		addNewButton.setOnMouseClicked(event -> {
			item = new PasswordEntry();
			setEditMode(true);
			clearAllFields();
			
			noSelection.setVisible(false);
			details.setVisible(true);
			deleteButton.setDisable(true);
			
			if (callEdit != null) {
				callEdit.run();
			}
		});
		
		/* generates a random password for convenience */
		generateButton.setOnMouseClicked(event -> {
			String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()_+-=[]?";

			String generated = new SecureRandom().ints(17, 0, charset.length())
									 			 .mapToObj(i -> String.valueOf(charset.charAt(i)))
									 			 .collect(Collectors.joining());
			
			passwordPlain.setText(generated);
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
		item = data;
		
		boolean nullData = (data == null);
		
		favicon.setVisible(!nullData);
		title.setVisible(!nullData);
		noSelection.setVisible(nullData);
		editButton.setDisable(nullData);
		details.setVisible(!nullData);
		updatedBox.setVisible(!nullData);
		
		if (nullData) {
			return;
		}
		
		title.setText( data.getTitle() );
		website.setText( data.getUrl() );
		username.setText( data.getUsername() );
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
			// favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
			item.setDefaultFavicon();
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
		/*
		if (value) {
			toggleHide.setDisable(value);
			setReadOnly(!value);
			setMasked(!value);

			title.setVisible(!value);
			editTitle.setVisible(value);
			editTitle.setText(title.getText());
			addNewButton.setVisible(!value);
			editButton.setVisible(!value);
			editControls.setVisible(value);
			deleteButton.setVisible(value);
			deleteButton.setDisable(!value);
		}
		else {
			toggleHide.setDisable(false);
			setReadOnly(true);
			setMasked(true);
			
			title.setVisible(true);
			editTitle.setVisible(false);
			addNewButton.setVisible(true);
			editButton.setVisible(true);
			editControls.setVisible(false);
			deleteButton.setVisible(false);
		}
		*/
		toggleHide.setDisable(value);
		setReadOnly(!value);
		setMasked(!value);

		title.setVisible(!value);
		editTitle.setVisible(value);
		editTitle.setText(title.getText());
		addNewButton.setVisible(!value);
		editButton.setVisible(!value);
		editControls.setVisible(value);
		deleteButton.setVisible(value);
		deleteButton.setDisable(!value);
		
		generateButton.setVisible(value);
	}

	
	/**
	 * Called when the edits to the selected model should be saved to the database.
	 * @param MouseEvent event
	 */
	protected void onSaveEdit(MouseEvent event) {
		setEditMode(false);

		item.setTitle(editTitle.getText());
		item.setUsername(username.getText());
		item.setPassword(passwordPlain.getText().getBytes());
		item.setNote(notes.getText());
		item.setUrl(website.getText());
		item.setUpdatedAt(null);
		item.setFavicon(null);
		
		try {
			Dao<PasswordEntry, Integer> temp = Database.getDao(PasswordEntry.class);
			
			if (item.getId() == -1) {
				temp.create(item);
			}
			else {
				if (temp.update(item) != 1) {
					throw new SQLException();
				}
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (callCancel != null) {
				callCancel.run();
			}
			
			if (callSave != null) {
				callSave.run();
			}
		}
	}
	
	/**
	 * Clears all editable elements, and sets the default favicon image.
	 */
	protected void clearAllFields() {
		editTitle.clear();
		website.clear();
		username.clear();
		passwordPlain.clear();
		notes.clear();
		
		favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
	}

}
