package passmanager.Account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import passmanager.database.dbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class accountController implements Initializable {
    @FXML
    private Label whatAccount;
    @FXML
    private Label URLname;
    @FXML
    private Hyperlink link;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TableView<accountData> accountDataTable;
    @FXML
    private TableColumn<accountData, String> accounts;
    private dbConnection db;
    private ObservableList<accountData> data;
    public void initialize(URL location, ResourceBundle resources) {
        db = new dbConnection();
        try {
            loadAccountdata();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String sql = "SELECT * FROM accounts";
    private void loadAccountdata() throws SQLException {
        Connection connection = dbConnection.getConnection();
        data = FXCollections.observableArrayList();
        ResultSet rs = connection.createStatement().executeQuery(sql);
        while (rs.next()) {
            data.add(new accountData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        accounts.setCellValueFactory(new PropertyValueFactory<accountData, String>("website"));
        accountDataTable.setItems(data);
        rs.close();
        connection.close();
    }
    @FXML
    public void click(MouseEvent event)
    {
        if(event.getClickCount() == 1)
        {
            whatAccount.setText(accountDataTable.getSelectionModel().getSelectedItem().getWebsite());
            URLname.setText(accountDataTable.getSelectionModel().getSelectedItem().getWebsite());
            link.setText(accountDataTable.getSelectionModel().getSelectedItem().getUrl());
            username.setText(accountDataTable.getSelectionModel().getSelectedItem().getUsername());
            password.setText("********");
        }
    }
    @FXML
    public void showPass(ActionEvent event)
    {
            password.setText(accountDataTable.getSelectionModel().getSelectedItem().getPassword());
    }
    @FXML
    private void addAccountButton(ActionEvent event) throws IOException {
        Stage update = new Stage();
        Parent root;
        String addAccount = "/layout/addAccount.fxml";
        root = FXMLLoader.load(getClass().getResource(addAccount));
        update.setScene(new Scene(root));
        update.initModality(Modality.APPLICATION_MODAL);
        update.show();
    }
}