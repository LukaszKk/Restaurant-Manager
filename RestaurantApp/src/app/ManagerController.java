package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController
{
    public AnchorPane anchorPane;
    public Hyperlink logOutButton;
    public Button workersButton;

    public void workersClicked()
    {
        Stage managerStage = (Stage) anchorPane.getScene().getWindow();
        try
        {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/workers.fxml"));
            Main.loadStage( fxmlLoader );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        managerStage.close();
    }

    public void logOutClicked()
    {
        Stage managerStage = (Stage) anchorPane.getScene().getWindow();
        try
        {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            RegisterController.loadStage( fxmlLoader );
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        managerStage.close();
    }
}