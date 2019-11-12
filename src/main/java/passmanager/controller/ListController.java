package passmanager.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import passmanager.Util;

public class ListController extends BaseController {

	private final static Logger Log = Util.getLogger(ListController.class);
	@FXML MenuItem choiceNew;
	@FXML Button save;
	/**
	 * used to prompt user to create new password
	 * 
	 * @param event
	 */
	public void onNewEntry(Event event) throws IOException {
		// i don't think this is strictly necessary, but i just copied
		// what dennis did to be safe
		if (event.getSource() == choiceNew) {
			Stage update = new Stage();
			Parent root;
			String addAccount = "/layout/newEntries.fxml";
			root = FXMLLoader.load(getClass().getResource(addAccount));
			update.setScene(new Scene(root));
			update.setResizable(false);
			update.initModality(Modality.APPLICATION_MODAL);
			update.show();
		}
	}
	
}
