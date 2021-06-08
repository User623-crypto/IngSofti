package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.*;
import org.testfx.api.FxRobot;
import sample.Main;
import zextra.SceneChanger;
import zextra.Session;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class LoginTest {

    @Start
    public void start(Stage primaryStage) throws Exception{
        Parent mainNode = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
                //this.getClass().getResource("/view/LoginView.fxml"))

        primaryStage.setTitle("Login");
        primaryStage.setResizable(true);
        Scene scene = new Scene(mainNode);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();

    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void initialize() {
    }

    @Test
    void loginButtonPushed(FxRobot robot) {

        robot.clickOn("#nameField");
        robot.write("Andi");
        robot.clickOn("#passwordField");
        robot.write("");
        robot.clickOn("#loginBtn");

        Label label = robot.lookup("#errorLabel").queryAs(Label.class);

        if (label.getText().isEmpty())fail("It should send an error when a field is empty");


    }

    @Test
    void signupButtonPushed(FxRobot robot) {

        robot.clickOn("#nameField");
        robot.write("Andi");
        robot.clickOn("#passwordField");
        robot.write("");
        robot.clickOn("#signUpBtn");

        Label label = robot.lookup("#errorLabel").queryAs(Label.class);

        if (label.getText().isEmpty())fail("It should send an error when a field is empty");
    }

    @Test
    void changeLanguageToggle(FxRobot robot)
    {
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
}