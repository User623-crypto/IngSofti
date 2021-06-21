package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import zdatabase.DatabaseManager;

import java.io.File;
import java.sql.Connection;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(true);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        User a = new User("Luka","Laka");
        try{
            a.setImageFile(new File("./src/res/yugi.png"));
        }catch (Exception e){ System.out.println(e.getMessage());}

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
}
