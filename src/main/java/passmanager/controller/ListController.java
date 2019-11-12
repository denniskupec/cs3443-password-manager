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
<<<<<<< HEAD
	@FXML MenuItem choiceNew;
	@FXML Button save;
=======
	
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
	
>>>>>>> 0d62c75a3cc2bce75d634552a189fb4339333e3c
	/**
	 * used to prompt user to create new password
	 * 
	 * @param event
	 */
<<<<<<< HEAD
	public void onNewEntry(Event event) throws IOException {
=======
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
		
>>>>>>> 0d62c75a3cc2bce75d634552a189fb4339333e3c
		// i don't think this is strictly necessary, but i just copied
		// what dennis did to be safe
		if (event.getSource() == choiceNew) {
<<<<<<< HEAD
			Stage update = new Stage();
			Parent root;
			String addAccount = "/layout/newEntries.fxml";
			root = FXMLLoader.load(getClass().getResource(addAccount));
			update.setScene(new Scene(root));
			update.setResizable(false);
			update.initModality(Modality.APPLICATION_MODAL);
			update.show();
		}
=======
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
>>>>>>> 0d62c75a3cc2bce75d634552a189fb4339333e3c
	}
	
}
