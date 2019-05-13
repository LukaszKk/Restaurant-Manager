package app.accountant;

import app.main.Main;
import app.main.StageProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AccountantController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label loggedAs;

    public void initialize() {
        loggedAs.setText(Main.loggedAs);
    }

    public void logOutClicked()
    {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }


    public void logoutAction() {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }
}

