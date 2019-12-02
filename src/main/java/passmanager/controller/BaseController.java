package passmanager.controller;

import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
import java.util.logging.Logger;
=======
>>>>>>> develop
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import passmanager.*;
import passmanager.interfaces.*;
<<<<<<< HEAD

/**
 * All other controllers extend this controller. This provides the methods to switch scenes.
 */
public abstract class BaseController implements Initializable {
	private final static Logger Log = Util.getLogger(BaseController.class);

=======

/**
 * All other controllers extend this controller. This provides the methods to switch scenes.
 */
public abstract class BaseController implements Initializable {

>>>>>>> develop
	protected static Stage stage;
	protected Parent root;
	protected Scene scene;
	
	/**
	 * Only called when the application first launches!
	 * Sets the application stage and a title for the window.
	 * @param primaryStage
	 * @param title
	 */
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
	public static final <T extends BaseController> T loadScene(String resourceName) {
		URL resource = App.class.getResource(resourceName);
		FXMLLoader loader = new FXMLLoader(resource);
 
		try {
			Parent root2 = loader.load();
	
			Scene s = new Scene(root2, 800, 600);
<<<<<<< HEAD
				s.getStylesheets().add(App.class.getResource("/style/app.css").toString());
=======
			s.getStylesheets().add(App.class.getResource("/style/app.css").toString());
>>>>>>> develop

			T controller = loader.getController();
			controller.root = root2;
			controller.scene = s;
			stage.setScene(s);
				
			return controller;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final <T extends BaseController> T loadNewEntry(String resourceName) {
		URL resource = App.class.getResource(resourceName);
		FXMLLoader loader = new FXMLLoader(resource);
		Stage popStage = new Stage();

		try {
			Parent root2 = loader.load();
	

			Scene s = new Scene(root2);

			s.getStylesheets().add(App.class.getResource("/style/app.css").toString());

			T controller = loader.getController();
			controller.root = root2;
			controller.scene = s;
			popStage.getIcons().add(new Image("/icon/lock.png"));
			popStage.setScene(s);
			popStage.initModality(Modality.APPLICATION_MODAL);
			popStage.setResizable(false);
			popStage.show();
				
			return controller;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final <T extends BaseController> T loadNewEntry(String resourceName) {
		URL resource = App.class.getResource(resourceName);
		FXMLLoader loader = new FXMLLoader(resource);
		Stage popStage = new Stage();

		try {
			Parent root2 = loader.load();
	

			Scene s = new Scene(root2);

			s.getStylesheets().add(App.class.getResource("/style/app.css").toString());

			T controller = loader.getController();

				controller.root = root2;
				controller.scene = s;
				popStage.getIcons().add(new Image("/icon/lock.png"));
				popStage.setScene(s);
				popStage.initModality(Modality.APPLICATION_MODAL);
				popStage.setResizable(false);
				popStage.show();
				
			Log.info("Controller loaded: " + controller.getClass().getName());
			return controller;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Changes the main window title.
	 * @param title - string to set the main window title
	 */
	public void setTitle(String title) {
		BaseController.stage.setTitle(title);
	}

	/**
	 * Determines if the application window is in front.
	 * @return boolean - True if the window is in front, false otherwise.
	 */
	public boolean isFocused() {
		return stage.isFocused();
	}

	/**
	 * Returns the application stage
	 * @return Stage
	 */
	public static Stage getStage() {
		return stage;
	}
	
	/**
	 * Do not call this function outside of initialization!
	 * Sets the application stage.
	 * @param newStage
	 */
	public void setStage(Stage newStage) {
		stage = newStage;
	}
	
	/**
	 * Returns the currently displayed scene.
	 * @return Scene
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	/**
	 * Sets a new scene for the application stage.
	 * @param newScene
	 */
	public void setScene(Scene newScene) {
		stage.setScene(newScene);
	}
	
	/**
	 * Returns the root node.
	 * @return Parent
	 */
	public Parent getRoot() {
		return root;
	}
	
	/**
	 * Sets the root node. 
	 * @return Parent
	 */
	public void setRoot(Parent root) {
		this.root = root;
	}
}
