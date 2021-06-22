package zextra.components.commentComponent;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import language.LanguageController;
import model.Comment;
import model.Helpers;
import model.dao.CommentDao;
import zextra.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentComponent {

    LanguageController lang = new LanguageController();

    VBox commentContainer;
    VBox repliesContainer;
    VBox commentVBox;

    List<Comment> replies = new ArrayList<>();



    boolean showReplies = false;


    public CommentComponent(Comment c)
    {

        commentContainer = new VBox();
        VBox.setMargin(commentContainer, new Insets(10));

        commentVBox = getSingleCommentContainer(c);
        commentVBox.setPadding(new Insets(10));

        commentContainer.setStyle(
                "-fx-border-radius:10;" +
                "-fx-border-color: #4db6ac;" +
                "-fx-border-width: 2 2 2 2;");
        commentContainer.getChildren().add(commentVBox);

        repliesContainer = getRepliesVBox(c);

        repliesContainer.setAlignment(Pos.CENTER_RIGHT);
        repliesContainer.setSpacing(5);
        VBox.setMargin(repliesContainer, new Insets(5,0,0,50));

        repliesContainer.setVisible(false);
        repliesContainer.managedProperty().bind(repliesContainer.visibleProperty());

        commentContainer.getChildren().add(repliesContainer);
    }

    public VBox getRepliesVBox(Comment c) {
        repliesContainer = new VBox();
        repliesContainer.setPadding(new Insets(10));
        try {
            replies = new CommentDao().getComments(Helpers.CommentType.REPLY.ordinal(), c.getId(), c.getId_course(), null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(replies.size() != 0) {
            for (Comment r : replies) {
                repliesContainer.getChildren().add(getSingleCommentContainer(r));
            }
        }
        else {
            Text noReplies = new Text(lang.NO_REPLIES_TEXT);
            noReplies.setFont(Font.font(20));
            repliesContainer.getChildren().add(noReplies);
        }

        return  repliesContainer;
    }

    public VBox getSingleCommentContainer(Comment c) {
        VBox singleComment = new VBox();


        Text commentUserName = new Text(c.getUser_name());
        commentUserName.setFont(Font.font(20));
        Label commentBody = new Label(c.getComment_body());

        singleComment.getChildren().add(commentUserName);
        singleComment.getChildren().add(commentBody);
        HBox commentHBox = new HBox();

        commentHBox.setAlignment(Pos.CENTER_RIGHT);
        commentHBox.setSpacing(10);

        Button commentLikeButton = new Button(lang.LIKE_TEXT);
        commentLikeButton.setMnemonicParsing(false);
        boolean checkIfCommentLiked;
        try {
            checkIfCommentLiked = new CommentDao().checkIfLiked(c.getId(), Session.userSession.getId());
            commentLikeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> checkIfCommentLiked));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Integer commentLikeNumber = 0;
        try {
            commentLikeNumber = new CommentDao().getLikes(c.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Label commentLikeNumberLabel = new Label(String.valueOf(commentLikeNumber));

        int finalCommentLikeNumber = commentLikeNumber;
        commentLikeButton.setOnAction(evt -> {
            try {
                new CommentDao().addLikeToComment(c.getId(), Session.userSession.getId());
                commentLikeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> true));
                commentLikeNumberLabel.setText(String.valueOf(finalCommentLikeNumber +1));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        commentHBox.getChildren().add(commentLikeButton);
        commentHBox.getChildren().add(commentLikeNumberLabel);

        if(c.getComment_type() != Helpers.CommentType.REPLY.ordinal()) {
            Button replyButton = new Button(lang.REPLY_TEXT);
            JFXButton showRepliesButton = new JFXButton(lang.SHOW_REPLIES_TEXT);
            showRepliesButton.setStyle("-fx-text-fill: #0033eb; -fx-font-size: 15px;");

            showRepliesButton.setOnAction(evt -> {
                setShowReplies(!showReplies);
                repliesContainer.setVisible(showReplies);
                if(showReplies)
                    showRepliesButton.setText(lang.HIDE_REPLIES_TEXT);
                else
                    showRepliesButton.setText(lang.SHOW_REPLIES_TEXT);
            });

            replyButton.setOnAction(evt -> {
                AddCommentComponent added = new AddCommentComponent(Helpers.CommentType.REPLY.ordinal(), c.getId(), Session.userSession.getId());
                if(added.getNewComment() == null)
                    return;
                repliesContainer.getChildren().add(0, getSingleCommentContainer(added.getNewComment()));
            });
            commentHBox.getChildren().add(replyButton);
            commentHBox.getChildren().add(showRepliesButton);
        }

        singleComment.getChildren().add(commentHBox);

        singleComment.setStyle(
                "-fx-border-radius:10;" +
                        "-fx-border-color: #4db6ac;" +
                        "-fx-border-width: 2 2 2 2;");

        return singleComment;
    }


    public boolean isShowReplies() {
        return showReplies;
    }

    public void setShowReplies(boolean showReplies) {
        this.showReplies = showReplies;
    }

    public VBox getCommentContainer() {
        return commentContainer;
    }

    public VBox getRepliesContainer() {return repliesContainer;}
}
