package view.user;

import error.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import language.LanguageController;
import model.Comment;
import model.Course;
import model.Helpers;
import model.User;
import model.dao.CommentDao;
import zextra.ControllerClass;
import zextra.components.commentComponent.CommentComponent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable, ControllerClass {

    public LanguageController lang = new LanguageController();
    /**Courses Table Properties*/
    @FXML
    public TableView<Course> courseTable;
    @FXML public TableColumn<Course,String> courseName;
    ObservableList<Course> courses = FXCollections.observableArrayList();

    @FXML
    Label nameLabel;
    @FXML
    VBox timeLineContainer;
    User selectedUser;

    @FXML
    Tab courseTab;
    @FXML Tab timeLineTab;

    @FXML Label enrolledCoursesLabel;
    @FXML Label coursesLabel;
    @FXML Label timelineLabel;
    @FXML Label postsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseName.setText(lang.COURSE_NAME_TEXT);
        enrolledCoursesLabel.setText(lang.ENROLLED_COURSES_TEXT);
        coursesLabel.setText(lang.COURSES_TEXT);
        timelineLabel.setText(lang.TIMELINE_TEXT);
        postsLabel.setText(lang.POSTS_TEXT);
    }

    @Override
    public void preloadData(Object object) {
        selectedUser = (User) object;
        Course.initCoursesTable(courseTable,courseName,courses,selectedUser.getId());

        nameLabel.setText(selectedUser.getName());

        try {
            List<Comment> usersPosts = new CommentDao().getComments(Helpers.CommentType.POST_UPDATE.ordinal(), null, null, selectedUser.getId());
            for (Comment usersPost: usersPosts) {
                timeLineContainer.getChildren().add(0,new CommentComponent(usersPost).getCommentContainer());
            }

        } catch (Exception exception) {
            ErrorHandler.generateError("Oops couldn't load the notification",()->{});
        }

    }


}
