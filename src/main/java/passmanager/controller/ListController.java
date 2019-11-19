package passmanager.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import passmanager.Database;
import passmanager.Util;
import passmanager.accountGettersSetters;

public class ListController extends BaseController implements Initializable {

	private final static Logger Log = Util.getLogger(ListController.class);
	@FXML MenuItem choiceNew;
	@FXML TextField DisplayWebsite;
	@FXML TextField DisplayUsername;
	@FXML TextField DisplayPassword;
	@FXML PasswordField hiddenPassword;
	@FXML TextArea DisplayNotes;
	@FXML MenuItem choiceSettings;
	@FXML MenuItem choiceQuit;
	@FXML Button DisplaySave;
	@FXML CheckBox edit;
	@FXML private TableView<accountGettersSetters> tableView;
	@FXML private TableColumn<accountGettersSetters, String> account;
	private ObservableList<accountGettersSetters> data;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadData();
			hiddenPassword.setEditable(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void loadData() throws SQLException {
		String sql = "SELECT * FROM entries";
		Connection conn = Database.connect();
		data = FXCollections.observableArrayList();
		ResultSet rs = conn.createStatement().executeQuery(sql);
		while (rs.next())
		{
			data.add(new accountGettersSetters(rs.getString("title"),rs.getString("username"),rs.getString("password"),rs.getString("url"),rs.getString("note")));
		}
		account.setCellValueFactory(new PropertyValueFactory<accountGettersSetters, String>("title"));
		tableView.setItems(data);
		rs.close();
		conn.close();
	}
	@FXML
	public void reload()
	{
		try {
			loadData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * used to process the different choices in the menu bar
	 * @param event
	 */
	public void onMenu(Event event) throws IOException {
		// i don't think this is strictly necessary, but i just copied
		// what dennis did to be safe
		if (event.getSource() == choiceNew) {
			loadNewEntry("/layout/newEntries.fxml");
			Log.info("New Selected, inputting new entry");
		}
		if (event.getSource() == choiceSettings) {
			loadNewEntry("/layout/settings.fxml");
			Log.info("Settings selected");
		}

		if (event.getSource() == choiceQuit) {
			stage.close();
		}
	}
	@FXML
	public void click(MouseEvent event)
	{
		if(event.getClickCount() == 1)
		{
			DisplayWebsite.setText(tableView.getSelectionModel().getSelectedItem().getTitle());
			DisplayUsername.setText(tableView.getSelectionModel().getSelectedItem().getUsername());
			DisplayNotes.setText(tableView.getSelectionModel().getSelectedItem().getNote());
//			DisplayUrl.setText(tableView.getSelectionModel().getSelectedItem().getUrl());
			DisplayPassword.setText(tableView.getSelectionModel().getSelectedItem().getPassword());
			hiddenPassword.setText(tableView.getSelectionModel().getSelectedItem().getPassword());
		}
	}
	public void onEditEntry(Event event) {
		if(event.getSource() == edit) {
			if(edit.isSelected()) {
				DisplayWebsite.setEditable(true);
				DisplayUsername.setEditable(true);
				DisplayPassword.setEditable(true);
				DisplayPassword.setVisible(true);
				hiddenPassword.setVisible(false);
				DisplayNotes.setEditable(true);
				DisplaySave.setVisible(true);
			}
			else {
				DisplayWebsite.setEditable(false);
				DisplayUsername.setEditable(false);
				DisplayPassword.setEditable(false);
				DisplayPassword.setVisible(false);
				hiddenPassword.setVisible(true);
				hiddenPassword.setEditable(false);
				DisplayNotes.setEditable(false);
				DisplaySave.setVisible(false);
			}
		}
	}
}
