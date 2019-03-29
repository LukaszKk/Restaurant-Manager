package app;

import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginController extends Main
{
    public AnchorPane anchorPane;
    public TextField loginField;
    public PasswordField passwordField;
    public Button signInButton;
    public Label loginLabel;
    public Hyperlink signUpHLink;

    public void initialize()
    {
        loginField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }

    /**
     * Sign In button clicked
     * Check if fields are filled
     * Open connection with databese
     * Give an error or if everything is alrigth check if given user exists in database
     */
    public void signInButtonAction()
    {
        int checked = 2;
        if( loginField.getText().length() == 0 )
        {
            loginField.setPromptText( "User name must be filled!");
            loginField.setStyle("-fx-prompt-text-fill: #ff0000");
            passwordField.clear();
            --checked;
        }

        if( passwordField.getText().length() == 0 )
        {
            passwordField.setPromptText( "Password must be filled!");
            passwordField.setStyle("-fx-prompt-text-fill: #ff0000");
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
                if( resultSet.getString(1).equals(loginField.getText()) )
                {
                    --checked;
                    break;
                }
            }

            sql = "SELECT password FROM user;";
            resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
            {
                if( resultSet.getString(1).equals(passwordField.getText()) )
                {
                    --checked;
                    sql = "SELECT position FROM user WHERE password = '" + passwordField.getText() + "';";
                    break;
                }
            }

            if( checked == 0 )
            {
                Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                sql = resultSet.getString(1);

                //TODO...
                switch( sql )
                {
                    case "Ksiegowa":
                        System.out.println("Ksiegowa"); break;
                    case "Logistyk":
                        System.out.println("Logistyk"); break;
                    case "Kierownik":
                        System.out.println("Kierownik"); break;
                    case "Kelner":
                        System.out.println( "Kelner" ); break;
                }

                primaryStage.close();
            }
            else
            {
                passwordField.clear();
                passwordField.setPromptText( "Invalid user name or password!");
                passwordField.setStyle("-fx-prompt-text-fill: #ff0000");
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Sign Up button clicked
     * Open registration form
     */
    public void signUpButtonAction()
    {
        try
        {
            Stage primaryStage = (Stage) anchorPane.getScene().getWindow();

            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("views/register.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(fxmlLoader));
            stage.show();

            primaryStage.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

    }
}
