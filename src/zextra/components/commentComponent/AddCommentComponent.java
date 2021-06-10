package zextra.components.commentComponent;

import com.jfoenix.controls.JFXTextArea;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import model.Comment;
import model.Helpers;
import model.dao.CommentDao;
import zextra.Session;

import java.sql.SQLException;
import java.util.List;

public class AddCommentComponent {

    Stage stage;
    Comment newComment;

    public AddCommentComponent(Integer comment_type, Integer id_thread, Integer id_course, List<Comment> replies){
        AnchorPane mainAP = new AnchorPane();
        VBox mainWrapper = new VBox();

        HBox titleHBox = new HBox();
        Text title = null;
        if(comment_type == Helpers.CommentType.BASIC_COMMENT.ordinal())
            title = new Text("Add comment");
        else if(comment_type == Helpers.CommentType.REPLY.ordinal())
            title = new Text("Add reply");
        else if(comment_type == Helpers.CommentType.POST_UPDATE.ordinal())
            title = new Text("Add post");

        titleHBox.setPadding(new Insets(5));
        titleHBox.getChildren().add(title);

        HBox commentHBox = new HBox();
        JFXTextArea comment_body = new JFXTextArea();

        commentHBox.setPadding(new Insets(10));
        commentHBox.getChildren().add(comment_body);

        HBox buttonsHBox = new HBox();
        Button addComment = null;
        if(comment_type == Helpers.CommentType.BASIC_COMMENT.ordinal())
            addComment = new Button("Add comment");
        else if(comment_type == Helpers.CommentType.REPLY.ordinal())
            addComment = new Button("Add reply");
        else if(comment_type == Helpers.CommentType.POST_UPDATE.ordinal())
            addComment = new Button("Add post");
        Button cancel = new Button("Cancel");

        addComment.setOnAction(evt -> {
            try {
                this.newComment = new CommentDao().insertIntoDB(new Comment(id_thread, id_course, Session.userSession.getId(), comment_type, comment_body.getText()));
                replies.add(this.newComment);
                //container.getChildren().add();
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        buttonsHBox.setPadding(new Insets(10));
        buttonsHBox.getChildren().addAll(addComment, cancel);

        mainWrapper.setPadding(new Insets(10));
        mainWrapper.getChildren().addAll(titleHBox, commentHBox, buttonsHBox);

        mainAP.getChildren().add(mainWrapper);

        Scene scene = new Scene(mainAP);

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add comment");
        stage.showAndWait();

        cancel.setOnAction(evt -> {
            stage.close();
        });
    }

    public VBox AddedCommentContainer(){
        VBox replyVBox = new VBox();
        replyVBox.setPrefHeight(171);
        replyVBox.setPrefWidth(812);

        Text replyUserName = new Text(this.getNewComment().getUser_name());
        replyUserName.setFont(Font.font(20));
        Label replyBody = new Label(this.getNewComment().getComment_body());

        replyVBox.getChildren().add(replyUserName);
        replyVBox.getChildren().add(replyBody);
        HBox replyHBox = new HBox();
        replyHBox.setAlignment(Pos.CENTER_RIGHT);
        replyHBox.setPrefHeight(100);
        replyHBox.setPrefWidth(200);
        replyHBox.setSpacing(10);

        Button replyLikeButton = new Button("Like");
        replyLikeButton.setMnemonicParsing(false);
        boolean checkIfReplyLiked;
        try {
            checkIfReplyLiked = new CommentDao().checkIfLiked(this.getNewComment().getId(), Session.userSession.getId());
            replyLikeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> checkIfReplyLiked));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Integer replyLikeNumber = 0;
        try {
            replyLikeNumber = new CommentDao().getLikes(this.getNewComment().getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Label replyLikeNumberLabel = new Label(String.valueOf(replyLikeNumber));

        int finalReplyLikeNumber = replyLikeNumber;
        replyLikeButton.setOnAction(evt -> {
            try {
                new CommentDao().addLikeToComment(this.getNewComment().getId(), Session.userSession.getId());
                replyLikeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> true));
                replyLikeNumberLabel.setText(String.valueOf(finalReplyLikeNumber +1));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        replyHBox.getChildren().add(replyLikeButton);
        replyHBox.getChildren().add(replyLikeNumberLabel);

        replyHBox.setPadding(new Insets(10, 10, 10, 10));

        replyVBox.getChildren().add(replyHBox);

        replyVBox.setStyle(
                "-fx-border-radius:10;" +
                        "-fx-border-color: #4db6ac;" +
                        "-fx-border-width: 2 2 2 2;");

        VBox.setMargin(replyVBox, new Insets(0,0,0,50));
        return replyVBox;
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
