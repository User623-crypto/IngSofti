package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import error.ErrorHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;
import model.dao.FriendRequestDao;
import model.dao.NotificationDao;
import model.dao.PostDao;
import zextra.ControllerClass;
import zextra.SceneChanger;
import zextra.Session;
import zextra.components.commentComponent.CommentComponent;
import zextra.components.jfx_list_component.FriendRequestCell;
import zextra.components.jfx_list_component.NotificationCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, ControllerClass {
    @FXML
    JFXListView<Notification> notificationList;
    ObservableList<Notification> notificationListView = FXCollections.observableArrayList();

    @FXML
    JFXListView<FriendRequest> friendRequestList;
    ObservableList<FriendRequest> friendRequestListView = FXCollections.observableArrayList();
    @FXML
    JFXButton logoutButton;
    @FXML Label nameLabel;
    @FXML
    VBox timeLineContainer;
    @FXML JFXTextArea postArea;
    SceneChanger sceneChanger = new SceneChanger();

    /**Courses Table Properties*/
    @FXML private TableView<Course> courseTableView;
    @FXML private TableColumn<Course,String> nameCol;
    ObservableList<Course> courses = FXCollections.observableArrayList();

    /**Friends Table Properties*/
    @FXML private TableView<User> friendsTable;
    @FXML private TableColumn<User,String> friendNameCol;
    ObservableList<User> friends = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationList.setItems(notificationListView);
        notificationList.setCellFactory(param -> new NotificationCell());
        friendRequestList.setItems(friendRequestListView);
        friendRequestList.setCellFactory(param -> new FriendRequestCell());
        User.initFriendsTable(friendsTable,friendNameCol,friends);
        Course.initCoursesTable(courseTableView,nameCol,courses);
    }

    @Override
    public void preloadData(Object object) {

        nameLabel.setText(Session.userSession.getName());
        if (Session.userSession.isNotification_on())
        {
            try {
                notificationListView.addAll(new NotificationDao().getNotifications(Session.userSession.getId()));
            } catch (Exception exception) {
                ErrorHandler.generateError("Oops couldn't load the notification",()->{});
            }
        }
        try {
            friendRequestListView.addAll(new FriendRequestDao().getFriendsRequest(Session.userSession.getId()));
            List<Post> usersPosts = new PostDao().readPostsFromUser(Session.userSession.getId());
            for (Post usersPost: usersPosts) {
                timeLineContainer.getChildren().add(0,new CommentComponent(usersPost).getCommentContainer());
            }

        } catch (Exception exception) {
            ErrorHandler.generateError("Oops couldn't load the notification",()->{});
        }



    }

    public void LogoutButtonPushed(ActionEvent event) {
        Session.userSession = null;

        try {
            sceneChanger.changeScene(event,"/view/LoginView.fxml","LoginView");
        } catch (Exception e) {
            ErrorHandler.generateError(e.getMessage(),()->{

            });
        }
    }

    public void settingsButtonPushed(ActionEvent event) {


        try {
            sceneChanger.changeScene(event,"/view/user/EditView.fxml","Settings",Session.userSession);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            ErrorHandler.generateError(e.getMessage(),()->{

            });
        }
    }

    public void checkCoursesDetail()
    {
        Course a = courseTableView.getSelectionModel().getSelectedItem();
        if (a == null) {
            System.out.println("Something is wrong");
            return;
        }
        try {
            sceneChanger.createStage("/view/courses/MainView.fxml","Courses",a,logoutButton);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            ErrorHandler.generateError("Internal Not Found Stage error",()->{});
        }
    }

    public void postButtonPushed()
    {
        Post newPost = new Post(0,postArea.getText());
        newPost.setPost_user(Session.userSession.getId());
        try {
            newPost = new PostDao().insertPostAndReturn(newPost);
            timeLineContainer.getChildren().add(0,new CommentComponent(newPost).getCommentContainer());

        }catch (Exception e)
        {
            ErrorHandler.generateError("Oops something went wrong",()->{});
            e.printStackTrace();
        }

    }
}
