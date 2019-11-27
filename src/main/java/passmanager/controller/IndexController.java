package passmanager.controller;

import com.j256.ormlite.dao.Dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
		
		Dao<PasswordEntry, Integer> pdao = Database.getDao(PasswordEntry.class);
		for (PasswordEntry entry : pdao) {
			entryCollection.add(entry);
		}

		entryListView.setItems( entryCollection );
		entryListView.setCellFactory(listview -> new EntryListCell() );
		entryListView.setOnMouseClicked(this::onListClicked);
		
		setStatusMessage("Loaded " + entryCollection.size() + " entries.");
	}

	protected void onListClicked(MouseEvent event) {
		PasswordEntry data = entryListView.getSelectionModel().getSelectedItem();
		
		if (data != null) {
			entryDetail.setData(data);
		}
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
			/* Shouldn't normally be able to get here. */
			break;
		}
	}
	
	public void setStatusMessage(String msg) {
		statusMessage.setText(msg);
	}
}
