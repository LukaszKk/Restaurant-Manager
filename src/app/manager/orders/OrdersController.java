package app.manager.orders;

import app.main.Main;
import app.main.StageProperty;
import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class OrdersController {
    public AnchorPane anchorPane;
    public Hyperlink backButton;
    public Button createAccountButton;
    public Hyperlink logOutButton;
    public Label loggedAs;
    public TableView tableView;
    public TextField search;
    private ArrayList<String> numberOrder = getOrderInfo("numberOrder");
    private ArrayList<String> date = getOrderInfo("date");
    private ArrayList<String> numberPeople = getOrderInfo("numberPeople");
    private ArrayList<String> time = getOrderInfo("time");
    private ArrayList<String> table = getOrderInfo("tableNum");


    public void initialize() {
        loggedAs.setText(Main.loggedAs);
        tableView.setEditable(true);
        listDishes();
    }

    public void backAction() {
        StageProperty.loadView("manager", anchorPane, this.getClass());
    }

    public void logOutAction() {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    public void makeOrderAction() {
        StageProperty.loadView("newOrder", anchorPane, this.getClass());
    }

    /**
     * get data about order
     * and list all order
     */
    private void listDishes() {
        TableColumn nameCol = new TableColumn("Number");
        TableColumn<Object, Object> dateCol = new TableColumn<>("Date");
        TableColumn<Object, Object> numberPeopleCol = new TableColumn<>("Number Of People");
        TableColumn<Object, Object> timeCol = new TableColumn<>("Time");
        TableColumn<Object, Object> tableCol = new TableColumn<>("Table");
        numberOrder = getOrderInfo("numberOrder");
        date = getOrderInfo("date");
        numberPeople = getOrderInfo("numberPeople");
        time = getOrderInfo("time");
        table = getOrderInfo("tableNum");

        nameCol.setPrefWidth(tableView.getPrefWidth() / 5);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("numberOrder"));

        dateCol.setPrefWidth(tableView.getPrefWidth() / 5);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        numberPeopleCol.setPrefWidth(tableView.getPrefWidth() / 5);
        numberPeopleCol.setCellValueFactory(new PropertyValueFactory<>("numberPeople"));

        timeCol.setPrefWidth(tableView.getPrefWidth() / 5);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableCol.setPrefWidth(tableView.getPrefWidth() / 5);
        tableCol.setCellValueFactory(new PropertyValueFactory<>("table"));

        tableView.getColumns().addAll(nameCol, dateCol, numberPeopleCol, timeCol,tableCol );

        tableView.setRowFactory(tv ->
        {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent ->
            {
                if (!row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 1)
                    showContextMenu(row.getIndex(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            });
            return row;
        });

        ObservableList<Order> data = FXCollections.observableArrayList();

        for (int i = 0; i < numberOrder.size(); i++) {
            data.add(new Order(numberOrder.get(i), date.get(i), numberPeople.get(i), time.get(i), table.get(i)));
        }

        FilteredList<Order> flOrder = new FilteredList(data, p -> true);
        tableView.setItems(flOrder);

        search.setOnKeyReleased(keyEvent ->
                flOrder.setPredicate(p -> p.getNumberOrder().toLowerCase().contains(search.getText().toLowerCase().trim()))
        );

        if (!loggedAs.getText().contains("Manager")) {
            createAccountButton.setVisible(false);
        }
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
        MenuItem delete = new MenuItem("Delete");

        edit.setOnAction(actionEvent1 ->
        {
            EditOrderController.numOrder = numberOrder.get(index);
            StageProperty.loadView("editOrder", anchorPane, this.getClass());
        });

        delete.setOnAction(actionEvent1 ->
        {

            Connection connection = new ConnectionClass().getConnection();
            try {
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM orders WHERE numberOrder='" + numberOrder.get(index) + "';";
                statement.executeUpdate(sql);
                sql = "DELETE FROM dishOrder WHERE numberOrder='" + numberOrder.get(index) + "';";
                statement.executeUpdate(sql);
                connection.close();
                listDishes();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        contextMenu.getItems().addAll(edit,delete);

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
    public ArrayList<String> getOrderInfo(String attribute) {
        ArrayList<String> result = new ArrayList<>();
        Connection connection = new ConnectionClass().getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT " + attribute + " FROM orders;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                result.add(resultSet.getString(1));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void workersClicked() {
        StageProperty.loadView("workers", anchorPane, this.getClass());
    }

    public void logOutClicked() {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    public void dishesClicked() {
        StageProperty.loadView("dishes", anchorPane, this.getClass());
    }
}
