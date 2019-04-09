<<<<<<< HEAD
package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogisticianController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label loggedAs;

    public void initialize() {
        loggedAs.setText(Main.loggedAs);
    }

    private void loadView(String view) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
            Main.loadStage(fxmlLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }

    public void logoutAction() {
        loadView("login");
    }
}
=======
package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogisticianController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label loggedAs;

    public void initialize() {
        loggedAs.setText(Main.loggedAs);
    }

    private void loadView(String view) {
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
            Main.loadStage(fxmlLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }

    public void logoutAction() {
        loadView("login");
    }
}
>>>>>>> 7f996d45a836aaf5948c3faa8bfd825182b672c7
