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

    /**
     * set fields initial property
     */
    public void initialize()
    {
        loginField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }

    static int checkFieldsFill( TextField field, TextField password )
    {
        int checked = 2;
        if( field.getText().length() == 0 )
        {
            field.setPromptText( "User name must be filled!");
            field.setStyle("-fx-prompt-text-fill: #ff0000");
            password.clear();
            --checked;
        }

        if( password.getText().length() == 0 )
        {
            password.setPromptText( "Password must be filled!");
            password.setStyle("-fx-prompt-text-fill: #ff0000");
            --checked;
        }

        return checked;
    }

    /**
     * Sign In button clicked
     * Check if fields are filled
     * Open connection with databese
     * Give an error or if everything is alrigth check if given user exists in database
     */
    public void signInButtonAction()
    {
        int checked = checkFieldsFill( loginField, passwordField );
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
                String openViewName = null;
                switch( sql )
                {
                    case "Ksiegowa":
                        break;
                    case "Logistyk":
                        break;
                    case "Kierownik":
                        openViewName = "views/manager.fxml"; break;
                    case "Kelner":
                        break;
                }

                Parent fxmlLoader = FXMLLoader.load(getClass().getResource(openViewName));
                Stage stage = new Stage();
                stage.setScene(new Scene(fxmlLoader));
                stage.show();

                primaryStage.close();
            }
            else
            {
                passwordField.clear();
                passwordField.setPromptText( "Invalid user name or password!");
                passwordField.setStyle("-fx-prompt-text-fill: #ff0000");
            }
        }
        catch( SQLException | IOException e )
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

            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
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
