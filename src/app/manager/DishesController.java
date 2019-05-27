package app.manager;

import app.main.Main;
import app.main.StageProperty;
import app.manager.EditWorkersController;
import app.manager.Person;
import app.manager.calendar.FullCalendarView;
import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.ArrayList;


public class DishesController
{
    public AnchorPane anchorPane;
    public Hyperlink backButton;
    public Button createAccountButton;
    public Hyperlink logOutButton;
    public Label loggedAs;
    public TableView tableView;
    public TextField search;
    private ArrayList<String> name = getWorkersInfo("nameDish");
    private ArrayList<String> price = getWorkersInfo("price");
    private ArrayList<String> category = getWorkersInfo("category");

    public void initialize()
    {
        loggedAs.setText(Main.loggedAs);
        tableView.setEditable(true);
        listWorkers();
    }

    public void backAction()
    {
        StageProperty.loadView("manager", anchorPane, this.getClass());
    }

    public void logOutAction()
    {
        StageProperty.loadView("login", anchorPane, this.getClass());
    }

    public void createDishAction()
    {
        StageProperty.loadView("newDish", anchorPane, this.getClass());
    }

    /**
     * get data about workers
     * and list all workers
     */
    private void listWorkers()
    {
        TableColumn nameCol = new TableColumn("Name");
        TableColumn<Object, Object> positionCol = new TableColumn<>("Category");
        TableColumn<Object, Object> priceCol = new TableColumn<>("Price");

        nameCol.setPrefWidth(tableView.getPrefWidth() / 3);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        positionCol.setPrefWidth(tableView.getPrefWidth() / 3);
        positionCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        priceCol.setPrefWidth(tableView.getPrefWidth() / 3);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(nameCol, positionCol,priceCol);

        tableView.setRowFactory(tv ->
        {
            TableRow<Dish> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent ->
            {
                if( !row.isEmpty() && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 1 )
                    showContextMenu(row.getIndex(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            });
            return row;
        });

        ObservableList<Dish> data = FXCollections.observableArrayList();

        for( int i = 0; i < name.size(); i++ )
        {
            data.add(new Dish(name.get(i), price.get(i), category.get(i)));
        }

        FilteredList<Dish> flDish = new FilteredList(data, p -> true);
        tableView.setItems(flDish);

        search.setOnKeyReleased(keyEvent ->
                flDish.setPredicate(p -> p.getCategory().toLowerCase().contains(search.getText().toLowerCase().trim()))
        );

        if( !loggedAs.getText().contains("Manager") )
        {
            createAccountButton.setVisible(false);
        }
    }

    /**
     * create context menu for one row
     *
     * @param index
     * @param X
     * @param Y
     */
    private void showContextMenu( int index, double X, double Y )
    {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");

        edit.setOnAction(actionEvent1 ->
        {
            EditDishController.dishNameDB = name.get(index);
            StageProperty.loadView("editDish", anchorPane, this.getClass());
        });

        contextMenu.getItems().addAll(edit);

        contextMenu.show(anchorPane, X, Y);
        anchorPane.setOnMousePressed(mouseEvent -> contextMenu.hide());
        tableView.setOnMousePressed(mouseEvent -> contextMenu.hide());
    }

    /**
     * get data form DB
     *
     * @param attribute
     * @return
     */
    private ArrayList<String> getWorkersInfo( String attribute )
    {
        ArrayList<String> result = new ArrayList<>();
        Connection connection = new ConnectionClass().getConnection();
        try
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT " + attribute + " FROM dishes;";
            ResultSet resultSet = statement.executeQuery(sql);
            while( resultSet.next() )
                result.add(resultSet.getString(1));
            connection.close();
        } catch( SQLException e )
        {
            e.printStackTrace();
        }

        return result;
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
}