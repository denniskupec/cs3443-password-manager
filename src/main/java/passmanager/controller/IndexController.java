package passmanager.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.j256.ormlite.dao.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import passmanager.Database;
import passmanager.Util;
import passmanager.component.EntryListCell;
import passmanager.component.EntryListCellController;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;

public class IndexController extends BaseController implements Initializable {
	private final static Logger Log = Util.getLogger(IndexController.class);
	
	@FXML MenuItem choiceNew;
	@FXML TextField DisplayWebsite;
	@FXML TextField DisplayUsername;
	@FXML TextField DisplayPassword;
	@FXML PasswordField hiddenPassword;
	@FXML TextArea DisplayNotes;
	@FXML MenuItem choiceSettings;
	@FXML MenuItem choiceQuit;
	@FXML Button DisplaySave;
	@FXML CheckBox edit;
	
	@FXML ListView<PasswordEntry> entryListView;
	
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
		/*
		entryListView.setCellFactory( listview -> {
			EntryListCellController cellController = new EntryListCellController();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/component/EntryListCell.fxml"));
			loader.setController(cellController);
			loader.setRoot(cellController);
			
			try {
				return loader.load();
			} 
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		*/
		
		entryListView.setCellFactory(listview -> new EntryListCell() );
	}

	
	/**
	 * used to process the different choices in the menu bar
	 * 
	 * @param event
	 */
	public void onMenu(Event event) throws IOException {
		// i don't think this is strictly necessary, but i just copied
		// what dennis did to be safe
		if (event.getSource() == choiceNew) {
			loadNewEntry("/layout/newEntries.fxml");
			Log.info("New Selected, inputting new entry");
		}
		if (event.getSource() == choiceSettings) {
			loadNewEntry("/layout/settings.fxml");
			Log.info("Settings selected");
		}

		if (event.getSource() == choiceQuit) {
			stage.close();
		}
	}

	
	@FXML
	public void click(MouseEvent event) {
		Log.info("Selected: " + entryListView.getSelectionModel().getSelectedItem().getTitle());
	}

	
	public void onEditEntry(Event event) {
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
}
