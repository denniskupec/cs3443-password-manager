package passmanager.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import com.j256.ormlite.dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import passmanager.Database;
import passmanager.component.EntryDetailController;
import passmanager.component.EntryListCell;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;
import passmanager.model.Settings;

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
	
	Date lastActive;
	Settings settings;
	EntryDetailController entryDetail;
	ObservableList<PasswordEntry> entryCollection = FXCollections.observableArrayList();

	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
		try {
			settings = Database.getDao(Settings.class).queryForId(1);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		entryDetail = new EntryDetailController();
		splitPane.getItems().add( entryDetail.getBox() );
		
		/* fill our collection of entries */
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}
		entryListView.setItems( entryCollection );
		
		if (entryCollection.isEmpty()) {
			setStatusMessage("Empty database. Try adding a new item.");
			entryDetail.setData(null);
		}

		/* this updates the detail pane with the correct model when a list item is selected/clicked */
		entryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> entryDetail.setData(newValue));
		
		entryListView.setCellFactory(listview -> new EntryListCell());
		entryListView.getSelectionModel().selectFirst();

		/* register any callbacks */
		entryDetail.setDeleteCallback(this::doDeleteCallback);
		
		entryDetail.setEditCallback(() -> {
			searchButton.setDisable(true);
			searchText.setDisable(true);
			entryListView.setDisable(true);
		});
		
		entryDetail.setCancelCallback(() -> {
			entryListView.setDisable(false);
			searchButton.setDisable(false);
			searchText.setDisable(false);
			entryDetail.setData(entryListView.getSelectionModel().getSelectedItem());
		});
		
		entryDetail.setSaveCallback(this::reload);
		
		searchButton.setOnMouseClicked(this::doSearch);
		searchText.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				searchText.clear();
				reload();
				break;
				
			case ENTER:
				doSearch(null);
				break;
				
			default:
				break;
			}
		});
		
		searchText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!oldValue.isEmpty() && newValue.isEmpty()) {
				reload();
			}
		});
		
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

	/**
	 * Reloads the list of items from the database. Does not change selected item.
	 */
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
			
		case "Export CSV":
			doExport();
			break;
			
		case "Preferences":
			// TODO: Refactor into a proper "modal" loader...
			loadNewEntry("/layout/settings.fxml");
			break;
			
		case "Reload":
			reload();
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
	
	/**
	 * Searches the current entry list collection for a title containing a keyword, and sets the entry list to display only the found items.
	 * @param MouseEvent event
	 */
	protected void doSearch(MouseEvent event) {
		String searchString = searchText.getText();
		if (searchString.isEmpty()) {
			return;
		}
		
		ObservableList<PasswordEntry> temp = FXCollections.observableArrayList();

		for (PasswordEntry p : entryCollection) {
			if (p.getTitle().contains(searchString)) {
				temp.add(p);
			}
		}
		
		entryListView.setItems(temp);
	}
	
	/**
	 * Exports all entries to a CSV file specified by the user.
	 */
	protected void doExport() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Export To");
		chooser.setInitialFileName("passwords.csv");
		chooser.getExtensionFilters().add( new ExtensionFilter("CSV (Comma separated values)", "*.csv") );
		
		File output = chooser.showSaveDialog(getStage());
		if (output == null) {
			return;
		}
		
		if (!output.getName().endsWith(".csv")) {
			output = new File(output.getAbsolutePath() + ".csv");
		}
		
		try (FileWriter writer = new FileWriter(output)) {
			
			for (PasswordEntry p : entryCollection) {
				writer.write(p.toString() + "\n");
			}
			
			setStatusMessage("Exported to: " + output.getAbsolutePath());
		}
		catch (IOException e) {
			setStatusMessage("Export Failed!");
		}
	}

}
