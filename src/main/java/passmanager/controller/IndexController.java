package passmanager.controller;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import java.sql.SQLException;
=======
=======
>>>>>>> develop
=======
>>>>>>> develop
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.Date;
<<<<<<< HEAD

>>>>>>> develop
=======
>>>>>>> develop
=======
>>>>>>> develop
import com.j256.ormlite.dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
>>>>>>> develop
=======
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
>>>>>>> develop
=======
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
>>>>>>> develop
import passmanager.Database;
import passmanager.component.EntryDetailController;
import passmanager.component.EntryListCell;
import passmanager.interfaces.Initializable;
import passmanager.model.PasswordEntry;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import passmanager.model.Settings;
>>>>>>> develop
=======
import passmanager.model.Settings;
>>>>>>> develop
=======
import passmanager.model.Settings;
import passmanager.Util;
>>>>>>> develop

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
	
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
	Date lastActive;
	Settings settings;
>>>>>>> develop
=======
	Date lastActive;
	Settings settings;
>>>>>>> develop
=======
	Settings settings;
>>>>>>> develop
	EntryDetailController entryDetail;
	ObservableList<PasswordEntry> entryCollection = FXCollections.observableArrayList();

	/**
	 * Called by FXMLLoader
	 */
	@Override
	public void initialize() {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		entryDetail = new EntryDetailController();
		
=======
=======
>>>>>>> develop
=======
>>>>>>> develop
		try {
			settings = Database.getDao(Settings.class).queryForId(1);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		entryDetail = new EntryDetailController();
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
		splitPane.getItems().add( entryDetail.getBox() );
		
		/* fill our collection of entries */
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}
		entryListView.setItems( entryCollection );
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> develop
		
		if (entryCollection.isEmpty()) {
			setStatusMessage("Empty database. Try adding a new item.");
			entryDetail.setData(null);
		}
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop

		/* this updates the detail pane with the correct model when a list item is selected/clicked */
		entryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> entryDetail.setData(newValue));
		
		entryListView.setCellFactory(listview -> new EntryListCell());
		entryListView.getSelectionModel().selectFirst();

		/* register any callbacks */
		entryDetail.setDeleteCallback(this::doDeleteCallback);
		
<<<<<<< HEAD
<<<<<<< HEAD
		entryDetail.setEditCallback(() -> entryListView.setDisable(true));
		
		entryDetail.setCancelCallback(() -> {
			entryListView.setDisable(false);
=======
=======
>>>>>>> develop
		entryDetail.setEditCallback(() -> {
			searchButton.setDisable(true);
			searchText.setDisable(true);
			entryListView.setDisable(true);
		});
		
		entryDetail.setCancelCallback(() -> {
			entryListView.setDisable(false);
			searchButton.setDisable(false);
			searchText.setDisable(false);
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
			entryDetail.setData(entryListView.getSelectionModel().getSelectedItem());
		});
		
		entryDetail.setSaveCallback(this::reload);
		
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> develop
=======
		
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
		
>>>>>>> develop
		searchButton.setOnMouseClicked(this::doSearch);
		searchText.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				searchText.clear();
<<<<<<< HEAD
=======
				entryListView.setItems(null);
>>>>>>> develop
				reload();
				break;
				
			case ENTER:
				doSearch(null);
				break;
				
<<<<<<< HEAD
			default:
				break;
			}
		});
		
		searchText.textProperty().addListener((observable, oldValue, newValue) -> {
=======
			default: break;
			}
		});
		
		searchText.textProperty().addListener((obs, oldValue, newValue) -> {
>>>>>>> develop
			if (!oldValue.isEmpty() && newValue.isEmpty()) {
				reload();
			}
		});
		
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
=======
		splitPane.getItems().add( entryDetail.getBox() );

>>>>>>> develop
		setStatusMessage("Loaded " + entryCollection.size() + " entries.");
	}
	
	/**
	 * Called when the 'delete' button is pressed when editing an entry.
	 * This probably should be moved to EntryDetailController.
	 */
	protected void doDeleteCallback() {
<<<<<<< HEAD
		PasswordEntry item = entryListView.getSelectionModel().getSelectedItem();
=======
		MultipleSelectionModel<PasswordEntry> sm = entryListView.getSelectionModel();
		PasswordEntry item = sm.getSelectedItem();
>>>>>>> develop
		
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
		
<<<<<<< HEAD
		setStatusMessage("Entry deleted.");
		
		entryListView.getSelectionModel().clearSelection();
		entryListView.getSelectionModel().selectNext();
		entryCollection.remove(item);
	}

