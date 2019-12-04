package passmanager.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import java.sql.SQLException;
=======
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.stream.Collectors;

>>>>>>> develop
=======
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.stream.Collectors;
>>>>>>> develop
=======
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.stream.Collectors;
>>>>>>> develop
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
<<<<<<< HEAD
import passmanager.Util;
=======
import static passmanager.Util.*;
>>>>>>> develop
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;

public class EntryDetailController implements Initializable {

<<<<<<< HEAD
<<<<<<< HEAD
=======
	/* enclosing containers */
>>>>>>> develop
=======
	// enclosing containers
>>>>>>> develop
	@FXML GridPane gridpane;
	@FXML VBox noSelection;
	@FXML Pane details;
	
<<<<<<< HEAD
<<<<<<< HEAD
=======
	/* entry detail contents */
>>>>>>> develop
=======
	// entry detail contents
>>>>>>> develop
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
	
<<<<<<< HEAD
<<<<<<< HEAD
=======
	/* edit mode specific elements */
>>>>>>> develop
=======
	// edit mode specific elements
>>>>>>> develop
	@FXML HBox editControls;
	@FXML TextField editTitle;
	@FXML Button deleteButton;
	@FXML Button cancelEdit;
	@FXML Button saveButton;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
	@FXML Button generateButton;
>>>>>>> develop
=======
	@FXML Button generateButton;
>>>>>>> develop
=======
	@FXML Button generateButton;
>>>>>>> develop
	
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
	
<<<<<<< HEAD
	
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
	
=======
>>>>>>> develop
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		/* action hides/shows passwords */
		toggleHide.setOnMouseClicked(event -> setMasked(!isMasked()));
		
		/* when in edit mode, persist to database */
		saveButton.setOnMouseClicked(this::onSaveEdit);
		
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
		/* keeps the masked and plain password text in sync */
		passwordPlain.textProperty().addListener((ob, oldValue, newValue) -> passwordMasked.setText(newValue));
		
>>>>>>> develop
=======
		/* keeps the masked and plain password text in sync */
		passwordPlain.textProperty().addListener((ob, oldValue, newValue) -> passwordMasked.setText(newValue));
		
>>>>>>> develop
		/* button will enter edit mode */
		editButton.setOnMouseClicked(event -> {
			setEditMode(true);
			if (callEdit != null) {
				callEdit.run();
			}
=======
		/* keeps the masked and plain password text in sync */
		passwordPlain.textProperty().addListener((ob, oldValue, newValue) -> passwordMasked.setText(newValue));
		
		/* button will enter edit mode */
		editButton.setOnMouseClicked(event -> {
			setEditMode(true);
			if (callEdit != null) callEdit.run();
>>>>>>> develop
		});
		
		/* discard changes and revert back to read-only mode */
		cancelEdit.setOnMouseClicked(event -> {
			setEditMode(false);
<<<<<<< HEAD
			if (callCancel != null) {
				callCancel.run();
			}
=======
			if (callCancel != null) callCancel.run();
>>>>>>> develop
		});
		
		/* deletes the current entry and refreshes the view */
		deleteButton.setOnMouseClicked(event -> {
			setEditMode(false);
<<<<<<< HEAD
			if (callDelete != null) {
				callDelete.run();
			}
		});
		
		addNewButton.setOnMouseClicked(event -> {
<<<<<<< HEAD
			deleteButton.setDisable(true);
			noSelection.setVisible(false);
			details.setVisible(true);
			
			item = new PasswordEntry();
<<<<<<< HEAD
			setEditMode(true);			
=======
			setEditMode(true);
			clearAllFields();
			
=======
=======
			if (callDelete != null) callDelete.run();
		});
		
		addNewButton.setOnMouseClicked(event -> {
>>>>>>> develop
			item = new PasswordEntry();
			setEditMode(true);
			clearAllFields();
			
			noSelection.setVisible(false);
			details.setVisible(true);
			deleteButton.setDisable(true);
			
<<<<<<< HEAD
>>>>>>> develop
			if (callEdit != null) {
				callEdit.run();
			}
=======
			if (callEdit != null) callEdit.run();
>>>>>>> develop
		});
		
		/* generates a random password for convenience */
		generateButton.setOnMouseClicked(event -> {
			String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()_+-=[]?";

			String generated = new SecureRandom().ints(17, 0, charset.length())
									 			 .mapToObj(i -> String.valueOf(charset.charAt(i)))
									 			 .collect(Collectors.joining());
			
			passwordPlain.setText(generated);
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
		});
	}
	
	
=======
		});
	}
	
>>>>>>> develop
=======
		});
	}
	
>>>>>>> develop
	/**
	 * Returns the bounding container for EntryData.
	 * @return GridPane
	 */
	public GridPane getBox() {
		return gridpane;
	}
	
<<<<<<< HEAD
<<<<<<< HEAD
	
