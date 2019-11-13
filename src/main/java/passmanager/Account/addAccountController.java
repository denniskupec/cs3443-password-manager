package passmanager.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import passmanager.App;
import passmanager.Util;
import passmanager.database.dbConnection;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

public class addAccountController {
    private final static Logger Log = Util.getLogger(App.class);
    @FXML
    private TextField website;
    @FXML
    private TextField url;
    @FXML
    private TextField userName;
    @FXML
    private TextField passWord;
    @FXML
    private TextField confirmPass;
    @FXML
    private TextField email;
    @FXML
    private Label error;
    @FXML
    private Button add;
    @FXML
    private Label choose;
    @FXML
    private Button cancel;
    @FXML
    private ImageView img;
    private String imageFile;
    File file;
    @FXML
    public void addAccount(ActionEvent event) throws Exception {
        if(!validateFields())
        {
            if(validatePassword(passWord.getText(), confirmPass.getText()))
            {
                addAccountToDatabase(website.getText(),url.getText(), userName.getText(), passWord.getText(), email.getText());
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
                Log.info("Account added!");
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
    private boolean validateFields() {
        return (userName.getText() == null || userName.getText().length() == 0 ||
                passWord.getText() == null || passWord.getText().length() == 0 ||
                confirmPass.getText() == null || confirmPass.getText().length() == 0 ||
                website.getText() == null || website.getText().length() == 0 ||
                url.getText() == null || website.getText().length() == 0 ||
                email.getText() == null || email.getText().length() == 0);
    }
    private boolean validatePassword(String password, String confirmpass) {
        return password.equals(confirmpass);
    }
    public void addAccountToDatabase(String website, String url, String username, String password, String email) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        Connection connection = dbConnection.getConnection();
        String sql = "INSERT INTO accounts (website, url, username, password, email,image) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, website);
        ps.setString(2, url);
        ps.setString(3, username);
        ps.setString(4, password);
        ps.setString(5, email);
        ps.setBinaryStream(6, fileInputStream, fileInputStream.available());
        ps.executeUpdate();
        ps.close();
    }
    @FXML
    public void cancel()
    {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void chooseFile() throws IOException{
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
}
