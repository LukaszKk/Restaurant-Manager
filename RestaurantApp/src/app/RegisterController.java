package app;

import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController extends Main
{
    public AnchorPane anchorPane;
    public Button signUpButton;
    public TextField userName;
    public TextField password;
    public ChoiceBox<String> choiceBox;
    public Hyperlink back;
    public Hyperlink exit;

    /**
     *
     */
    public void initialize()
    {
        if( Main.isFirstTimeRun )
        {
            choiceBox.getItems().add("Kierownik");
            choiceBox.setValue("Kierownik");
        }
        else
        {
            choiceBox.getItems().add("Logistyk");
            choiceBox.getItems().add("Kelner");
            choiceBox.setValue("Kelner");
        }

        userName.setFocusTraversable(false);
        password.setFocusTraversable(false);
    }

    /**
     * Back button clicked
     * Close registration form
     * Open login form
     */
    public void backAction()
    {
        Stage registerStage = (Stage) anchorPane.getScene().getWindow();

        try
        {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.show();

            registerStage.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Exit button clicked
     * Close registration form
     */
    public void exitAction()
    {
        Stage registerStage = (Stage) anchorPane.getScene().getWindow();
        registerStage.close();
    }

    /**
     * Sign Up button clicked
     * Check if fields are filled
     * Open connection with databese
     * Check if there is user in databese with given userName
     * Show error or if everything is alright save new user in databese
     * Trigger backAction() to close registration form and open login form
     */
    public void signUpAction()
    {
        int checked = LoginController.checkFieldsFill( userName, password );
        if( checked != 2 )
            return;

        Connection connection = new ConnectionClass().getConnection();

        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT name FROM user;";
            ResultSet resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
            {
                if( resultSet.getString(1).equals(userName.getText()) )
                {
                    userName.setPromptText( "User name is taken!");
                    userName.setStyle("-fx-prompt-text-fill: #ff0000");
                    password.clear();
                    return;
                }
            }
            sql = "INSERT INTO user VALUES('"+userName.getText()+"', '"+password.getText()+"', '"+choiceBox.getValue()+"');";
            statement.executeUpdate( sql );
            backAction();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
}
