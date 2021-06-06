package zextra.components.commentComponent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Comment;
import model.Post;
import model.dao.CommentDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comment2Component {
    VBox commentVBox;



    public Comment2Component(Comment c, VBox commentSection)
    {
        commentVBox = new VBox();
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

        for (Comment r : replies) {
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

    public VBox getCommentContainer() {
        return commentVBox;
    }
}
