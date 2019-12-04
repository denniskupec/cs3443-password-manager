package passmanager;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.j256.ormlite.logger.LocalLog;
import passmanager.controller.BaseController;
import passmanager.model.Settings;

/**
 * Main class.
 */
public class App extends Application {
	private static final Logger Log = Util.getLogger(App.class);

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	private final static Logger Log = Util.getLogger(App.class);

	public static void main(String[] args) {
=======
=======
>>>>>>> develop
=======
>>>>>>> develop
	/**
	 * Program entry
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		/* makes ormlite logging less verbose */
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
		
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
=======
>>>>>>> develop
		launch(args);
		Database.close();
	}

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseController.setup(primaryStage, "Password Manager");
		BaseController.loadScene(Database.exists() ? "/layout/login.fxml" : "/layout/firstrun.fxml");
=======
	/**
	 * Program entry
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
>>>>>>> develop
		
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "INFO");
		
		launch(args);
		Database.close();
	}

	/**
	 * JavaFX entry
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.setup();
		
=======
	/**
	 * JavaFX entry
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.setupDemo(); // sets up the program to use the demo files
		//Database.setup();
		
>>>>>>> develop
=======
	/**
	 * JavaFX entry
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.setupDemo(); // sets up the program to use the demo files
		//Database.setup();
		
>>>>>>> develop
=======
	/**
	 * JavaFX entry
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.setupDemo(); // sets up the program to use the demo files
		//Database.setup();
		
>>>>>>> develop
		BaseController.setup(primaryStage, "Password Manager");

		if (Database.getDao(Settings.class).countOf() == 0) {
			BaseController.loadScene("/layout/firstrun.fxml");
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
		}
		else {
			BaseController.loadScene("/layout/login.fxml");
		}
=======
=======
>>>>>>> develop
=======
>>>>>>> develop
		}
		else {
			BaseController.loadScene("/layout/login.fxml");
		}
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> develop
=======
>>>>>>> develop
=======
>>>>>>> develop

		primaryStage.getIcons().add(new Image("/icon/password.png"));
		primaryStage.show();
		
		Log.info("Data path: " + Util.getStoragePath());
	}

}
