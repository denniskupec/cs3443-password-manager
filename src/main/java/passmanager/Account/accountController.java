package passmanager.Account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import passmanager.database.dbConnection;
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
    private Label username;
    @FXML
    private Label password;
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
}