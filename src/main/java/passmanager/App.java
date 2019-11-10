package passmanager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import passmanager.signuplogin.signupLoginModel;

public class App extends Application {
	private passmanager.signuplogin.signupLoginModel signupLoginModel = new signupLoginModel();
	private final static Logger Log = Util.getLogger(App.class);

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		if(signupLoginModel.count() > 0)
		{
			Parent root = FXMLLoader.load(getClass().getResource("/layout/login.fxml"));
			newScene(primaryStage, root);
		}
		else {
			Parent root = FXMLLoader.load(getClass().getResource("/layout/signup.fxml"));
			newScene(primaryStage, root);
		}

		Log.info("Data path: " + App.getStoragePath().getPath());
	}

	private void newScene(Stage primaryStage, Parent root) {
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/style/app.css").toExternalForm());
		primaryStage.getIcons().add(new Image("/Icons/lock.png"));
		primaryStage.setTitle("Password Manager");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}


	/**
	 * In this application, data is stored in the same directory as the jarfile. 
	 * @return URL
	 */
	public static URL getStoragePath() {
		return App.class.getProtectionDomain().getCodeSource().getLocation();
	}
	
	/**
	 * Returns the path to a specific file in the storage directory.
	 * @param String 	name of the file
	 * @return URL
	 * @throws MalformedURLException 
	 */
	public static URL getStoragePath(String filename)  {
		URL u = null;
		try {
			u = new URL(App.getStoragePath(), filename);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return u;
	}

}
