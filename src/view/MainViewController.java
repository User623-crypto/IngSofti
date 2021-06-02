package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import error.ErrorCallBack;
import error.ErrorHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import zextra.ControllerClass;
import zextra.SceneChanger;
import zextra.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, ControllerClass {
    @FXML
    JFXListView notificationList;
    ObservableList<String> listView = FXCollections.observableArrayList("test","testing","test2");
    @FXML
    JFXButton logoutButton;
    static class Cells extends ListCell<String>{
        HBox hbox = new HBox();
        Button btn = new Button("Testing");
        Pane pane = new Pane();
        Label label = new Label("");
        public Cells(){
            super();
            hbox.getChildren().addAll(label,pane,btn);
            hbox.setHgrow(pane, Priority.ALWAYS);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);
            if (item != null) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationList.setItems(listView);
        notificationList.setCellFactory(param -> new Cells());
    }

    @Override
    public void preloadData(Object object) {



    }

    public void LogoutButtonPushed(ActionEvent event) {
        Session.userSession = null;

        try {
            new SceneChanger().changeScene(event,"/view/LoginView.fxml","LoginView");
        } catch (Exception e) {
            ErrorHandler.generateError(e.getMessage(),()->{

            });
        }
    }
}
