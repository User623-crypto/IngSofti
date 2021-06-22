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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import language.LanguageController;
import model.*;
import model.dao.CommentDao;
import model.dao.FriendRequestDao;
import model.dao.NotificationDao;
import zextra.ControllerClass;
import zextra.SceneChanger;
import zextra.Session;
import zextra.components.commentComponent.AddCommentComponent;
import zextra.components.commentComponent.CommentComponent;
import zextra.components.jfx_list_component.FriendRequestCell;
import zextra.components.jfx_list_component.NotificationCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, ControllerClass {
    @FXML
    public JFXListView<Notification> notificationList;
    public ObservableList<Notification> notificationListView = FXCollections.observableArrayList();

    @FXML
    public JFXListView<FriendRequest> friendRequestList;
    public ObservableList<FriendRequest> friendRequestListView = FXCollections.observableArrayList();
    @FXML
    public JFXButton logoutButton;
    @FXML public Label nameLabel;
    @FXML
    public VBox timeLineContainer;
    @FXML public JFXTextArea postArea;
    SceneChanger sceneChanger = new SceneChanger();

    /**Courses Table Properties*/
    @FXML public TableView<Course> courseTableView;
    @FXML public TableColumn<Course,String> nameCol;
    public ObservableList<Course> courses = FXCollections.observableArrayList();

    /**Friends Table Properties*/
    @FXML public TableView<User> friendsTable;
    @FXML public TableColumn<User,String> friendNameCol;
    public ObservableList<User> friends = FXCollections.observableArrayList();

    @FXML JFXButton settingsButton;
    @FXML JFXButton calendarBtn;
    @FXML Label avatarLabel;
    @FXML Tab timelineTab;
    @FXML Tab friendsTab;
    @FXML MenuItem seeDetailsMenu;
    @FXML Tab noticesTab;
    @FXML Label friendRequestLabel;
    @FXML Label notificationsLabel;
    @FXML Tab coursesTab;
    @FXML JFXButton searchCourseBtn;
    @FXML MenuItem courseDetailsMenu;

    public LanguageController lang = new LanguageController();

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
                ErrorHandler.generateError(lang.DATABASE_CONNECTION_ERROR_TEXT,()->{});
            }
        }

        Button addComment = new Button(lang.ADD_POST_TEXT);
        HBox commentHBox = new HBox();
        commentHBox.setPadding(new Insets(20));
        commentHBox.getChildren().add(addComment);
        timeLineContainer.getChildren().add(commentHBox);


        addComment.setOnAction(evt -> {
            AddCommentComponent added = new AddCommentComponent(Helpers.CommentType.POST_UPDATE.ordinal(), null, null);
            if(added.getNewComment() == null)
                return;
            timeLineContainer.getChildren().add(1, new CommentComponent(added.getNewComment()).getCommentContainer());
        });

        try {
            friendRequestListView.addAll(new FriendRequestDao().getFriendsRequest(Session.userSession.getId()));
            List<Comment> usersPosts = new CommentDao().getComments(Helpers.CommentType.POST_UPDATE.ordinal(), null, null, Session.userSession.getId());
            for (Comment usersPost: usersPosts) {
                timeLineContainer.getChildren().add(1,new CommentComponent(usersPost).getCommentContainer());
            }

        } catch (Exception exception) {
            ErrorHandler.generateError(lang.DATABASE_CONNECTION_ERROR_TEXT,()->{});
        }




        logoutButton.setText(lang.LOGOUT_TEXT);
        friendNameCol.setText(lang.FRIENDS_TEXT);
        settingsButton.setText(lang.CONFIGURATION_TEXT);
        calendarBtn.setText(lang.CALENDAR_TEXT);
        avatarLabel.setText(lang.AVATAR_TEXT);
        timelineTab.setText(lang.TIMELINE_TEXT);
        friendsTab.setText(lang.FRIENDS_TEXT);
        seeDetailsMenu.setText(lang.SEE_DETAILS_TEXT);
        noticesTab.setText(lang.NOTICES_TEXT);
        friendRequestLabel.setText(lang.FRIEND_REQUEST_TEXT);
        notificationsLabel.setText(lang.NOTIFICATIONS_TEXT);
        coursesTab.setText(lang.COURSES_TEXT);
        searchCourseBtn.setText(lang.SEARCH_COURSES_TEXT);
        nameCol.setText(lang.COURSES_TEXT);
    }

    /**
     * Logs out the user
     * @param event Stage
     */
    public void LogoutButtonPushed(ActionEvent event) {
        Session.userSession = null;

        try {
            sceneChanger.changeScene(event,"/view/LoginView.fxml","LoginView");
        } catch (Exception e) {
            ErrorHandler.generateError(e.getMessage(),()->{

            });
        }
    }

    /**
     * Opens the configuration tab
     * @param event Stage
     */
    public void settingsButtonPushed(ActionEvent event) {


        try {
            sceneChanger.changeScene(event,"/view/user/EditView.fxml",lang.CONFIGURATION_TEXT,Session.userSession);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            ErrorHandler.generateError(e.getMessage(),()->{

            });
        }
    }

    /**
     * Opens the course detail stage
     */
    public void checkCoursesDetail()
    {
        Course a = courseTableView.getSelectionModel().getSelectedItem();
        if (a == null) {
            System.out.println("Something is wrong");
            return;
        }
        try {
            sceneChanger.createStage("/view/courses/MainView.fxml",a.getName(),a,logoutButton);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            ErrorHandler.generateError(lang.STAGE_ERROR_TEXT,()->{});
        }
    }

    /**
     * Opens the courses calendar stage
     */
    public void checkCoursesCalendar()
    {
        int userId = Session.userSession.getId();

        try {
            sceneChanger.createStage("/view/calendar/fullCalendar.fxml",lang.CALENDAR_TEXT,userId,logoutButton);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            ErrorHandler.generateError(lang.STAGE_ERROR_TEXT,()->{});
        }
    }
}
