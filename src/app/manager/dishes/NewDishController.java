package app.manager.dishes;

import app.main.Main;
import app.main.StageProperty;
import connectivity.ConnectionClass;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewDishController extends Main
{
    public AnchorPane anchorPane;
    public Button signUpButton;
    public TextField dishName;
    public TextField price;
    public ChoiceBox<String> choiceBox;
    public Hyperlink back;
    public Hyperlink exit;
    public Label loggedAs;

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
        choiceBox.getItems().add("Soup");
        choiceBox.getItems().add("Meat");
        choiceBox.getItems().add("Dessert");
    }

    /**
     * Back button clicked
     * Close registration form
     * Open login form
     */
    public void backAction() {
        StageProperty.loadView("dishes", anchorPane, this.getClass());
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

    /**
     * Sign Up button clicked
     * Check if fields are filled
     * Open connection with databese
     * Check if there is user in databese with given userName
     * Show error or if everything is alright save new user in databese
     * Trigger backAction() to close registration form and open login form
     */
    public void signUpAction() {

        Connection connection = new ConnectionClass().getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT nameDish FROM dishes;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(dishName.getText())) {
                    dishName.clear();
                    dishName.setPromptText("Dish name is already taken!");
                    dishName.setStyle("-fx-prompt-text-fill: #ff0000");
                    //password.clear();
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
}
