package passmanager.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import passmanager.signuplogin.databaseModel;

public class testController {
    databaseModel databaseModel = new databaseModel();
    @FXML
    private TextField website;
    @FXML
    private TextField url;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPass;
    @FXML
    private TextField email;
    @FXML
    private Label error;
    @FXML
    public void addAccount(ActionEvent event) throws Exception {
        if(!validateFields())
        {
        if(validatePassword(password.getText(), confirmPass.getText()))
        {
           databaseModel.addAccountToDatabase(website.getText(),"abit", "abit", "abit", "abit");
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
        return (username.getText() == null || username.getText().length() == 0 ||
                password.getText() == null || password.getText().length() == 0 ||
                confirmPass.getText() == null || confirmPass.getText().length() == 0 ||
                website.getText() == null || website.getText().length() == 0 ||
                url.getText() == null || website.getText().length() == 0 ||
                email.getText() == null || email.getText().length() == 0);
    }
    private boolean validatePassword(String password, String confirmpass) {
        return password.equals(confirmpass);
    }
}
