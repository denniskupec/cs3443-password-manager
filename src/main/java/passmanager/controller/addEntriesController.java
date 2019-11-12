package passmanager.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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

    /**
     * used to prompt user to create entries
     *
     * @param String website, String url, String username, String password, String note
     */
    @FXML
    public void addEntries(ActionEvent event) throws Exception {
        if(!validateFields())
        {
            if(validatePassword(passWord.getText(), confirmPass.getText()))
            {
                addEntriesToDatabase(website.getText(),url.getText(), userName.getText(), passWord.getText(), note.getText());
                Stage stage = (Stage) save.getScene().getWindow();
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
    public void addEntriesToDatabase(String website, String url, String username, String password, String note) throws Exception {
        Connection connection = Database.connect();
        String sql = "INSERT INTO entries (updated_at,title, username, password, url, note) VALUES (datetime(),?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, website);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.setString(4, url);
        ps.setString(5, note);
        ps.executeUpdate();
        ps.close();
    }
    @FXML
    public void cancel()
    {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    private boolean validateFields() {
        return (userName.getText() == null || userName.getText().length() == 0 ||
                passWord.getText() == null || passWord.getText().length() == 0 ||
                confirmPass.getText() == null || confirmPass.getText().length() == 0 ||
                website.getText() == null || website.getText().length() == 0 ||
                url.getText() == null || website.getText().length() == 0 ||
                note.getText() == null || note.getText().length() == 0);
    }
    private boolean validatePassword(String password, String confirmPass) {
        return password.equals(confirmPass);
    }
}
