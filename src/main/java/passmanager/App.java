package passmanager;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import passmanager.controller.AppController;

public class App extends Application {

	private final static Logger log = Util.getLogger(App.class);
	private AppController app = new AppController();

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent login = FXMLLoader.load(getClass().getResource("/layout/login.fxml"));
			Parent newUser = FXMLLoader.load(getClass().getResource("/layout/newUser.fxml"));

			Scene scene = new Scene(login, 1133, 700);
				  scene.getStylesheets().add(getClass().getResource("/style/app.css").toExternalForm());
			
			primaryStage.setTitle("Password Manager");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
