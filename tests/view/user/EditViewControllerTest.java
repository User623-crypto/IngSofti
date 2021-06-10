package view.user;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.*;
import zextra.Session;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)

class EditViewControllerTest {

    EditViewController editViewController;
    Stage  stage;


    public void setUp(Stage primaryStage)
    {
        Session.userSession = new User("Andi","Andi");
        Session.userSession.setId(1);
        Session.userSession.setNotification_on(true);
        Session.isInEnglish = false;
        stage = primaryStage;
    }
    @Start
    public void start(Stage primaryStage) throws Exception{
        setUp(primaryStage);
        String viewName = "/view/user/EditView.fxml";
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
        editViewController = loader.getController();
        editViewController.preloadData(Session.userSession);

        primaryStage.setScene(scene1);
        primaryStage.show();
        primaryStage.toFront();

    }

    @Test void checkLoad()
    {
        assertEquals(editViewController.nameField.getText(),Session.userSession.getName());
        assertEquals(editViewController.passwordField.getText(),Session.userSession.getPassword());
        assertEquals(editViewController.yesNotification.isSelected(),Session.userSession.isNotification_on());
        assertEquals(!editViewController.albBtn.isDisable(),Session.isInEnglish);


    }

    @Test
    public void updateWithBadInput(FxRobot robot)
    {
        robot.clickOn(editViewController.nameField);
        pushBackSpaceNTimes(robot,Session.userSession.getName().length());
        robot.write("");
        robot.clickOn(editViewController.passwordField);
        pushBackSpaceNTimes(robot,Session.userSession.getPassword().length());
        robot.write("");
        robot.clickOn(editViewController.updateBtn);


        if (editViewController.errorLabel.getText().isEmpty())fail("It should send an error when a field is empty");
    }

    public void pushBackSpaceNTimes(FxRobot robot,int times)
    {
        for (int i = 0; i < times; i++) {
            robot.push(KeyCode.BACK_SPACE);
        }
    }

    @Test
    void changeImage() {
    }

    @Test
    void toggleButton(FxRobot robot) {
        Button albBtn = robot.lookup("#albBtn").queryAs(Button.class);
        assertNotNull(albBtn);
        Button engBtn = robot.lookup("#engBtn").queryAs(Button.class);
        assertNotNull(engBtn);

        Label languageLabel = robot.lookup("#languageLabel").queryAs(Label.class);

        if (albBtn.isDisable())
        {
            assert(Session.isInEnglish == false);
            assertEquals(languageLabel.getText(),"Language");
        }

        robot.clickOn(engBtn);
        if (!engBtn.isDisable() || albBtn.isDisable()) fail("After it is clicked the button should be disabled");
        if (Session.isInEnglish != true) fail("When clicked in english Session should be in english");
        assertEquals(languageLabel.getText(),"Gjuha");
    }

    @Test
    void goBack(FxRobot robot) {
        robot.clickOn(editViewController.goBackBtn);
        if (!stage.getTitle().equals("MainView"))
            fail("It should return to mainPage");
    }
}