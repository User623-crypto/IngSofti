package zextra.components.jfx_list_component;

import error.ErrorHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Notification;
import model.dao.NotificationDao;

public class NotificationCell extends ListCell<Notification> {

    private HBox hbox = new HBox();
    private Button btn = new Button("Dismiss");
    private Pane pane = new Pane();
    private Label label = new Label("");

    public NotificationCell(){
        super();
        this.hbox.getChildren().addAll(label,pane,btn);
        this.hbox.setHgrow(pane, Priority.ALWAYS);
        btn.setOnAction(e->{
            try {
                new NotificationDao().dismissNotification(super.getItem());
                getListView().getItems().remove(getItem());

            }catch (Exception exception){
                ErrorHandler.generateError("Oops something went wrong",()->{});
            }
        });
    }

    @Override
    public void updateItem(Notification item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null) {
            label.setText(item.getNotification());

            setGraphic(hbox);
        }
    }
}
