package zextra.components.commentComponent;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import language.LanguageController;
import model.Comment;
import model.Helpers;
import model.dao.CommentDao;
import zextra.Session;

import java.sql.SQLException;
import java.util.List;

public class AddCommentComponent {

    LanguageController lang = new LanguageController();


    Stage stage;
    Comment newComment;

    public AddCommentComponent(Integer comment_type, Integer id_thread, Integer id_course){
        AnchorPane mainAP = new AnchorPane();
        VBox mainWrapper = new VBox();

        HBox titleHBox = new HBox();
        Text title = null;
        if(comment_type == Helpers.CommentType.BASIC_COMMENT.ordinal())
            title = new Text(lang.ADD_COMMENT_TEXT);
        else if(comment_type == Helpers.CommentType.REPLY.ordinal())
            title = new Text(lang.ADD_REPLY_TEXT);
        else if(comment_type == Helpers.CommentType.POST_UPDATE.ordinal())
            title = new Text(lang.ADD_POST_TEXT);

        titleHBox.setPadding(new Insets(5));
        titleHBox.getChildren().add(title);

        HBox commentHBox = new HBox();
        JFXTextArea comment_body = new JFXTextArea();

        commentHBox.setPadding(new Insets(10));
        commentHBox.getChildren().add(comment_body);

        HBox buttonsHBox = new HBox();
        Button addComment = null;
        if(comment_type == Helpers.CommentType.BASIC_COMMENT.ordinal())
            addComment = new Button(lang.ADD_COMMENT_TEXT);
        else if(comment_type == Helpers.CommentType.REPLY.ordinal())
            addComment = new Button(lang.ADD_REPLY_TEXT);
        else if(comment_type == Helpers.CommentType.POST_UPDATE.ordinal())
            addComment = new Button(lang.ADD_POST_TEXT);

        addComment.setOnAction(evt -> {
            try {
                this.newComment = new CommentDao().insertIntoDB(new Comment(id_thread, id_course, Session.userSession.getId(), comment_type, comment_body.getText()));
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        buttonsHBox.setPadding(new Insets(10));
        buttonsHBox.getChildren().addAll(addComment);

        mainWrapper.setPadding(new Insets(10));
        mainWrapper.getChildren().addAll(titleHBox, commentHBox, buttonsHBox);

        mainAP.getChildren().add(mainWrapper);

        Scene scene = new Scene(mainAP);

        stage = new Stage();
        stage.setScene(scene);
        if(comment_type==Helpers.CommentType.BASIC_COMMENT.ordinal())
            stage.setTitle(lang.ADD_COMMENT_TEXT);
        if(comment_type==Helpers.CommentType.REPLY.ordinal())
            stage.setTitle(lang.ADD_REPLY_TEXT);
        if(comment_type==Helpers.CommentType.POST_UPDATE.ordinal())
            stage.setTitle(lang.ADD_POST_TEXT);

        stage.showAndWait();


    }




    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Comment getNewComment() {return newComment;}

    public void setNewComment(Comment comment) {this.newComment = comment;}
}
