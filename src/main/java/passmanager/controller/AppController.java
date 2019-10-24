package passmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AppController {
	
	
	@FXML
	public void processLogin(ActionEvent event) {
	
		String value = ((Button)event.getSource()).getText();
		
		if(value.equals("New User")) System.out.println("you need to make a new username");
		
		else if(value.equals("Unlock")) System.out.println("you clicked unlock");
		
	}
	
}
