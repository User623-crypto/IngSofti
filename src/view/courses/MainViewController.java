package view.courses;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import error.ErrorCallBack;
import error.ErrorHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Comment;
import model.Course;
import model.Post;
import model.User;
import model.dao.CommentDao;
import model.dao.CourseDao;
import model.dao.PostDao;
import model.dao.UserDao;
import zextra.ControllerClass;
import zextra.Session;
import zextra.components.commentComponent.Comment2Component;
import zextra.components.commentComponent.CommentComponent;
import zextra.components.jfx_list_component.FriendsCell;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, ControllerClass {
    @FXML
    public JFXListView<User> usersInCourseList;
    ObservableList<User> attendee = FXCollections.observableArrayList();

    public List<Comment> comments = new ArrayList<>();

    @FXML
    public Label nameLabel;
    @FXML public Label
            dayLabel;
    @FXML public Label timeLabel;
    @FXML
    public Button enrollButton;
    @FXML public Button dropButton;

    public Course selectedCourse;
    public boolean isEnrolled;

    @FXML
    public VBox commentSection;

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


        } catch (Exception exception) {
            ErrorHandler.generateError("There is a Grave error try again late ", Platform::exit);
        }

        try {
            comments = CommentDao.getCommentsByCourse(selectedCourse.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(comments.size());
//        for (Comment c : comments) {
//            commentSection.getChildren().add(0,new Comment2Component(c).getCommentContainer());
//        }
        for (Comment c : comments) {
            new Comment2Component(c, commentSection);
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
