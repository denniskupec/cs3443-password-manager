package passmanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import passmanager.Database;
import passmanager.Util;

public class addEntriesController extends BaseController {

    private final static Logger Log = Util.getLogger(addEntriesController.class);

    @FXML private TextField website;
    @FXML private TextField userName;
    @FXML private TextField passWord;
    @FXML private TextField confirmPass;
    @FXML private TextArea note;
    @FXML private TextField url;
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private Label error;
    @FXML private Label choose;
    @FXML private ImageView img;
    private String imageFile;

    File file;
    /**
     * used to prompt user to create entries
     *
     * @param event handles addEntries
     */
    @FXML
    public void addEntries(ActionEvent event) throws Exception {
        if(!validateFields())
        {
            if(validatePassword(passWord.getText(), confirmPass.getText()))
            {
                addEntriesToDatabase(website.getText(),url.getText(), userName.getText(), passWord.getText().getBytes(), note.getText());
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();
                Log.info("New entry added to the database!");
            }
            else
            {
                error.setTextFill(Color.web("#ff0000"));
                error.setText("Password do not match!");
            }
        }
        else
        {
            error.setTextFill(Color.web("#ff0000"));
            error.setText("one or more field is empty!");
        }
    }
    
    /**
     * adds entries to the database
     *
     * @param  website,  url,  username,  password,  note
     */
    public void addEntriesToDatabase(String website, String url, String username, byte[] password, String note) throws Exception {
        Connection connection = Database.connect();
        FileInputStream fileInputStream = new FileInputStream(file);
        String sql = "INSERT INTO entries (updated_at,title, username, password, url, note, favicon) VALUES (datetime(),?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, website);
        ps.setString(2, username);
        ps.setBytes(3, Util.sha256(password));
        ps.setString(4, url);
        ps.setString(5, note);
        ps.setBinaryStream(6, fileInputStream, fileInputStream.available());
        ps.executeUpdate();
        ps.close();
    }
    
    /**
     * handles cancel button
     *
     */
    @FXML
    public void cancel()
    {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void addIcon() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(choose.getScene().getWindow());
        if(file != null)
        {
            imageFile = file.toURI().toURL().toString();
            Image image = new Image(imageFile);
            img.setImage(image);
            choose.setTextFill(Color.web("black"));
            choose.setText("Choose Image");
        }
        else {
            choose.setTextFill(Color.web("#ff0000"));
            choose.setText("Please select a icon for your account website!");
        }
    }
    /**
     * used to validate input fields
     *
     * @return true or false (i.e if one of the fields are empty it returns true)
     */
    private boolean validateFields() {
        return (userName.getText() == null || userName.getText().length() == 0 ||
                passWord.getText() == null || passWord.getText().length() == 0 ||
                confirmPass.getText() == null || confirmPass.getText().length() == 0 ||
                website.getText() == null || website.getText().length() == 0 ||
                url.getText() == null || website.getText().length() == 0 ||
                note.getText() == null || note.getText().length() == 0 ||
                file == null);
    }
    
    /**
     * used to validate if passwords match
     *
     * @return true or false (i.e if one of the fields are empty it returns true)
     */
    private boolean validatePassword(String password, String confirmPass) {
        return password.equals(confirmPass);
    }
}
