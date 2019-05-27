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
        Scene scene = new Scene( fxmlLoader, 600, 400 );
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * opens given form with given size

     * @param fxmlLoader
     * @param width
     * @param height
     */
    public static void loadStage( Parent fxmlLoader, int width, int height )
    {
        Stage stage = new Stage();
        Scene scene = new Scene( fxmlLoader, width, height );
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.initStyle(StageStyle.UNDECORATED);
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

    public static void loadView( String view, Parent fxmlLoader, Class c )
    {
        ((Stage) fxmlLoader.getScene().getWindow()).close();
        try
        {
            fxmlLoader = FXMLLoader.load(c.getResource("/views/" + view + ".fxml"));
            loadStage(fxmlLoader);
        } catch( IOException e )
        {
            e.printStackTrace();
        }
    }
}
