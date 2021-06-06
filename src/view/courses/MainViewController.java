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
import model.User;
import model.dao.CommentDao;
import model.dao.CourseDao;
import model.dao.UserDao;
import zextra.ControllerClass;
import zextra.Session;
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

        try {
            comments = CommentDao.getCommentsByCourse(selectedCourse.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for(Comment c: comments) {
            VBox commentVBox = new VBox();
            commentVBox.setPrefHeight(171);
            commentVBox.setPrefWidth(842);
            commentVBox.setPadding(new Insets(10));
            Text userName = new Text(c.getUser_name());
            userName.setFont(Font.font(20));
//            Label userNameLabel = new Label(userName);
//            userNameLabel.setPadding(new Insets(10,10,10,10));
            Label commentBody = new Label(c.getComment_body());

            commentVBox.getChildren().add(userName);
            commentVBox.getChildren().add(commentBody);
            HBox commentHBox = new HBox();
            commentHBox.setAlignment(Pos.CENTER_RIGHT);
            commentHBox.setPrefHeight(100);
            commentHBox.setPrefWidth(200);
            commentHBox.setSpacing(10);

            Button likeButton = new Button("Like");
            likeButton.setMnemonicParsing(false);
            Label likeNumberLabel = new Label(c.getNo_of_likes().toString());
            Button replyButton = new Button("Reply");
            /*
             *  TODO: Add event listener in reply button to redirect to
              *  adding a comment with id_thread = this comment id (c.id)
             */

            commentHBox.getChildren().add(likeButton);
            commentHBox.getChildren().add(likeNumberLabel);
            commentHBox.getChildren().add(replyButton);

            commentHBox.setPadding(new Insets(10, 10, 10, 10));

            commentVBox.getChildren().add(commentHBox);

            List<Comment> replies = new ArrayList<>();
            try {
                replies = CommentDao.getRepliesByComment(c.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            commentSection.getChildren().add(commentVBox);

            for(Comment r : replies) {
                VBox replyVBox = new VBox();
                replyVBox.setPrefHeight(171);
                replyVBox.setPrefWidth(812);
                Text replyUserName = new Text(r.getUser_name());
                replyUserName.setFont(Font.font(20));
//                Label replyUserName = new Label(r.getUser_name());
                Label replyBody = new Label(r.getComment_body());

                replyVBox.getChildren().add(replyUserName);
                replyVBox.getChildren().add(replyBody);
                HBox replyHBox = new HBox();
                replyHBox.setAlignment(Pos.CENTER_RIGHT);
                replyHBox.setPrefHeight(100);
                replyHBox.setPrefWidth(200);
                replyHBox.setSpacing(10);

                Button replyLikeButton = new Button("Like");
                replyLikeButton.setMnemonicParsing(false);
                Label replyLikeNumberLabel = new Label(c.getNo_of_likes().toString());


                replyHBox.getChildren().add(replyLikeButton);
                replyHBox.getChildren().add(replyLikeNumberLabel);

                replyHBox.setPadding(new Insets(10, 10, 10, 10));

                replyVBox.getChildren().add(replyHBox);
                replyVBox.setPadding(new Insets(0, 0, 0, 30));
                commentSection.getChildren().add(replyVBox);
            }

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
