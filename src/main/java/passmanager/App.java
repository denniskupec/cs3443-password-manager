package passmanager;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private final static Logger Log = Util.getLogger(App.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/layout/login.fxml"));

		Scene scene = new Scene(root, 700, 500);
			scene.getStylesheets().add(getClass().getResource("/style/app.css").toExternalForm());
			  
		primaryStage.setTitle("Password Manager");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		Log.info("Data path: " + App.getStoragePath().getPath());
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
	
	/**
	 * Convenience method to fetch resource files.
	 * @param String	name of the resource file
	 * @return InputStream
	 */
	public static InputStream getResource(String name) {
		InputStream is = App.class.getResourceAsStream(name);
		if (is == null) {
			throw new MissingResourceException("Requested resource doesn't exist.", App.class.getName(), name);
		}
		
		return is;
	}


}
