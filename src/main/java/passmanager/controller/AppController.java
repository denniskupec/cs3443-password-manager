package passmanager.controller;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AppController {
	
	private String login = "/layout/login.fxml";
	private String newUser = "/layout/newUser.fxml";
	private String current = "/layout/login.fxml";
	@FXML
	public Button btnNewUser;

//	public AppController(Stage primaryStage) {
//
//	}
	
	@FXML
	public void processLogin(ActionEvent event) throws Exception{
		
		Stage updateStage;
	    Parent updated;
	
		String value = ((Button)event.getSource()).getText();
		
		if(event.getSource()==btnNewUser){
			
			current = newUser;
	        updateStage = (Stage)btnNewUser.getScene().getWindow();
	        updated = FXMLLoader.load(getClass().getResource(current));
	        Scene scene = new Scene(updated, 700, 500);
	        updateStage.setScene(scene);
	        updateStage.show();
	        
	    }
		
		
	}

}
