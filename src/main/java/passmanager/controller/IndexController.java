package passmanager.controller;

import java.sql.SQLException;
import com.j256.ormlite.dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import passmanager.Database;
import passmanager.component.EntryDetailController;
import passmanager.component.EntryListCell;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;

/**
 * Controller manages the 'index' view. 
 */
public class IndexController extends BaseController implements Initializable {

	@FXML MenuBar menu;
	@FXML TextField searchText;
	@FXML Button searchButton;
	@FXML ListView<PasswordEntry> entryListView;
	@FXML Label statusMessage;
	@FXML SplitPane splitPane;
	
	EntryDetailController entryDetail;
	ObservableList<PasswordEntry> entryCollection = FXCollections.observableArrayList();

	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		entryDetail = new EntryDetailController();
		
		splitPane.getItems().add( entryDetail.getBox() );
		
		/* fill our collection of entries */
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}
		entryListView.setItems( entryCollection );

		/* this updates the detail pane with the correct model when a list item is selected/clicked */
		entryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> entryDetail.setData(newValue));
		
		entryListView.setCellFactory(listview -> new EntryListCell());
		entryListView.getSelectionModel().selectFirst();

		/* register any callbacks */
		entryDetail.setDeleteCallback(this::doDeleteCallback);
		
		entryDetail.setEditCallback(() -> entryListView.setDisable(true));
		
		entryDetail.setCancelCallback(() -> {
			entryListView.setDisable(false);
			entryDetail.setData(entryListView.getSelectionModel().getSelectedItem());
		});
		
		entryDetail.setSaveCallback(this::reload);
		
		setStatusMessage("Loaded " + entryCollection.size() + " entries.");
	}
	
	/**
	 * Called when the 'delete' button is pressed when editing an entry.
	 * This probably should be moved to EntryDetailController.
	 */
	protected void doDeleteCallback() {
		PasswordEntry item = entryListView.getSelectionModel().getSelectedItem();
		
		try {
			Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
			pdao.delete(item);
		}
		catch (SQLException e) {
			setStatusMessage("Failed to delete entry!");
			return;
		}
		finally {
			entryListView.setDisable(false);
		}
		
		setStatusMessage("Entry deleted.");
		
		entryListView.getSelectionModel().clearSelection();
		entryListView.getSelectionModel().selectNext();
		entryCollection.remove(item);
	}

	public void reload() {
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		entryCollection.clear();
		
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}
		entryListView.setItems( entryCollection );
	}

	/**
	 * used to process the different choices in the menu bar
	 * 
	 * @param ActionEvent event
	 */
	public void onMenuClick(ActionEvent event) {
		MenuItem menuItem = (MenuItem) event.getTarget();

		switch (menuItem.getText()) {
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
			/* Shouldn't normally be able to get here. */
		}
	}

	/**
	 * Sets the status message on the bottom right of the window.
	 * @param String msg
	 */
	public void setStatusMessage(String msg) {
		statusMessage.setText(msg);
	}

}
