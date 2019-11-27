package passmanager.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import com.j256.ormlite.dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import passmanager.Database;
import passmanager.Util;
import passmanager.component.EntryListCell;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;

public class IndexController extends BaseController implements Initializable {
	private final static Logger Log = Util.getLogger(IndexController.class);
	
	@FXML MenuBar menu;
	@FXML TextField searchText;
	@FXML Button searchButton;
	@FXML Button editButton;
	@FXML Button addNewButton;
	@FXML ListView<PasswordEntry> entryListView;
	@FXML Label statusMessage;
	
	ObservableList<PasswordEntry> entryCollection = FXCollections.observableArrayList();
	
	
	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}

		entryListView.setItems( entryCollection );
		entryListView.setCellFactory(listview -> new EntryListCell() );
	}

	
	/**
	 * used to process the different choices in the menu bar
	 * 
	 * @param ActionEvent event
	 */
	public void onMenuClick(ActionEvent event) {
		MenuItem menuItem = (MenuItem) event.getTarget();

		switch (menuItem.getText()) {
		case "Add New Entry":
			loadNewEntry("/layout/newEntries.fxml");
			break;
			
		case "Logout":
			loadScene("/layout/login.fxml");
			break;
			
		case "Export":
			// TODO: Export functionality
			break;
			
		case "Preferences":
			// TODO: Refactor into a proper "modal" loader...
			loadNewEntry("/layout/settings.fxml");
			break;
			
		case "Close":
			getStage().close();

		default:
			Log.info("Unexpected MenuItem ActionEvent recieved.");
		}
	}


	
	public void onEditEntry(ActionEvent event) {
		if (event.getSource() == edit) {
			if (edit.isSelected()) {
				DisplayWebsite.setEditable(true);
				DisplayUsername.setEditable(true);
				DisplayPassword.setEditable(true);
				DisplayPassword.setVisible(true);
				hiddenPassword.setVisible(false);
				DisplayNotes.setEditable(true);
				DisplaySave.setVisible(true);
			} else {
				DisplayWebsite.setEditable(false);
				DisplayUsername.setEditable(false);
				DisplayPassword.setEditable(false);
				DisplayPassword.setVisible(false);
				hiddenPassword.setVisible(true);
				hiddenPassword.setEditable(false);
				DisplayNotes.setEditable(false);
				DisplaySave.setVisible(false);
			}
		}
	}
	
	
	public void setStatusMessage(String msg) {
		statusMessage.setText(msg);
	}
}
