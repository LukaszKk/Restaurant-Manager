package app.manager;

import app.main.Main;
import app.main.StageProperty;
import connectivity.ConnectionClass;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditDishController extends Main
{
    public AnchorPane anchorPane;
    public Button submitButton;
    public Button deleteButton;
    public TextField newDishName;
    public TextField newPrice;
    public ChoiceBox<String> choiceBox;
    public Hyperlink back;
    public Hyperlink logOut;
    public Label loggedAs;
    static String dishNameDB;
    public Label dishName;
    public Label price;
    public Label category;

    /**
     * set initial properties
     * disable focusing cursor on TextField
     */
    public void initialize() {
        loggedAs.setText("Logged as: " + Main.loggedAs);

        dishName.setText(dishNameDB);

        back.setVisible(true);
        loggedAs.setVisible(true);
        choiceBox.getItems().add("Soup");
        choiceBox.getItems().add("Meat");
        choiceBox.getItems().add("Dessert");
        choiceBox.setValue(fillLabels());
    }



    public void backAction() {
        StageProperty.loadView("dishes", anchorPane, this.getClass());
    }

    public void logOutAction() {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    /**
     * fills labels with information from DB
     * returns position
     *
     * @return
     */
    private String fillLabels() {
        price.setText(getInfo("price"));
        String result = getInfo("category");
        category.setText(result);

        return result;
    }

    /**
     * get given attribute from DB
     *
     * @param attribute
     * @return
     */
    private String getInfo(String attribute) {
        Connection connection = new ConnectionClass().getConnection();
        String result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT " + attribute + " FROM dishes WHERE nameDish='" + dishNameDB + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
                result = resultSet.getString(1);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * save new data if were given
     */
    public void submitAction() {
        if (!newDishName.getText().equals("")) {
            Connection connection = new ConnectionClass().getConnection();
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nameDish FROM dishes;");
                while (resultSet.next()) {
                    if (resultSet.getString(1).equals(newDishName.getText())) {
                        newDishName.clear();
                        newDishName.setPromptText("Dish name is already taken!");
                        newDishName.setStyle("-fx-prompt-text-fill: #ff0000");
                        newPrice.clear();
                        connection.close();
                        return;
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            setInfo("nameDish", newDishName.getText());
            dishNameDB = newDishName.getText();
        }

        if (!newPrice.getText().equals("")) {
            setInfo("price", newPrice.getText());
        }

        setInfo("category", choiceBox.getValue());

        backAction();
    }

    /**
     * delete data
     */
    public void deleteAction() {

        Connection connection = new ConnectionClass().getConnection();
        try {
            Statement statement = connection.createStatement();
            //DELETE FROM dishes WHERE condition;
            String sql = "DELETE FROM dishes WHERE nameDish='" + dishNameDB + "';";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        backAction();
    }

    /**
     * set given attribute with value
     *
     * @param attribute
     * @param value
     */
    private void setInfo(String attribute, String value) {
        Connection connection = new ConnectionClass().getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "UPDATE dishes SET " + attribute + "=" + "'" + value + "'" + " WHERE nameDish='" + dishNameDB + "';";
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
