package app;

import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddDishController extends Main {
    public AnchorPane anchorPane;
    public Button addButton;
    public TextField dishName;
    public TextField price;
    public ChoiceBox<String> choiceBox;
    public Hyperlink back;
    public Hyperlink exit;
    public Label loggedAs;


    public void initialize() {

        back.setVisible(true);
        exit.setText("Log out");
        loggedAs.setVisible(true);
        loggedAs.setText("Logged as: " + Main.loggedAs);
        choiceBox.getItems().add("Soup");
        choiceBox.getItems().add("Meat");
        choiceBox.getItems().add("Dessert");


    }

    public void backAction() {
        loadView("manager");
    }

    public void loadLogin() {
        loadView("login");
    }


    private void loadView(String view) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
            Main.loadStage(fxmlLoader);
            primaryStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitAction() {
        if (Main.isFirstTimeRun) {
            Stage registerStage = (Stage) anchorPane.getScene().getWindow();
            registerStage.close();
        } else
            loadLogin();
    }


    public void addAction() {
        //int checked = LoginController.checkFieldsFill(userName, password);
        //if (checked != 2)
        //    return;

        Connection connection = new ConnectionClass().getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT name FROM dishes;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(dishName.getText())) {
                    dishName.clear();
                    dishName.setPromptText("Dish name is already taken!");
                    dishName.setStyle("-fx-prompt-text-fill: #ff0000");
                    price.clear();
                    connection.close();
                    return;
                }
            }
            sql = "INSERT INTO dishes VALUES('" + dishName.getText() + "', '" + price.getText() + "', '" + choiceBox.getValue() + "');";
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
}
