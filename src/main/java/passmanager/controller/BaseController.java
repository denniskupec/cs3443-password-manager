package passmanager.controller;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import passmanager.App;
import passmanager.Util;

public abstract class BaseController {

	private final static Logger Log = Util.getLogger(BaseController.class);
	
	protected static Stage stage;
	protected Parent root;
	protected Scene scene;
	
	public static void setup(Stage primaryStage, String title) {
		stage = primaryStage;
		stage.setTitle(title);
		stage.setResizable(false);
	}

	/**
	 * This method handles the loading of scenes. The object returned is always an instance of the controller
	 * associated with a scene which extends this BaseController. 
	 * @param resourceName		name of the scene to load
	 * @return T 				instance of the controller for the scene
	 */
	public static <T extends BaseController> T loadScene(String resourceName) {
		URL resource = App.class.getResource(resourceName);
		FXMLLoader loader = new FXMLLoader(resource);

		try {
			Parent root = loader.load();
			
			Scene s = new Scene(root, 970, 600);
				s.getStylesheets().add(App.class.getResource("/style/app.css").toString());
			
			T controller = loader.getController();
				controller.root = root;
				controller.scene = s;
				controller.stage.setScene(s);
			
			Log.info("Loaded controller: " + controller.getClass().getName());
			
			return controller;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setTitle(String title) {
		BaseController.stage.setTitle(title);
	}

	public boolean isFocused() {
		return stage.isFocused();
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public void setScene(Scene newScene) {
		this.scene = newScene;
	}
	
}
