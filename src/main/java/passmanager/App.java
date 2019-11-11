package passmanager;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		
		URL resource = getClass().getResource(Database.exists() ? "/layout/login.fxml" : "/layout/newUser.fxml");
		
		Parent root = FXMLLoader.load(resource);

		Scene scene = new Scene(root, 700, 500);
			scene.getStylesheets().add(getClass().getResource("/style/app.css").toExternalForm());
			  
		primaryStage.setTitle("Password Manager");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		Log.info("Data path: " + App.getStoragePath());
	}


	/**
	 * Configuration and database files are stored in the user home directory
	 * @return Path
	 */
	public static Path getStoragePath() {
		return Paths.get(System.getProperty("user.home"), ".config", "CS3442-passmanager");
	}
	
	/**
	 * Returns the path to a specific file in the storage directory.
	 * @param String 	name of the file
	 * @return Path
	 */
	public static Path getStoragePath(String filename)  {
		return Paths.get(App.getStoragePath().toString(), filename);
	}
	
	/**
	 * Convenience method to fetch resource files.
	 * @param String name		name of the resource file
	 * @return InputStream
	 */
	public static InputStream getResource(String name) {
		InputStream is = App.class.getResourceAsStream("/" + name);
		if (is == null) {
			throw new MissingResourceException("Requested resource doesn't exist.", App.class.getName(), name);
		}
		
		return is;
	}
	
	/**
	 * Get the path of a resource file. 
	 * @param String name 		name of the resource file requested
	 * @return Path
	 */
	public static Path getResourcePath(String name) {
		return Paths.get(App.class.getResource(name).getPath(), name);
	}


	
	
}
