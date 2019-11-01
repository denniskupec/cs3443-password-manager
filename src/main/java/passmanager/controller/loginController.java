package passmanager.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class loginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label errorLabel;
    @FXML
    private Button login;
    @FXML
    private Hyperlink backtoSignup;


    @FXML
    public void Login(ActionEvent event) throws Exception{
        if(validateFields())
        {
            errorLabel.setTextFill(Color.web("#ff0000"));
            errorLabel.setText("one or more field is empty!");
        }
        else
        {
            String user = username.getText();
            String pass = password.getText();
            if(Files.exists(Paths.get("./src/main/resources/users/" + user + ".csv")))
            {
                if(validatePassword(user, pass))
                {
                    Stage updateStage;
                    Parent updated;
                    if(event.getSource()==login){
                        updateStage=(Stage)login.getScene().getWindow();
                        String signup = "/layout/accounts.fxml";
                        updated= FXMLLoader.load(getClass().getResource(signup));
                        Scene scene=new Scene(updated);
                        updateStage.setScene(scene);
                        updateStage.show();
                    }
                }
                else {
                    errorLabel.setTextFill(Color.web("#ff0000"));
                    errorLabel.setText("invalid Password!");
                }
            }
            else
            {
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setText("User does not Exist!");
            }
        }
    }
    private boolean validateFields() {
        return (username.getText() == null || username.getText().length() == 0 ||
                password.getText() == null || password.getText().length() == 0);
    }

    private boolean validatePassword(String name, String password) {
        File file = new File("./src/main/resources/users/" + name + ".csv");
        String pass = "";
        try {
            Scanner inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String data = inputStream.next();
                String[] temp = data.split(",");
                String user = temp[0];
                pass = temp[1];
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return password.equals(pass);
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

