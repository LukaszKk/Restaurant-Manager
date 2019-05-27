package app.manager.calendar;

import app.main.StageProperty;
<<<<<<< HEAD
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
=======
import javafx.scene.Group;
>>>>>>> master
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
<<<<<<< HEAD
import javafx.stage.Stage;

import java.io.IOException;
=======

>>>>>>> master
import java.time.LocalDate;

public class DayView
{
    private LocalDate date;
    public AnchorPane anchorPane;

    public DayView( LocalDate date )
    {
        this.date = date;
        anchorPane = new AnchorPane();
<<<<<<< HEAD
        StageProperty.loadStage(anchorPane, 800, 625);
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.setTitle(date.toString());
=======
        /*Button close = new Button("Close");
        close.setOnAction( e ->
        {
            Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
            primaryStage.close();
        });
        anchorPane.getChildren().add( close );*/
        StageProperty.loadStage(anchorPane, 800, 625);
>>>>>>> master
    }

    public void draw()
    {
        Group group = new Group();
        for( int i = 0; i < 25; i++ )
        {
            Line line = new Line(20, 25 * i + 6, 790, 25 * i + 6);
            Label label = new Label(i + "");
            label.setLayoutX(1);
            label.setLayoutY(25 * i - 1);
            group.getChildren().addAll(label, line);
        }
        anchorPane.getChildren().add(group);

<<<<<<< HEAD
        Button add = new Button("+");
        add.setPrefSize(25, 25);
        add.setLayoutX(760);
        add.setLayoutY(585);
        anchorPane.getChildren().add(add);

        add.setOnAction(e -> addEvent());
    }

    private void addEvent()
    {
        Parent fxmlLoader = null;
        try
        {
            fxmlLoader = FXMLLoader.load(this.getClass().getResource("/views/addEvent.fxml"));
        } catch( IOException e )
        {
            e.printStackTrace();
        }
        StageProperty.loadStage(fxmlLoader, 400, 300);
        EventController.parentPane = anchorPane;
=======
        Button add = new Button("Add event");
        anchorPane.getChildren().add( add );
>>>>>>> master
    }
}
