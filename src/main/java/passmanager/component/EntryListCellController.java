package passmanager.component;

import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
	}

	protected void onSelected(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		/* Intentionally left blank */
	}

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
