package passmanager.controller;

import java.util.logging.Logger;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import passmanager.Util;

public class ListController extends BaseController {

	private final static Logger Log = Util.getLogger(ListController.class);
	
	@FXML TextField website;
	@FXML TextField username;
	@FXML TextField password;
	@FXML TitledPane newPassword;
	@FXML MenuItem choiceNew;
	@FXML Button save;
	
	public void onNewEntry(Event event) {
		
		newPassword.setVisible(false);
		
		if (event.getSource() == choiceNew) {
			newPassword.setVisible(true);
			
		}
		
		if(event.getSource() == save) {
			Log.info("New Password Saved!");
			newPassword.setVisible(false);
		}
		
	}
	
}
