package view.courses;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Course;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.*;

import zextra.Session;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class MainViewControllerTest {

    MainViewController mainViewController;
    Stage stage;
    Course course = new Course("Math",1,"6pm");


    public void setUp(Stage primaryStage)
    {
        Session.userSession = new User("Andi","Andi");
        Session.userSession.setId(1);
        Session.userSession.setNotification_on(true);
        Session.isInEnglish = false;
        course.setId(1);
        stage = primaryStage;
    }
    @Start
    public void start(Stage primaryStage) throws Exception{
        setUp(primaryStage);
        String viewName = "/view/courses/MainView.fxml";
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
        mainViewController.preloadData(course);

        primaryStage.setScene(scene1);
        primaryStage.show();
        primaryStage.toFront();

    }

    @Test void checkLoad()
    {
        assertEquals(mainViewController.courseNameLabel.getText(),course.getName());
        assertEquals(mainViewController.timeLabel.getText(),course.getTime());
        if(mainViewController.attendee.isEmpty())
            fail("From the test db the course should have some attendee");
    }


}