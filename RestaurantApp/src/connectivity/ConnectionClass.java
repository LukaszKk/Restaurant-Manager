package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass
{
    public Connection connection;

    public Connection getConnection()
    {
        String dbName = "Login";
        String userName = "root";
        String password = "";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);
        }
        catch( ClassNotFoundException | SQLException e )
        {
            e.printStackTrace();
        }

        return connection;
    }
}