package passmanager.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javafx.stage.Modality;
import javafx.stage.Stage;
import passmanager.database.dbConnection;
import passmanager.signuplogin.databaseModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class addAccountController {
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
    public void addAccount(ActionEvent event) throws Exception {
        if(!validateFields())
        {
            if(validatePassword(passWord.getText(), confirmPass.getText()))
            {
                addAccountToDatabase(website.getText(),url.getText(), userName.getText(), passWord.getText(), email.getText());
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
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
        Connection connection = dbConnection.getConnection();
        String sql = "INSERT INTO accounts (website, url, username, password, email) VALUES (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, website);
        ps.setString(2, url);
        ps.setString(3, username);
        ps.setString(4, password);
        ps.setString(5, email);
        ps.executeUpdate();
        ps.close();
    }
}
