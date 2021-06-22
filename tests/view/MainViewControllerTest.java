package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Course;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.*;
import org.testfx.api.FxRobot;

import zextra.Session;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class MainViewControllerTest {
    MainViewController mainViewController;
    Stage stage;
    User user;


    public void setUp(Stage primaryStage)
    {
        Session.userSession = new User("Andi","Andi");
        Session.userSession.setId(1);
        Session.userSession.setNotification_on(true);
        Session.isInEnglish = false;
        user = Session.userSession;
        stage = primaryStage;
    }
    @Start
    public void start(Stage primaryStage) throws Exception{
        setUp(primaryStage);
        String viewName = "/view/MainView.fxml";
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginView.fxml")));

        primaryStage.setTitle("Login");
        primaryStage.setResizable(true);
        Scene scene = new Scene(mainNode);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));

        Parent parent=loader.load();

        Scene scene1=new Scene(parent);
        mainViewController = loader.getController();
        mainViewController.preloadData(user);

        primaryStage.setScene(scene1);
        primaryStage.show();
        primaryStage.toFront();

    }

    @Test void didLoadCorrectly()
    {
        assertEquals(mainViewController.nameLabel.getText(),user.getName());
        if (!user.isNotification_on()){
            if (!mainViewController.notificationListView.isEmpty()) fail("There should be no notification");
        }
    }

    @Test
    void logoutButtonPushed(FxRobot robot) {
        robot.clickOn(mainViewController.logoutButton);
        if (!stage.getTitle().equals("LoginView")) fail("The logout button does not work correctly");
    }

    @Test
    void settingsButtonPushed(FxRobot robot) {
        robot.clickOn(mainViewController.settingsButton);
        if (!stage.getTitle().equals("Configurations") && !stage.getTitle().equals("Konfigurimet")) fail("The settings button does not work correctly");
    }



}