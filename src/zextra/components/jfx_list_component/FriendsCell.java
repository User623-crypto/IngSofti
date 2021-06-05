package zextra.components.jfx_list_component;

import error.ErrorHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.User;
import model.dao.FriendRequestDao;
import zextra.Session;
import zextra.UtilityZ;

import java.io.File;
import java.sql.SQLException;

public class FriendsCell extends ListCell<User> {

    private HBox hbox = new HBox();
    private Button btn = new Button("Send Friend Request");
    private Pane pane = new Pane();
    private Label label = new Label("");
    private ImageView img = new ImageView();

    public FriendsCell(){
        super();
        this.hbox.getChildren().addAll(img,label,pane,btn);
        this.hbox.setHgrow(pane, Priority.ALWAYS);
        hbox.setSpacing(10);

        btn.setOnAction(e->{
            //remove the shit
            try {
                new FriendRequestDao().sendFriendRequest(Session.userSession.getId(),super.getItem().getId() );
                btn.setText("Request Sent");
                btn.setDisable(true);
            }catch (Exception exception){

                ErrorHandler.generateError("Oop an unexpected error happened",()->{});
                exception.printStackTrace();
            }
        });
    }

    /**
     * This will set the value for the label and the image in the ListCell
     * @param item The user passed as Parameter
     * @param empty
     */
    @Override
    public void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null) {
            label.setText(item.toString());
            UtilityZ.setImage(new File("./src/res/"+item.getImageFile().getName()),this.img);
            img.setFitHeight(50);
            img.setFitWidth(50);
            try {
                if (new FriendRequestDao().areFriends(Session.userSession.getId(),super.getItem().getId())) {
                    btn.setText("Friends");
                    btn.setDisable(true);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            setGraphic(hbox);

        }
    }

}