<<<<<<< HEAD
<<<<<<< HEAD
=======
	/**
	 * Reloads the list of items from the database. Does not change selected item.
	 */
>>>>>>> develop
=======
	/**
	 * Reloads the list of items from the database. Does not change selected item.
	 */
>>>>>>> develop
=======
		sm.selectNext();
		entryCollection.remove(item);
		
		setStatusMessage("Entry deleted.");
	}

	/**
	 * Reloads the list of items from the database. If there are any items found, then the
	 * first is selected.
	 */
>>>>>>> develop
	public void reload() {
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		entryCollection.clear();
		
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}
		entryListView.setItems( entryCollection );
<<<<<<< HEAD
=======

		if (entryCollection.isEmpty()) {
			setStatusMessage("Empty database. Try adding a new item.");
			entryDetail.setData(null);
		}
		else {
			entryListView.getSelectionModel().selectFirst();
		}
>>>>>>> develop
	}

	/**
	 * used to process the different choices in the menu bar
<<<<<<< HEAD
	 * 
=======
>>>>>>> develop
	 * @param ActionEvent event
	 */
	public void onMenuClick(ActionEvent event) {
		MenuItem menuItem = (MenuItem) event.getTarget();

		switch (menuItem.getText()) {
		case "Logout":
			loadScene("/layout/login.fxml");
			break;
			
<<<<<<< HEAD
<<<<<<< HEAD
		case "Export":
<<<<<<< HEAD
			// TODO: Export functionality
=======
			doExport();
>>>>>>> develop
=======
		case "Export CSV":
			doExport();
>>>>>>> develop
=======
		case "Export CSV":
			doExport();
>>>>>>> develop
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

<<<<<<< HEAD
		default:
			/* Shouldn't normally be able to get here. */
=======
		default: /* Shouldn't normally be able to get here. */
>>>>>>> develop
		}
	}

	/**
	 * Sets the status message on the bottom right of the window.
	 * @param String msg
	 */
	public void setStatusMessage(String msg) {
		statusMessage.setText(msg);
	}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> develop
	
	/**
	 * Searches the current entry list collection for a title containing a keyword, and sets the entry list to display only the found items.
=======
	
	/**
	 * Searches the current entry list collection for a title containing a keyword, and 
	 * sets the entry list to display only the found items.
>>>>>>> develop
	 * @param MouseEvent event
	 */
	protected void doSearch(MouseEvent event) {
		String searchString = searchText.getText();
		if (searchString.isEmpty()) {
			return;
		}
		
<<<<<<< HEAD
		ObservableList<PasswordEntry> temp = FXCollections.observableArrayList();

		for (PasswordEntry p : entryCollection) {
			if (p.getTitle().contains(searchString)) {
				temp.add(p);
			}
		}
		
		entryListView.setItems(temp);
=======
		ObservableList<PasswordEntry> filtered = entryCollection.filtered(entry -> entry.getTitle().contains(searchString));
		entryListView.setItems(filtered);
>>>>>>> develop
	}
	
	/**
	 * Exports all entries to a CSV file specified by the user.
	 */
	protected void doExport() {
		FileChooser chooser = new FileChooser();
<<<<<<< HEAD
<<<<<<< HEAD
=======
		chooser.setTitle("Export To");
		chooser.setInitialFileName("passwords.csv");
		chooser.getExtensionFilters().add( new ExtensionFilter("CSV (Comma separated values)", "*.csv") );
>>>>>>> develop
=======
		chooser.setTitle("Export To");
		chooser.setInitialFileName("passwords.csv");
		chooser.getExtensionFilters().add( new ExtensionFilter("CSV (Comma Separated Values)", "*.csv") );
>>>>>>> develop
		
		File output = chooser.showSaveDialog(getStage());
		if (output == null) {
			return;
		}
		
<<<<<<< HEAD
=======
		// makes sure the output file has the 'csv' extension
>>>>>>> develop
		if (!output.getName().endsWith(".csv")) {
			output = new File(output.getAbsolutePath() + ".csv");
		}
		
		try (FileWriter writer = new FileWriter(output)) {
<<<<<<< HEAD
			
			for (PasswordEntry p : entryCollection) {
<<<<<<< HEAD
				writer.write(p.toString());
				writer.write(System.lineSeparator());
=======
				writer.write(p.toString() + "\n");
>>>>>>> develop
=======

			for (PasswordEntry p : entryCollection) {
				writer.write(p.toString() + "\n");
>>>>>>> develop
			}
			
			setStatusMessage("Exported to: " + output.getAbsolutePath());
		}
		catch (IOException e) {
			setStatusMessage("Export Failed!");
		}
	}
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
=======
>>>>>>> develop

}
