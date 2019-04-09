package app;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WorkersController {
    public AnchorPane anchorPane;
    public Hyperlink backButton;
    public Button createAccountButton;
    public Hyperlink logOutButton;
    public Label loggedAs;
    public TableView tableView;
    public TextField search;
    private ArrayList<String> name = getWorkersInfo("name");
    private ArrayList<String> position = getWorkersInfo("position");

    public void initialize() {
        loggedAs.setText(Main.loggedAs);
        tableView.setEditable(true);
        listWorkers();
    }

    public void backAction() {
        loadView("manager");
    }

    public void logOutAction() {
        loadView("login");
    }

    public void createAccountAction() {
        loadView("register");
    }

    /**
     * load given view
     *
     * @param view
     */
    private void loadView(String view) {
        try {
            Stage primaryStage = (Stage) anchorPane.getScene().getWindow();

            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
            Main.loadStage(fxmlLoader);
            primaryStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get data about workers
     * and list all workers
     */
    private void listWorkers() {
        TableColumn nameCol = new TableColumn("Name");
        TableColumn positionCol = new TableColumn("Position");

        nameCol.setPrefWidth(tableView.getPrefWidth() / 2);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        positionCol.setPrefWidth(tableView.getPrefWidth() / 2);
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        tableView.getColumns().addAll(nameCol, positionCol);

        tableView.setRowFactory(tv ->
        {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent ->
            {
                if (!row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 1)
                    showContextMenu(row.getIndex(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            });
            return row;
        });

        ObservableList<Person> data = FXCollections.observableArrayList();

        for (int i = 0; i < name.size(); i++) {
            data.add(new Person(name.get(i), position.get(i)));
        }

        FilteredList<Person> flPerson = new FilteredList(data, p -> true);
        tableView.setItems(flPerson);

        search.setOnKeyReleased(keyEvent ->
                flPerson.setPredicate(p -> p.getPosition().toLowerCase().contains(search.getText().toLowerCase().trim()))
        );
    }

    /**
     * create context menu for one row
     *
     * @param index
     * @param X
     * @param Y
     */
    private void showContextMenu(int index, double X, double Y) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        MenuItem schedule = new MenuItem("Schedule");

        edit.setOnAction(actionEvent1 ->
        {
            EditWorkersController.userNameDB = name.get(index);
            loadView("editWorkers");
        });

        schedule.setOnAction(actionEvent1 ->
        {
            /*loadView( "scheduleWorkers" );*/
        });

        contextMenu.getItems().addAll(edit, schedule);

        contextMenu.show(anchorPane, X, Y);
        anchorPane.setOnMousePressed(mouseEvent -> contextMenu.hide());
        tableView.setOnMousePressed(mouseEvent -> contextMenu.hide());
    }

    /**
     * get data form DB
     *
     * @param attribute
     * @return
     */
    private ArrayList<String> getWorkersInfo(String attribute) {
        ArrayList<String> result = new ArrayList<>();
        Connection connection = new ConnectionClass().getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT " + attribute + " FROM users;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                result.add(resultSet.getString(1));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}