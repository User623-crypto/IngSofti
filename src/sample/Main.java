package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import zdatabase.DatabaseManager;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/LoginView.fxml")));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(true);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            try {
                Connection connection = DatabaseManager.getConnection();
                if (connection!=null)
                    connection.close();
            }catch (Exception ignored){
            }
            Platform.exit();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void exitApplication() {

        try {
            Connection connection = DatabaseManager.getConnection();
            if (connection!=null)
                connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Platform.exit();
    }
}
