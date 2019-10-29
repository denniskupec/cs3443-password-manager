package passmanager.controller;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AppController {
	
	private String login = "/layout/login.fxml";
	private String newUser = "/layout/newUser.fxml";
	private String current = "/layout/login.fxml";
	private String username, password;
	@FXML
	public Button btnNewUser;
	@FXML
	public Button btnLogin;
	@FXML
	public Button btnUnlock;
	@FXML
	public TextField usernameText;
	@FXML
	public TextField passwordText;
	@FXML
	public PasswordField enteredPassword;

//	public AppController(Stage primaryStage) {
//
//	}
	
	@FXML
	public void processLogin(ActionEvent event) throws Exception{
		
		Stage updateStage;
	    Parent updated;
		
		if(event.getSource() == btnNewUser){
			
			current = newUser;
	        updateStage = (Stage)btnNewUser.getScene().getWindow();
	        updated = FXMLLoader.load(getClass().getResource(current));
	        Scene scene = new Scene(updated, 700, 500);
	        updateStage.setScene(scene);
	        updateStage.show();
	        
	    }
		else if(event.getSource() == btnUnlock && enteredPassword.getText().equals(password)) {
			
			System.out.println("password accepted");
//			System.out.println(enteredPassword.getText());
			
		}
		
	}

	@FXML
	public void processNewUser(ActionEvent event) throws Exception{
		
		Stage updateStage;
	    Parent updated;
		
		if (event.getSource() == btnLogin) {
			
			username = usernameText.getText();
			password = passwordText.getText();
			
			current = login;
	        updateStage = (Stage)btnLogin.getScene().getWindow();
	        updated = FXMLLoader.load(getClass().getResource(current));
	        Scene scene = new Scene(updated, 1133, 700);
	        updateStage.setScene(scene);
	        updateStage.show();
			
		}
		
	}
	
}
