package view.user;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.*;
import org.testfx.api.FxRobot;

import zextra.Session;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)

class ProfileViewControllerTest {
    ProfileViewController profileViewController;
    User userProfile;


    @Start
    public void start(Stage primaryStage) throws Exception{

        setUp();
        String viewName = "/view/user/ProfileView.fxml";
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginView.fxml")));
        //this.getClass().getResource("/view/LoginView.fxml"))

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
        profileViewController = loader.getController();
        profileViewController.preloadData(Session.userSession);

        primaryStage.setScene(scene1);
        primaryStage.show();
        primaryStage.toFront();

    }


    void setUp() {
        Session.userSession = new User("Andi","Andi");
        Session.userSession.setId(1);
        Session.userSession.setNotification_on(true);
        userProfile = Session.userSession;
    }

    @Test
    void  checkTabs(FxRobot robot)
    {
        robot.clickOn("#timeLineTab");
        if (!profileViewController.timeLineTab.isSelected()) fail("The timeLinetab should be selected");
        robot.clickOn("#courseTab");
        if (!profileViewController.courseTab.isSelected()) fail("The courseTab should be selected");

    }
    @Test void checkLoad()
    {
        assertEquals(profileViewController.nameLabel.getText(),userProfile.getName());
    }
}