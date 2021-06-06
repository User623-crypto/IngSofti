package view.user;

import error.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.Course;
import model.Post;
import model.User;
import model.dao.FriendRequestDao;
import model.dao.PostDao;
import zextra.ControllerClass;
import zextra.Session;
import zextra.components.commentComponent.CommentComponent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable, ControllerClass {
    /**Courses Table Properties*/
    @FXML
    private TableView<Course> courseTable;
    @FXML private TableColumn<Course,String> courseName;
    ObservableList<Course> courses = FXCollections.observableArrayList();

    @FXML
    Label nameLabel;
    @FXML
    VBox timeLineContainer;
    User selectedUser;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void preloadData(Object object) {
        selectedUser = (User) object;
        Course.initCoursesTable(courseTable,courseName,courses,selectedUser.getId());

        nameLabel.setText(selectedUser.getName());

        try {
            List<Post> usersPosts = new PostDao().readPostsFromUser(selectedUser.getId());
            for (Post usersPost: usersPosts) {
                timeLineContainer.getChildren().add(0,new CommentComponent(usersPost).getCommentContainer());
            }

        } catch (Exception exception) {
            ErrorHandler.generateError("Oops couldn't load the notification",()->{});
        }

    }


}
