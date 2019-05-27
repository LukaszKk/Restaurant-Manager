module RestaurantManager.app
{
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens app.account;
    opens app.main;
    opens app.accountant;
    opens app.logistician;
    opens app.manager;
    opens app.waiter;
    opens app.manager.calendar;
}