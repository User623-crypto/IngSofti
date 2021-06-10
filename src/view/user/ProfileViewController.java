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
import model.Course;
import model.Post;
import model.User;
import model.dao.PostDao;
import zextra.ControllerClass;
import zextra.components.commentComponent.PostComponent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable, ControllerClass {
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
                timeLineContainer.getChildren().add(0,new PostComponent(usersPost).getCommentContainer());
            }

        } catch (Exception exception) {
            ErrorHandler.generateError("Oops couldn't load the notification",()->{});
        }

    }


}
