package passmanager.controller;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import passmanager.*;


public class ListController extends BaseController {
	private final static Logger Log = Util.getLogger(ListController.class);
	
	@FXML Label statusMessage;
	@FXML Button btnSearch;
	@FXML TextField textfieldSearch;
	@FXML Button btnEdit;
	@FXML ImageView favicon;
	@FXML Label title;
}
