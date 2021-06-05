package view.calendar;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.Helpers;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    public static YearMonth yearMonth;
    // Date associated with this pane
    private LocalDate date;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> {
            if(yearMonth.getMonth().equals(date.getMonth())) {
                System.out.println(date.getDayOfWeek().ordinal());
                /*
                 * TODO: shto popup qe shfaq oraret dhe kursin qe ndodhet ne ate dite
                 */
            }
        });
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
