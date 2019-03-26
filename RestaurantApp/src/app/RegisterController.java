package app;

import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController
{
    public AnchorPane anchorPane;
    public Button signUpButton;
    public TextField userName;
    public TextField password;
    public ChoiceBox<String> choiceBox;
    public Hyperlink back;

    public void initialize()
    {
        choiceBox.getItems().add("Logistyk");
        choiceBox.getItems().add("Kierownik");
        choiceBox.getItems().add("Kelner");
        choiceBox.getItems().add("Ksiegowa");
        choiceBox.setValue("Kierownik");
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
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("views/login.fxml"));
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
     * Sign Up button clicked
     * Check if fields are filled
     * Open connection with databese
     * Check if there is user in databese with given userName
     * Show error or if everything is alright save new user in databese
     * Trigger backAction() to close registration form and open login form
     */
    public void signUpAction()
    {
        int checked = 2;
        if( userName.getText().length() == 0 )
        {
            userName.setPromptText( "User name must be filled!");
            userName.setStyle("-fx-prompt-text-fill: #ff0000");
            password.clear();
            --checked;
        }

        if( password.getText().length() == 0 )
        {
            password.setPromptText( "Password must be filled!");
            password.setStyle("-fx-prompt-text-fill: #ff0000");
            --checked;
        }

        if( checked != 2 )
            return;

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

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
