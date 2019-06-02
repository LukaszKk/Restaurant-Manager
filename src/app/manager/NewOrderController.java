package app.account;

import app.manager.Dish;
import app.manager.DishesController;
import app.main.Main;
import app.main.StageProperty;
import app.manager.EditDishController;
import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NewOrderController extends Main
{
    public AnchorPane anchorPane;
    public Button signUpButton;
    public TextField numberOrder;
    public TextField date;
    public TextField numberPeople;
    public TextField time;
    public TextField table;
    public TableView tableView;
    public ChoiceBox<String> choiceBox;
    public Hyperlink back;
    public Hyperlink exit;
    public Label loggedAs;
    private ArrayList<String> dishes = getInfo("nameDish");
    private ArrayList<String> dishesList;
    private ArrayList<String> priceList;
    private ArrayList<String> categoryList;

    /**
     * set initial properties
     * disable focusing cursor on TextField
     * different views if its first time run
     */
    public void initialize() {
        back.setVisible(true);
        exit.setText("");
        loggedAs.setVisible(true);
        loggedAs.setText(Main.loggedAs);
        for(int i= 0; i< dishes.size(); i++)
            choiceBox.getItems().add(dishes.get(i));

    }

    /**
     * Back button clicked
     * Close registration form
     * Open login form
     */
    public void backAction() {
        StageProperty.loadView("orders", anchorPane, this.getClass());
    }

    public void loadLogin() {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    /**
     * Exit button clicked
     * Close registration form
     */
    public void exitAction() {
        if (Main.isFirstTimeRun) {
            Stage registerStage = (Stage) anchorPane.getScene().getWindow();
            registerStage.close();
        } else
            loadLogin();
    }

    public void addAction() {
        String cat = new String();
        String pri = new String();

        Connection connection = new ConnectionClass().getConnection();
        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT  category  FROM dishes WHERE nameDish = '" + choiceBox.getValue()+ "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
                cat =resultSet.getString(1);

            sql = "SELECT  price  FROM dishes WHERE nameDish = '" + choiceBox.getValue()+ "';";
            resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
                pri =resultSet.getString(1);
            connection.close();
        } catch( SQLException e )
        {
            e.printStackTrace();
        }
        connection = new ConnectionClass().getConnection();

        try {
            Statement statement = connection.createStatement();

            if (numberOrder.getText().equals("")) {
                numberOrder.clear();
                numberOrder.setPromptText("Order number can not be empty!");
                numberOrder.setStyle("-fx-prompt-text-fill: #ff0000");

                connection.close();
                return;
            }
            String sql = "SELECT numberOrder FROM orders;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(numberOrder.getText())) {
                    numberOrder.clear();
                    numberOrder.setPromptText("Order number is already taken!");
                    numberOrder.setStyle("-fx-prompt-text-fill: #ff0000");

                    connection.close();
                    return;
                }
            }

            sql = "INSERT INTO dishOrder VALUES('" + numberOrder.getText() + "', '" + choiceBox.getValue() + "', '" + cat + "', '" + pri + "');";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listDishes();
    }

    /**
     * Sign Up button clicked
     * Check if fields are filled
     * Open connection with databese
     * Check if there is user in databese with given userName
     * Show error or if everything is alright save new user in databese
     * Trigger backAction() to close registration form and open login form
     */
    public void submitAction() {

        Connection connection = new ConnectionClass().getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT numberOrder FROM orders;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(numberOrder.getText())) {
                    numberOrder.clear();
                    numberOrder.setPromptText("Order number is already taken!");
                    numberOrder.setStyle("-fx-prompt-text-fill: #ff0000");

                    connection.close();
                    return;
                }
            }

            sql = "INSERT INTO orders VALUES('" + numberOrder.getText() + "', '" + date.getText() + "', '" + numberPeople.getText() + "', '" + time.getText() + "', '" + table.getText() + "');";
            statement.executeUpdate(sql);
            connection.close();
            if (!Main.isFirstTimeRun)
                backAction();
            else {
                Main.isFirstTimeRun = false;
                loadLogin();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void workersClicked()
    {
        StageProperty.loadView("workers", anchorPane, this.getClass());
    }

    public void logOutClicked()
    {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    public void dishesClicked()
    {
        StageProperty.loadView("dishes", anchorPane, this.getClass());
    }

    public ArrayList<String> getInfo( String attribute )
    {
        ArrayList<String> result = new ArrayList<>();
        Connection connection = new ConnectionClass().getConnection();
        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT " + attribute + " FROM dishes;";
            ResultSet resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
                result.add(resultSet.getString(1));
            connection.close();
        } catch( SQLException e )
        {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getDishInfo( String attribute )
    {
        ArrayList<String> result = new ArrayList<>();
        Connection connection = new ConnectionClass().getConnection();
        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT " + attribute + " FROM dishOrder WHERE numberOrder = " + numberOrder.getText() + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
                result.add(resultSet.getString(1));
            connection.close();
        } catch( SQLException e )
        {
            e.printStackTrace();
        }

        return result;
    }

    private void listDishes()
    {
        dishesList = getDishInfo("name");
        priceList = getDishInfo("price");
        categoryList = getDishInfo("category");

        TableColumn nameCol = new TableColumn("Name");
        TableColumn<Object, Object> categoryCol = new TableColumn<>("Category");
        TableColumn<Object, Object> priceCol = new TableColumn<>("Price");

        nameCol.setPrefWidth(tableView.getPrefWidth() / 3);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        categoryCol.setPrefWidth(tableView.getPrefWidth() / 3);
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        priceCol.setPrefWidth(tableView.getPrefWidth() / 3);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(nameCol, categoryCol,priceCol);

        tableView.setRowFactory(tv ->
        {
            TableRow<Dish> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent ->
            {
                if( !row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 1 )
                    showContextMenu(row.getIndex(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            });
            return row;
        });

        ObservableList<Dish> data = FXCollections.observableArrayList();

        for( int i = 0; i < dishesList.size(); i++ )
        {
            data.add(new Dish(dishesList.get(i), categoryList.get(i), priceList.get(i)));
        }

        FilteredList<Dish> flDish = new FilteredList(data, p -> true);
        tableView.setItems(flDish);


    }

    private void showContextMenu( int index, double X, double Y )
    {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");

        delete.setOnAction(actionEvent1 ->
        {

            Connection connection = new ConnectionClass().getConnection();
            try {
                Statement statement = connection.createStatement();
                //DELETE FROM dishes WHERE condition;
                String sql = "DELETE FROM dishOrder WHERE name='" + dishesList.get(index) + "';";
                statement.executeUpdate(sql);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            listDishes();
        });

        contextMenu.getItems().addAll(delete);

        contextMenu.show(anchorPane, X, Y);
        anchorPane.setOnMousePressed(mouseEvent -> contextMenu.hide());
        tableView.setOnMousePressed(mouseEvent -> contextMenu.hide());

    }

}
