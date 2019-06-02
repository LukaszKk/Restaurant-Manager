package app.manager;

import app.main.Main;
import app.main.StageProperty;
import app.schedule.FullCalendarView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.YearMonth;

public class ManagerController
{
    public AnchorPane anchorPane;
    public AnchorPane calendarAP;
    public Button button1;
    public Label loggedAs;

    public void initialize()
    {
        loggedAs.setText(Main.loggedAs);
        calendarAP.getChildren().add( new FullCalendarView(YearMonth.now()).getView() );
    }

    public void workersClicked()
    {
        StageProperty.loadView("workers", anchorPane, this.getClass());
    }

    public void logOutClicked()
    {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    public void dishesClicked()
    {
        StageProperty.loadView("dishes", anchorPane, this.getClass());
    }

    public void ordersClicked()
    {
        StageProperty.loadView("orders", anchorPane, this.getClass());
    }
}