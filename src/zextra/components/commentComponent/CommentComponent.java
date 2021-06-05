package zextra.components.commentComponent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Post;

public class CommentComponent {
    HBox container1;
    public CommentComponent(Post post)
    {
        container1 = new HBox();
        VBox container2 = new VBox();
        JFXTextArea postContent = new JFXTextArea(post.getText());
        JFXButton reply = new JFXButton("See Replies");
        reply.setStyle("-fx-text-fill: #0033eb; -fx-font-size: 15px;");

        container2.setAlignment(Pos.TOP_RIGHT);
        container2.getChildren().addAll(postContent,reply);
        container2.setSpacing(10);
        container1.getChildren().add(container2);
        container1.setPadding(new Insets(30,30,30,30));
        container1.setStyle("" +
                "-fx-border-radius:50;" +
                "-fx-border-color: #4db6ac;" +
                "-fx-border-width: 5 5 5 5;");
        postContent.setEditable(false);
        container2.setFillWidth(true);
    }

    public HBox getCommentContainer() {
        return container1;
    }
}
