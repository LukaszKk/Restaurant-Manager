package app;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginController
{
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button signButton;
    @FXML
    public Label loginLabel;

    @FXML
    public void signButtonAction(ActionEvent actionEvent) throws SQLException
    {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "INSERT INTO USER VALUES('" + loginField.getText() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate( sql );
        sql = "SELECT  * FROM USER;";
        ResultSet resultSet = statement.executeQuery(sql);
        while( resultSet.next() )
        {
            loginLabel.setText( resultSet.getString(1) );
        }
    }
}
