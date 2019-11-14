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
	@FXML TextField DisplayWebsite;
	@FXML TextField DisplayUsername;
	@FXML TextField DisplayPassword;
	@FXML PasswordField hiddenPassword;
	@FXML TextArea DisplayNotes;
	@FXML MenuItem choiceSettings;
	@FXML MenuItem choiceQuit;
	@FXML Button DisplaySave;
	@FXML CheckBox edit;
	
	/**
	 * used to process the different choices in the menu bar
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
	
	public void onEditEntry(Event event) {
		if(event.getSource() == edit) {
			if(edit.isSelected()) {
				DisplayWebsite.setEditable(true);
				DisplayUsername.setEditable(true);
				DisplayPassword.setEditable(true);
				DisplayPassword.setVisible(true);
				hiddenPassword.setVisible(false);
				DisplayNotes.setEditable(true);
				DisplaySave.setVisible(true);
			}
			else {
				DisplayWebsite.setEditable(false);
				DisplayUsername.setEditable(false);
				DisplayPassword.setEditable(false);
				DisplayPassword.setVisible(false);
				hiddenPassword.setVisible(true);
				DisplayNotes.setEditable(false);
				DisplaySave.setVisible(false);
			}
		}
	}
}
