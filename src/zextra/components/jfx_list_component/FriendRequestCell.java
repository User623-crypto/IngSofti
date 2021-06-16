package zextra.components.jfx_list_component;

import error.ErrorHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import language.LanguageController;
import model.FriendRequest;
import model.Notification;
import model.dao.FriendRequestDao;
import model.dao.NotificationDao;

public class FriendRequestCell extends ListCell<FriendRequest> {

    public LanguageController lang = new LanguageController();

    private HBox hbox = new HBox();
    private Button acceptButton = new Button(lang.ACCEPT_TEXT);
    private Button rejectButton = new Button(lang.REJECT_TEXT);
    private Pane pane = new Pane();
    private Label label = new Label("");

    public FriendRequestCell(){
        super();
        this.hbox.getChildren().addAll(label,pane,acceptButton,rejectButton);
        this.hbox.setHgrow(pane, Priority.ALWAYS);
        hbox.setSpacing(5);
        acceptButton.setOnAction(e->{
            try {
                new FriendRequestDao().acceptFriendRequest(super.getItem());
                getListView().getItems().remove(getItem());
                ErrorHandler.generateConfirmation(getListView(),"Successes",()->{});

            }catch (Exception exception){
                ErrorHandler.generateError("Oops something went wrong",()->{});
            }
        });

        rejectButton.setOnAction(e->{
            try {
                new FriendRequestDao().rejectFriendRequest(super.getItem());
                getListView().getItems().remove(getItem());
                ErrorHandler.generateConfirmation(getListView(),"Rejected Nice Job!",()->{});

            }catch (Exception exception){
                ErrorHandler.generateError("Oops something went wrong",()->{});
                exception.printStackTrace();

            }
        });
    }

    @Override
    public void updateItem(FriendRequest item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null) {
            label.setText(item.getSenderUser().getName());

            setGraphic(hbox);
        }
    }
}
