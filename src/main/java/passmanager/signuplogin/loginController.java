package passmanager.signuplogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    databaseModel databaseModel = new databaseModel();
    @FXML
    private TextField password;
    @FXML
    private Label errorLabel;
    @FXML
    private Button login;
    @FXML
    private Hyperlink backtoSignup;
    @FXML
    private Label status;
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            if(databaseModel.count() > 0)
            {
                backtoSignup.setDisable(true);
            }
            else{
                backtoSignup.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(this.databaseModel.isConnected())
        {
            this.status.setText("Connected");
        }
        else
        {
            this.status.setText("Failed!");
        }
    }
    @FXML
    public void Login(ActionEvent event) throws Exception{
        if(validateField())
        {
            errorLabel.setTextFill(Color.web("#ff0000"));
            errorLabel.setText("Please enter password to continue!");
        }
        else
        {
            String pass = password.getText();
            if (databaseModel.validatePassword(pass)) {
                Stage updateStage;
                Parent updated;
                if(event.getSource()==login){
                    updateStage=(Stage)login.getScene().getWindow();
                    String signup = "/layout/accounts.fxml";
                    updated= FXMLLoader.load(getClass().getResource(signup));
                    Scene scene=new Scene(updated);
                    updateStage.setScene(scene);
                    updateStage.show();
                    try {
                        databaseModel.connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setText("invalid password!");
            }
        }
    }
    private boolean validateField() {
        return (password.getText() == null || password.getText().length() == 0);
    }
    @FXML
    public void signup(ActionEvent event) throws Exception{
        Stage updateStage;
        Parent updated;
        if(event.getSource()==backtoSignup){
            updateStage=(Stage)backtoSignup.getScene().getWindow();
            String login = "/layout/signup.fxml";
            updated = FXMLLoader.load(getClass().getResource(login));
            Scene scene=new Scene(updated);
            updateStage.setScene(scene);
            updateStage.show();
        }
    }
}

