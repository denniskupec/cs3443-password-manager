package passmanager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import passmanager.controller.BaseController;

public class App extends Application {

	private final static Logger Log = Util.getLogger(App.class);

	public static void main(String[] args) {
		launch(args);
		Log.info("Program closed");
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.setup(primaryStage, "Password Manager");
		BaseController.loadScene(Database.exists() ? "/layout/login.fxml" : "/layout/firstrun.fxml");
		primaryStage.show();
		
		Log.info("Data path: " + App.getStoragePath());
	}

	/**
	 * Configuration and database files are stored in the user home directory.
	 * (e.g. ~/.config/CS3443-passmanager)
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
	 * Get the path of a resource file. 
	 * @param String name 		name of the resource file requested
	 * @return Path
	 * @throws URISyntaxException
	 */
	public static Path getResourcePath(String name) {
		try {
			return Paths.get( App.class.getResource(name).toURI() );
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		Log.warning("Couldn't find the resource: " + name);
		return null;
	}

}
