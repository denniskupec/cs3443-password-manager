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

	/**
	 * Program entry
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
		
		launch(args);
		Database.close();
	}

	/**
	 * JavaFX entry
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.setup();
		
		BaseController.setup(primaryStage, "Password Manager");

		if (Database.getDao(Settings.class).countOf() == 0) {
			BaseController.loadScene("/layout/firstrun.fxml");
		}
		else {
			BaseController.loadScene("/layout/login.fxml");
		}

		primaryStage.getIcons().add(new Image("/icon/password.png"));
		primaryStage.show();
		
		Log.info("Data path: " + Util.getStoragePath());
	}

}
