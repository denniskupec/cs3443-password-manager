package passmanager.component;

import java.io.IOException;
import java.sql.SQLException;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import passmanager.Database;
import passmanager.model.PasswordEntry;

/**
 * Custom ListCell component for the main entry list
 */
public class EntryListCellController {

	@FXML Label title;
	@FXML ImageView favicon;
	@FXML HBox hbox;
	
	public EntryListCellController(EntryListCell cell) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/component/EntryListCell.fxml"));
			loader.setController(this);
			hbox = loader.load();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		cell.setGraphic(hbox);
		cell.selectedProperty().addListener(this::onSelected);
		cell.addEventHandler(ActionEvent.ACTION, this::onActionEvent);
	}

	/**
	 * Called when a list item is selected.
	 * @param observable
	 * @param oldValue
	 * @param newValue
	 */
	protected void onSelected(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		/* Intentionally left blank */
	}
	
	/**
	 * Called when a cell is clicked. Loads the correct data into the scene.
	 * @param event
	 */
	protected void onActionEvent(ActionEvent event) {
		EntryListCell cell = (EntryListCell) event.getTarget();
		PasswordEntry entry = null;
		
		try {
			entry = Database.getDao(PasswordEntry.class).queryForId(cell.getIndex());
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	/**
	 * Sets the fields and handles favicon retrieval.
	 * @param item
	 */
	public void setData(PasswordEntry item) {
		title.setText(item.getTitle());
		
		if (item.getFavicon() == null) {
			favicon.setImage(new Image(getClass().getResourceAsStream("/icon/default-favicon.png")));
		}
		else {
			favicon.setImage(item.getFavicon());
		}
	}

}
