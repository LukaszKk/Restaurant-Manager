package app.manager;

import app.main.StageProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jfxtras.labs.icalendaragenda.scene.control.agenda.ICalendarAgenda;
import jfxtras.labs.icalendarfx.VCalendar;

import java.time.LocalDateTime;
import java.time.Period;

public class Calendar
{
    public void loadCalendar(Stage primaryStage)
    {
        VCalendar vCalendar = new VCalendar();
        AnchorPane root = new AnchorPane();
        BorderPane borderPane = new BorderPane();
        root.getChildren().add(borderPane);
        ICalendarAgenda agenda = new ICalendarAgenda(vCalendar);
        borderPane.setCenter(agenda);

        Button increaseWeek = new Button(">");
        Button decreaseWeek = new Button("<");
        Button back = new Button("Back");
        HBox weekButtons = new HBox(decreaseWeek, increaseWeek, back);
        weekButtons.setSpacing( 10 );
        borderPane.setTop(weekButtons);

        back.setOnAction( e -> StageProperty.loadView("workers", root, this.getClass()) );
        increaseWeek.setOnAction( e ->
        {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().plus(Period.ofWeeks(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });

        decreaseWeek.setOnAction( e ->
        {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().minus(Period.ofWeeks(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }
}
