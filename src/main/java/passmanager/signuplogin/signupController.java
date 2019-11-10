package passmanager.signuplogin;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class signupController {
    signupLoginModel signupLoginModel = new signupLoginModel();
    @FXML
	private Button signup;
	@FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmpass;
    @FXML
    private Label errorLabel;
    @FXML
    private Hyperlink login;

    @FXML
    public void Login(ActionEvent event) throws Exception{
        Stage updateStage;
        Parent updated;
        if(event.getSource()==login){
            updateStage=(Stage)login.getScene().getWindow();
            String login = "/layout/login.fxml";
            updated = FXMLLoader.load(getClass().getResource(login));
            Scene scene=new Scene(updated);
            updateStage.setScene(scene);
            updateStage.show();
        }
    }

	@FXML
	public void Signup(ActionEvent event) throws Exception{
        if(validateFields())
        {
            errorLabel.setTextFill(Color.web("#ff0000"));
            errorLabel.setText("one or more field is empty!");
        }
        else {
            if(validatePassword(password.getText(), confirmpass.getText()))
            {
                String user = username.getText();
                String pass = password.getText();
                signupLoginModel.addUser(user, pass);
                System.out.println(user);
                System.out.println(pass);
                Stage updateStage;
                Parent updated;
                if(event.getSource()==signup){
                    updateStage=(Stage)signup.getScene().getWindow();
                    String signup = "/layout/accounts.fxml";
                    updated=FXMLLoader.load(getClass().getResource(signup));
                    Scene scene=new Scene(updated);
                    updateStage.setScene(scene);
                    updateStage.show();
                }
            }
            else
            {
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setText("Password does not match!");
            }
        }
	}

    private boolean validateFields() {
        return (username.getText() == null || username.getText().length() == 0 ||
                password.getText() == null || password.getText().length() == 0 ||
                confirmpass.getText() == null || confirmpass.getText().length() == 0);
    }

    private boolean validatePassword(String password, String confirmpass) {
        return password.equals(confirmpass);
    }
}