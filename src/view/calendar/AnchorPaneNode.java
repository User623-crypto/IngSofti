package view.calendar;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Window;
import language.LanguageController;
import model.Course;
import model.Helpers;
import zextra.ControllerClass;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    public static YearMonth yearMonth;
    // Date associated with this pane
    private LocalDate date;



    private List<Course> courses = new ArrayList<>();

    public LanguageController lang = new LanguageController();

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> {
            if(yearMonth.getMonth().equals(date.getMonth())) {
                Dialog d = new Dialog();
                DialogPane dp = new DialogPane();
                if(courses.size() == 0){
                    return;
                }

                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(10));
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                Text coursesText = new Text(lang.COURSES_TEXT);
                coursesText.setFont(Font.font(20));
                Text timeText = new Text(lang.TIME_TEXT);
                timeText.setFont(Font.font(20));
                gridPane.add(coursesText, 0, 0, 1, 1);
                gridPane.add(timeText, 1, 0, 1, 1);
                int i = 1;
                for(Course c : courses){
                    Text courseName = new Text(c.getName());
                    Text courseTime = new Text(c.getTime());

                    gridPane.add(courseName, 0, i, 1, 1);
                    gridPane.add(courseTime, 1, i, 1, 1);
                }

                dp.getChildren().add(gridPane);
                dp.setPadding(new Insets(10));
                d.setDialogPane(dp);
                d.setTitle(lang.COURSES_ON_TEXT + " " + date.getDayOfMonth() + " " + lang.MONTHS_TEXT[date.getMonthValue() - 1]);
                Window window = d.getDialogPane().getScene().getWindow();
                window.setOnCloseRequest((evt) -> {
                    d.close();
                });
                d.show();


            }
        });
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
