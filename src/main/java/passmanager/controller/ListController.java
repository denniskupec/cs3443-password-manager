package passmanager.controller;

import java.util.logging.Logger;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import passmanager.Util;

public class ListController extends BaseController {

	private final static Logger Log = Util.getLogger(ListController.class);
	
	@FXML TextField website, displayWebsite;
	@FXML TextField username, displayUsername;
	@FXML TextField password, DisplayPassword;
	@FXML TextArea notes, DisplayNotes;
	@FXML TitledPane newPassword;
	@FXML MenuItem choiceNew;
	@FXML MenuItem choicePreferences;
	@FXML MenuItem choiceQuit;
	@FXML Button save, DisplaySave;
	@FXML CheckBox edit;
	
	/**
	 * used to prompt user to create new password
	 * 
	 * @param event
	 */
	public void onNewEntry(Event event) {
		
		if(event.getSource() == save) {
			Log.info("New Password Saved!");
			newPassword.setVisible(false);
		}
		
	}
	
	/**
	 * used to process the different choices in the menu bar
	 * @param event
	 */
	public void onMenu(Event event) {
		
		// i don't think this is strictly necessary, but i just copied
		// what dennis did to be safe
		newPassword.setVisible(false);
		
		if (event.getSource() == choiceNew) {
//			newPassword.setVisible(true);
			Log.info("New Selected, inputting new password");
//			onNewEntry(event);
			loadNewEntry("/layout/newentry.fxml");
		}
		
		if (event.getSource() == choicePreferences) {
			Log.info("Preferences selected");
		}
		
		if (event.getSource() == choiceQuit) {
			Log.info("Quit selected");
		}
	}
	
}
