package app.manager.calendar;

import app.main.StageProperty;
import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DayView
{
    private LocalDate date;
    public AnchorPane anchorPane;
    public String worker;
    private AnchorPaneNode parentNode;

    public DayView( LocalDate date, String worker, AnchorPaneNode parentNode )
    {
        this.date = date;
        this.worker = worker;
        this.parentNode = parentNode;
        anchorPane = new AnchorPane();
        StageProperty.loadStage(anchorPane, 800, 625);
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.setTitle(date.toString() + " " + worker);

        drawEvents();
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

        Button add = new Button("+");
        add.setPrefSize(25, 25);
        add.setLayoutX(760);
        add.setLayoutY(585);
        anchorPane.getChildren().add(add);

        add.setOnAction(e -> addEvent());
    }

    private void addEvent()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/views/addEvent.fxml"));
        Parent root = null;
        try
        {
            root = fxmlLoader.load();
        } catch( IOException e )
        {
            e.printStackTrace();
        }

        EventController controller = fxmlLoader.getController();
        controller.setWorker(worker);
        controller.setDate(date);
        controller.setParentPane(anchorPane);
        controller.setParentNode(parentNode);

        StageProperty.loadStage(root, 400, 300);
    }

    /**
     * draw all events from DB
     */
    private void drawEvents()
    {
        Connection connection = new ConnectionClass().getConnection();
        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM dailyEvents WHERE worker='"+ worker +"';";
            ResultSet resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
            {
                if( resultSet.getString(2).equals(date.toString()) )
                {
                    drawEvent(resultSet.getString(1), resultSet.getString(3), resultSet.getDouble(4), resultSet.getDouble(5));
                }
            }
            connection.close();
        } catch( SQLException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * draws event from DB
     */
    private void drawEvent( String worker, String description, double startH, double endH )
    {
        Rectangle rect = new Rectangle(20,startH*25 + 6, 770, (endH-startH)*25);
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.BLUE);

        parentNode.setStyle("-fx-background-color: #ed3838");

        Text text = new Text(worker);
        text.setFill(Color.BLACK);
        text.setStroke(Color.BLACK);
        StackPane pane = new StackPane();
        pane.getChildren().addAll(rect, text);
        pane.setLayoutX(20);
        pane.setLayoutY(startH*25 + 6);

        Tooltip tooltip = new Tooltip(description);
        tooltip.setShowDelay(Duration.seconds(0));
        Tooltip.install( pane, tooltip );

        anchorPane.getChildren().add(pane);
    }
}
