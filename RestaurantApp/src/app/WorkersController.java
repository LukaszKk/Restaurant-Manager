package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkersController
{
    public AnchorPane anchorPane;
    public Hyperlink backButton;
    public Button createAccountButton;
    public Hyperlink logOutButton;
    public Label loggedAs;

    public void initialize()
    {
        loggedAs.setText( "Logged as: " + Main.loggedAs );
    }

    public void backAction()
    {
        loadView( "manager" );
    }

    public void logOutAction()
    {
        loadView( "login" );
    }

    public void createAccountAction()
    {
        loadView( "register" );
    }

    /**
     * load given view
     * @param view
     */
    private void loadView( String view )
    {
        try
        {
            Stage primaryStage = (Stage) anchorPane.getScene().getWindow();

            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
            Main.loadStage( fxmlLoader );
            primaryStage.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
