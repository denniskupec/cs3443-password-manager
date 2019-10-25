package passmanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;

public class AppController {
	
	
	private int sceneMarker;
	
	@FXML
	public void processLogin(ActionEvent event) {
	
		String value = ((Button)event.getSource()).getText();
		
		if(value.equals("New User")) sceneMarker = 1;
		
		else if(value.equals("Unlock")) sceneMarker = 2;
		
	}
	
	@FXML
	public int currentScene() {
		
		return sceneMarker;
	}
	
}
