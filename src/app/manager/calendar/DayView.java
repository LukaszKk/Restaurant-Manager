package app.manager.calendar;

import app.main.StageProperty;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.time.LocalDate;

public class DayView
{
    private LocalDate date;
    public AnchorPane anchorPane;

    public DayView( LocalDate date )
    {
        this.date = date;
        anchorPane = new AnchorPane();
        /*Button close = new Button("Close");
        close.setOnAction( e ->
        {
            Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
            primaryStage.close();
        });
        anchorPane.getChildren().add( close );*/
        StageProperty.loadStage(anchorPane, 800, 625);
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

        Button add = new Button("Add event");
        anchorPane.getChildren().add( add );
    }
}
