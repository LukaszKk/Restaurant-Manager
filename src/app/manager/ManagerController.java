package app.manager;

import app.main.Main;
import app.main.StageProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ManagerController
{
    public AnchorPane anchorPane;
    public Hyperlink logOutButton;
    public Button button1;
    public Label loggedAs;

    public void initialize()
    {
        loggedAs.setText(Main.loggedAs);
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