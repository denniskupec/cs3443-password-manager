package passmanager.controller;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import passmanager.SignupGettersSetters;

import java.io.*;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppController {
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

    private boolean confirmClicked = false;
    public boolean isConfirmClicked()
    {
        return confirmClicked;
    }

    public SignupGettersSetters getUser()
    {
        if(confirmClicked)
        {
            return new SignupGettersSetters(username.getText(), password.getText());
        }
        else {
            return null;
        }
    }
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
                save(user, pass);
                System.out.println(user);
                System.out.println(pass);
                confirmClicked = true;
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

    void save(String name, String password) throws Exception {
        FileOutputStream user_file = new FileOutputStream("./src/main/resources/users/"+name+".csv");
        PrintWriter pw = new PrintWriter(user_file);
        pw.println(name + "," + password);
        pw.close();
    }





}