=======
>>>>>>> develop
=======
>>>>>>> develop
	/**
	 * Populate the EntryDetail view with the correct data.
	 * @param PasswordEntry data
	 */
	public void setData(PasswordEntry data) {
		item = data;
		
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		boolean nullData = (data == null || data == null);
=======
		boolean nullData = (data == null);
>>>>>>> develop
=======
		boolean nullData = (data == null);
>>>>>>> develop
		
		favicon.setVisible(!nullData);
		title.setVisible(!nullData);
		noSelection.setVisible(nullData);
		editButton.setDisable(nullData);
		details.setVisible(!nullData);
		updatedBox.setVisible(!nullData);
=======
		boolean nullData = (data == null);
		
		setVisible(!nullData, favicon, title, details, updatedBox);
		noSelection.setVisible(nullData);
		editButton.setDisable(nullData);
>>>>>>> develop
		
		if (nullData) {
			return;
		}
		
		title.setText( data.getTitle() );
		website.setText( data.getUrl() );
		username.setText( data.getUsername() );
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		passwordMasked.setText( new String(data.getPassword()) );
=======
>>>>>>> develop
=======
>>>>>>> develop
		notes.setText( data.getNote() );
		lastUpdateLabel.setText( Util.formatDate(data.getUpdatedAt()) );
=======
		notes.setText( data.getNote() );
		lastUpdateLabel.setText( formatDate(data.getUpdatedAt()) );
>>>>>>> develop
		
		try {
			passwordPlain.setText( new String(data.getPassword(), "UTF-8") );
		} 
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		/* use a placeholder image for entries without a favicon set */
		if (data.getFavicon() == null) {
<<<<<<< HEAD
<<<<<<< HEAD
			favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
=======
			item.setDefaultFavicon();
>>>>>>> develop
=======
			item.setDefaultFavicon();
>>>>>>> develop
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
<<<<<<< HEAD
<<<<<<< HEAD
		
		passwordMasked.setVisible(value);
		passwordMasked.setDisable(!value);
		
<<<<<<< HEAD
		toggleHide.setText(value ? "Hide" : "Show");
=======
		toggleHide.setText(value ? "Show" : "Hide");
>>>>>>> develop
=======
		passwordMasked.setVisible(value);
		passwordMasked.setDisable(!value);
		toggleHide.setText(value ? "Show" : "Hide");
>>>>>>> develop
=======
		passwordMasked.setVisible(value);
		passwordMasked.setDisable(!value);
		toggleHide.setText(value ? "Show" : "Hide");
>>>>>>> develop
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
<<<<<<< HEAD
<<<<<<< HEAD
		if (value) {
			toggleHide.setDisable(true);
			setReadOnly(false);
			setMasked(false);
<<<<<<< HEAD
		
			title.setVisible(false);
			editTitle.setVisible(true);
=======

			title.setVisible(false);
			editTitle.setVisible(true);
			editTitle.setText(title.getText());
>>>>>>> develop
			addNewButton.setVisible(false);
			editButton.setVisible(false);
			editControls.setVisible(true);
			deleteButton.setVisible(true);
			deleteButton.setDisable(false);
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
<<<<<<< HEAD
=======
		
		generateButton.setVisible(value);
>>>>>>> develop
=======
=======
>>>>>>> develop
		setReadOnly(!value);
		setMasked(!value);
		
		editTitle.setVisible(value);
		if (value) {
			editTitle.setText(title.getText());
		}

<<<<<<< HEAD
		toggleHide.setDisable(value);
		title.setVisible(!value);
		addNewButton.setVisible(!value);
		editButton.setVisible(!value);
		editControls.setVisible(value);
		deleteButton.setVisible(value);
		deleteButton.setDisable(!value);
		generateButton.setVisible(value);
>>>>>>> develop
=======
		setVisible(value, editControls, deleteButton, generateButton);
		setVisible(!value, title, addNewButton, editButton);
		
		toggleHide.setDisable(value);
		deleteButton.setDisable(!value);
>>>>>>> develop
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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
		item.setFavicon(null);
>>>>>>> develop
=======
		item.setFavicon(null);
>>>>>>> develop
=======
		item.setFavicon(null);
>>>>>>> develop
		
		try {
			Dao<PasswordEntry, Integer> temp = Database.getDao(PasswordEntry.class);
			
			if (item.getId() == -1) {
				temp.create(item);
			}
<<<<<<< HEAD
			else {
				if (temp.update(item) != 1) {
					throw new SQLException();
				}
=======
			else if (temp.update(item) != 1) {
				throw new SQLException();
>>>>>>> develop
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
<<<<<<< HEAD
			if (callCancel != null) {
				callCancel.run();
			}
			
			if (callSave != null) {
				callSave.run();
			}
		}
	}
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> develop
=======
			if (callCancel != null) callCancel.run();
			if (callSave != null) callSave.run();
		}
	}
>>>>>>> develop
	
	/**
	 * Clears all editable elements, and sets the default favicon image.
	 */
	protected void clearAllFields() {
<<<<<<< HEAD
		editTitle.clear();
		website.clear();
		username.clear();
		passwordPlain.clear();
		notes.clear();
		
		favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
	}
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
=======
		clearText(editTitle, website, username, passwordPlain, notes);
		
		favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
	}
	
	/**
	 * Setter methods for various callbacks.
	 * @param callback
	 */
	public void setCancelCallback(Runnable callback) {
		callCancel = callback;
	}
	
	public void setDeleteCallback(Runnable callback) {
		callDelete = callback;
	}
	
	public void setEditCallback(Runnable callback) {
		callEdit = callback;
	}
	
	public void setSaveCallback(Runnable callback) {
		callSave = callback;
	}
>>>>>>> develop

}
