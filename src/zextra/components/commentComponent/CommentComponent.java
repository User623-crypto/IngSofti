package zextra.components.commentComponent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Comment;
import model.Helpers;
import model.Post;
import model.dao.CommentDao;
import zextra.SceneChanger;
import zextra.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentComponent {
    VBox commentContainer;
    VBox repliesContainer;
    VBox commentVBox;

    SceneChanger sc = new SceneChanger();


    boolean showReplies = false;


    public CommentComponent(Comment c)
    {
        commentContainer = new VBox();
        commentVBox = new VBox();
        commentVBox.setPrefHeight(171);
        commentVBox.setPrefWidth(842);
        commentVBox.setPadding(new Insets(10));
        Text userName = new Text(c.getUser_name());
        userName.setFont(Font.font(20));
        Label commentBody = new Label(c.getComment_body());

        commentVBox.getChildren().add(userName);
        commentVBox.getChildren().add(commentBody);
        HBox commentHBoxInner = new HBox();
        commentHBoxInner.setAlignment(Pos.CENTER_RIGHT);
        commentHBoxInner.setPrefHeight(100);
        commentHBoxInner.setPrefWidth(200);
        commentHBoxInner.setSpacing(10);

        Button likeButton = new Button("Like");
        likeButton.setMnemonicParsing(false);
        boolean checkIfLiked;
        try {
            checkIfLiked = new CommentDao().checkIfLiked(c.getId(), Session.userSession.getId());
            likeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> checkIfLiked));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Integer no_of_likes = 0;
        try {
            no_of_likes = new CommentDao().getLikes(c.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Label likeNumberLabel = new Label(no_of_likes.toString());

        Button replyButton = new Button("Reply");
        JFXButton showRepliesButton = new JFXButton("Show replies");
        showRepliesButton.setStyle("-fx-text-fill: #0033eb; -fx-font-size: 15px;");


        Integer finalNo_of_likes = no_of_likes;
        likeButton.setOnAction(evt -> {
            try {
                new CommentDao().addLikeToComment(c.getId(), Session.userSession.getId());
                likeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> true));
                likeNumberLabel.setText(String.valueOf(finalNo_of_likes+1));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        commentHBoxInner.getChildren().add(likeButton);
        commentHBoxInner.getChildren().add(likeNumberLabel);
        commentHBoxInner.getChildren().add(replyButton);
        commentHBoxInner.getChildren().add(showRepliesButton);

        commentHBoxInner.setPadding(new Insets(10, 10, 10, 10));

        commentVBox.getChildren().add(commentHBoxInner);

        commentContainer.setStyle(
                "-fx-border-radius:10;" +
                "-fx-border-color: #4db6ac;" +
                "-fx-border-width: 2 2 2 2;");

        commentContainer.getChildren().add(commentVBox);

        repliesContainer = getRepliesVBox(c);

        showRepliesButton.setOnAction(evt -> {
            showReplies = !showReplies;
            repliesContainer.setVisible(showReplies);
            if(showReplies)
                showRepliesButton.setText("Hide replies");
            else
                showRepliesButton.setText("Show replies");
        });

        replyButton.setOnAction(evt -> {
            new AddCommentComponent(Helpers.CommentType.REPLY.ordinal(), c.getId(), Session.userSession.getId());
        });

        commentContainer.getChildren().add(repliesContainer);
        repliesContainer.setVisible(false);
        repliesContainer.managedProperty().bind(repliesContainer.visibleProperty());

    }

    public VBox getRepliesVBox(Comment c) {
        repliesContainer = new VBox();
        List<Comment> replies = new ArrayList<>();

        try {
            replies = new CommentDao().getComments(Helpers.CommentType.REPLY.ordinal(), c.getId(), c.getId_course(), null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(replies.size() != 0) {
            for (Comment r : replies) {
                VBox replyVBox = new VBox();
                replyVBox.setPrefHeight(171);
                replyVBox.setPrefWidth(812);

                Text replyUserName = new Text(r.getUser_name());
                replyUserName.setFont(Font.font(20));
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
                boolean checkIfReplyLiked;
                try {
                    checkIfReplyLiked = new CommentDao().checkIfLiked(r.getId(), Session.userSession.getId());
                    replyLikeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> checkIfReplyLiked));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Integer replyLikeNumber = 0;
                try {
                    replyLikeNumber = new CommentDao().getLikes(r.getId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Label replyLikeNumberLabel = new Label(String.valueOf(replyLikeNumber));

                int finalReplyLikeNumber = replyLikeNumber;
                replyLikeButton.setOnAction(evt -> {
                    try {
                        new CommentDao().addLikeToComment(r.getId(), Session.userSession.getId());
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

                repliesContainer.getChildren().add(replyVBox);
            }
        }
        else {
            Text noReplies = new Text("There are no replies to this comment");
            noReplies.setFont(Font.font(20));
            repliesContainer.getChildren().add(noReplies);
        }

        return  repliesContainer;
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
