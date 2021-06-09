package view.courses;

import com.jfoenix.controls.JFXListView;
import error.ErrorHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Course;
import model.Helpers;
import model.User;
import model.dao.CommentDao;
import model.dao.CourseDao;
import model.dao.UserDao;
import zextra.ControllerClass;
import zextra.Session;
import zextra.components.commentComponent.CommentComponent;
import zextra.components.jfx_list_component.FriendsCell;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, ControllerClass {
    @FXML
    private JFXListView<User> usersInCourseList;
    ObservableList<User> attendee = FXCollections.observableArrayList();

    private List<Comment> comments = new ArrayList<>();
    private List<Comment> announcements = new ArrayList<>();

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

    @FXML
    private VBox commentSection;
    @FXML
    private VBox announcementSection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersInCourseList.setItems(attendee);
        usersInCourseList.setCellFactory(param -> new FriendsCell());
    }

    @Override
    public void preloadData(Object object) {
        selectedCourse = (Course) object;
        nameLabel.setText(selectedCourse.getName());
        dayLabel.setText(selectedCourse.getDay() + "");
        timeLabel.setText(selectedCourse.getTime());
        try {

            isEnrolled = new UserDao().isEnrolled(Session.userSession.getId(), selectedCourse.getId());
            toggleEnrollButton(isEnrolled);
            List<User> array = new CourseDao().readUsersInCourse(selectedCourse);
            attendee.addAll(array);

            comments = new CommentDao().getComments(Helpers.CommentType.BASIC_COMMENT.ordinal(), null, selectedCourse.getId(), Session.userSession.getId());
            announcements = new CommentDao().getComments(Helpers.CommentType.ANNOUNCEMENT.ordinal(), null, selectedCourse.getId(), null);

        } catch (Exception exception) {
            ErrorHandler.generateError("There is a Grave error try again late ", Platform::exit);
        }


        for (Comment c : comments) {
            commentSection.getChildren().add(new CommentComponent(c).getCommentContainer());
        }
        for(Comment a: announcements){
            announcementSection.getChildren().add(new CommentComponent(a).getCommentContainer());
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
