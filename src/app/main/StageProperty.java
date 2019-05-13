package app.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageProperty
{

    /**
     * opens given form
     *
     * @param fxmlLoader
     */
    public static void loadStage( Parent fxmlLoader )
    {
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * load given view
     *
     * @param view
     */
    public static void loadView( String view, AnchorPane anchorPane, Class c )
    {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        try
        {
            Parent fxmlLoader = FXMLLoader.load(c.getResource("/views/" + view + ".fxml"));
            loadStage(fxmlLoader);
        } catch( IOException e )
        {
            e.printStackTrace();
        }
        primaryStage.close();
    }
}
