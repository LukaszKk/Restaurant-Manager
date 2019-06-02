package app.waiter;

import app.main.Main;
import app.main.StageProperty;
import app.schedule.FullCalendarView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.YearMonth;

public class WaiterController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label loggedAs;
    @FXML
    private AnchorPane calendarAP;

    public void initialize() {
        loggedAs.setText(Main.loggedAs);
        calendarAP.getChildren().add( new FullCalendarView(YearMonth.now()).getView() );
    }

    public void workersClicked() {
        loadView("workers");
    }

    public void logOutClicked()
    {
        loadView("login");
    }

    private void loadView(String view) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
            StageProperty.loadStage(fxmlLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }

    public void logoutAction() {
        loadView("login");
    }
}

