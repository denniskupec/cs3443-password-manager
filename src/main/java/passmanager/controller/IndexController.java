package passmanager.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
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
import passmanager.Util;

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
		
		// this updates the detail pane with the correct model when a list item is selected/clicked
		entryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> entryDetail.setData(newValue));
		entryListView.setCellFactory(listview -> new EntryListCell());

		/* fill our collection of entries */
		reload();

		// register any callbacks
		entryDetail.setDeleteCallback(this::doDeleteCallback);
		entryDetail.setSaveCallback(this::reload);
		entryDetail.setEditCallback(() -> Util.setDisabled(true, searchButton, searchText, entryListView));
		
		entryDetail.setCancelCallback(() -> {
			Util.setDisabled(false, searchButton, searchText, entryListView);
			entryDetail.setData( entryListView.getSelectionModel().getSelectedItem() );
		});
		
		searchButton.setOnMouseClicked(this::doSearch);
		searchText.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				searchText.clear();
				entryListView.setItems(null);
				reload();
				break;
				
			case ENTER:
				doSearch(null);
				break;
				
			default: break;
			}
		});
		
		searchText.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!oldValue.isEmpty() && newValue.isEmpty()) {
				reload();
			}
		});
		
		splitPane.getItems().add( entryDetail.getBox() );

		setStatusMessage("Loaded " + entryCollection.size() + " entries.");
	}
	
	/**
	 * Called when the 'delete' button is pressed when editing an entry.
	 * This probably should be moved to EntryDetailController.
	 */
	protected void doDeleteCallback() {
		MultipleSelectionModel<PasswordEntry> sm = entryListView.getSelectionModel();
		PasswordEntry item = sm.getSelectedItem();
		
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
		
		sm.selectNext();
		entryCollection.remove(item);
		
		setStatusMessage("Entry deleted.");
	}

	/**
	 * Reloads the list of items from the database. If there are any items found, then the
	 * first is selected.
	 */
	public void reload() {
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		entryCollection.clear();
		
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}
		entryListView.setItems( entryCollection );

		if (entryCollection.isEmpty()) {
			setStatusMessage("Empty database. Try adding a new item.");
			entryDetail.setData(null);
		}
		else {
			entryListView.getSelectionModel().selectFirst();
		}
	}

	/**
	 * used to process the different choices in the menu bar
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

		default: /* Shouldn't normally be able to get here. */
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
	 * Searches the current entry list collection for a title containing a keyword, and 
	 * sets the entry list to display only the found items.
	 * @param MouseEvent event
	 */
	protected void doSearch(MouseEvent event) {
		String searchString = searchText.getText();
		if (searchString.isEmpty()) {
			return;
		}
		
		ObservableList<PasswordEntry> filtered = entryCollection.filtered(entry -> entry.getTitle().contains(searchString));
		entryListView.setItems(filtered);
	}
	
	/**
	 * Exports all entries to a CSV file specified by the user.
	 */
	protected void doExport() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Export To");
		chooser.setInitialFileName("passwords.csv");
		chooser.getExtensionFilters().add( new ExtensionFilter("CSV (Comma Separated Values)", "*.csv") );
		
		File output = chooser.showSaveDialog(getStage());
		if (output == null) {
			return;
		}
		
		// makes sure the output file has the 'csv' extension
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
