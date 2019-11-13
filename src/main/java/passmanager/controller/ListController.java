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
	@FXML TextField website, displayWebsite;
	@FXML TextField username, displayUsername;
	@FXML TextField password, DisplayPassword;
	@FXML PasswordField hiddenPassword;
	@FXML TextArea notes, DisplayNotes;
	@FXML TitledPane newPassword;
	@FXML MenuItem choicePreferences;
	@FXML MenuItem choiceQuit;
	@FXML Button DisplaySave;
	@FXML CheckBox edit;
	/**
	 * used to prompt user to create new password
	 * 
	 * @param event
	 */
	/**
	 * used to process the different choices in the menu bar
	 * @param event
	 */
	public void onMenu(Event event) throws IOException {
		// i don't think this is strictly necessary, but i just copied
		// what dennis did to be safe
		if (event.getSource() == choiceNew) {
			loadNewEntry("/layout/newEntries.fxml");
			Log.info("New Selected, inputting new entry!");
		}
		if (event.getSource() == choicePreferences) {
			loadNewEntry("/layout/settings.fxml");
			Log.info("Preferences selected");
		}

		if (event.getSource() == choiceQuit) {
			Log.info("Program closed");
			stage.close();
		}
	}
}
