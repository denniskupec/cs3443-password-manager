package passmanager;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import passmanager.controller.AppController;

public class App extends Application {

	private final static Logger log = Util.getLogger(App.class);
//	private AppController app = new AppController();
	private int sceneMarker;
	private String login = "/layout/login.fxml";
	private String newUser = "/layout/newUser.fxml";
	private String current = "/layout/login.fxml";
	

	@Override
	public void start(Stage primaryStage) throws Exception{
		
			Parent root = FXMLLoader.load(getClass().getResource(current));

			Scene scene = new Scene(root, 1133, 700);
				  scene.getStylesheets().add(getClass().getResource("/style/app.css").toExternalForm());
				  
				  
			primaryStage.setTitle("Password Manager");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		
	}


	public static void main(String[] args) {
		launch(args);
	}

}
