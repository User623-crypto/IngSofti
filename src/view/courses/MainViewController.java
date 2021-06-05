package view.courses;

import com.jfoenix.controls.JFXListView;
import error.ErrorCallBack;
import error.ErrorHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Course;
import model.User;
import model.dao.CourseDao;
import model.dao.UserDao;
import zextra.ControllerClass;
import zextra.Session;
import zextra.components.jfx_list_component.FriendsCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, ControllerClass {
    @FXML
    private JFXListView<User> usersInCourseList;
    ObservableList<User> attendee = FXCollections.observableArrayList();

    @FXML
    private Label nameLabel;
    @FXML private Label
            dayLabel;
    @FXML private Label timeLabel;
    @FXML
    private Button enrollButton;
    @FXML private Button dropButton;

    private Course selectedCourse;
    private boolean isEnrolled;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersInCourseList.setItems(attendee);
        usersInCourseList.setCellFactory(param -> new FriendsCell());
    }

    @Override
    public void preloadData(Object object) {
        selectedCourse = (Course) object;
        nameLabel.setText(selectedCourse.getName());
        dayLabel.setText(selectedCourse.getDay()+"");
        timeLabel.setText(selectedCourse.getTime());
        try{

            isEnrolled = new UserDao().isEnrolled(Session.userSession.getId(),selectedCourse.getId());
            toggleEnrollButton(isEnrolled);
            List<User> array = new CourseDao().readUsersInCourse(selectedCourse);
            attendee.addAll(array);




        }catch (Exception exception){
            ErrorHandler.generateError("There is a Grave error try again late ", Platform::exit);
        }

    }

    /**
     * Makes the enrolled button visible is the user is not enrolled and it makes the
     * drop button visible if the user is enrolled
     * @param isEnrolled is the student enrolled in the course
     */
    private void toggleEnrollButton(boolean isEnrolled)
    {
        enrollButton.setVisible(!isEnrolled);
        dropButton.setVisible(isEnrolled);
    }

    /**
     * When enroll Button is pushed this saves the user into the course
     */
    public void enrollButtonPushed()
    {
        try{
            new UserDao().insertUserIntoCourse(Session.userSession.getId(),selectedCourse);

            toggleEnrollButton(true);

        }catch (Exception e)
        {
            ErrorHandler.generateError(e.getMessage(),null);
            e.printStackTrace();

        }
    }

    /**
     * When the dropButton is Pushed this function removes the user from the course
     */
    public void dropButtonPushed()
    {
        try{
            new UserDao().deleteUserFromCourse(Session.userSession.getId(),selectedCourse.getId());

            toggleEnrollButton(false);

        }catch (Exception e)
        {
            ErrorHandler.generateError(e.getMessage(),null);

        }
    }

}
