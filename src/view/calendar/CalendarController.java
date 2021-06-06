package view.calendar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import zextra.ControllerClass;
import zextra.SceneChanger;
import zextra.Session;

import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class CalendarController implements Initializable, ControllerClass {
    SceneChanger sceneChanger = new SceneChanger();
    // Get the pane to put the calendar on
    @FXML public Pane calendarPane;

    public CalendarController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarPane.getChildren().add(new FullCalendarView(YearMonth.now()).getView());
    }

    @Override
    public void preloadData(Object object) {

    }
}
