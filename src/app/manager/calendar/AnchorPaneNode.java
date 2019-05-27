package app.manager.calendar;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane
{
    // Date associated with this pane
    public LocalDate date;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     *
     * @param children children of the anchor pane
     */
    public AnchorPaneNode( Node... children )
    {
        super(children);
        // Add action handler for mouse clicked
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate( LocalDate date )
    {
        this.date = date;
    }
}
