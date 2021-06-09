package view.calendar;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Course;
import model.User;
import model.dao.CourseDao;
import zextra.ControllerClass;
import zextra.Session;

import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class FullCalendarView {

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    private List<Course> courses = new ArrayList<>();


    /**
     * Create a calendar view
     * @param yearMonth year month to create the calendar of
     */
    public FullCalendarView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        AnchorPaneNode.yearMonth = currentYearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200,200);
                calendar.add(ap,j,i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Monday"), new Text("Tuesday"),
                new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                new Text("Saturday"), new Text("Sunday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(800);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);

        // Information about green days
        HBox info = new HBox();

        info.setPadding(new Insets(20));
        Label greenBox = new Label();
        greenBox.setPrefSize(30, 30);
        greenBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.DEFAULT_WIDTHS)));
        greenBox.setStyle("-fx-background-color: #52ff52");
        Text infoText = new Text("Days with courses");
        infoText.setFont(Font.font(18));
        info.getChildren().add(greenBox);
        info.getChildren().add(infoText);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar, info);
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) {
        List<Course> courses = new ArrayList<>();

        int userId = Session.userSession.getId();
        try {
            courses = CourseDao.getCoursesByUser(userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        Month currentMonth = yearMonth.getMonth();
        // Dial back the day until it is MONDAY (unless the month starts on a monday)
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }
            Text txt;
            if(currentMonth.equals(calendarDate.getMonth()))
                txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            else
                txt = new Text();
            ap.setDate(calendarDate);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);

            boolean hasCourse = false;
            List<Course> apCourse = new ArrayList<>();
            for(Course c : courses){
                if(calendarDate.getDayOfWeek().ordinal()+1 == c.getDay()) {
                    hasCourse = true;
                    apCourse.add(c);
                }
            }
            ap.setCourses(apCourse);

            if(calendarDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                    || calendarDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
            {
                ap.setDisable(true);
                if(currentMonth.equals(calendarDate.getMonth()))
                    ap.setStyle("-fx-background-color: lightgray; -fx-border-color: black, black, black, black");
                else
                    ap.setStyle("-fx-background-color: gray; -fx-border-color: black, black, black, black");
            }
            else {
                if(hasCourse && currentMonth.equals(calendarDate.getMonth()))
                    ap.setStyle("-fx-background-color: #52ff52; -fx-border-color: black, black, black, black");
                else if(currentMonth.equals(calendarDate.getMonth()))
                    ap.setStyle("-fx-background-color: white; -fx-border-color: black, black, black, black");
                else
                    ap.setStyle("-fx-background-color: gray; -fx-border-color: black, black, black, black");
            }


            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        AnchorPaneNode.yearMonth = currentYearMonth;
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        AnchorPaneNode.yearMonth = currentYearMonth;
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
