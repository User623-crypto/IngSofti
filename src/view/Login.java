package view;

import com.jfoenix.controls.JFXButton;
import error.ErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import language.LanguageController;
import model.User;
import model.dao.UserDao;
import zextra.SceneChanger;
import zextra.Session;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    User user = null;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField nameField;
    @FXML
    Label errorLabel;
    SceneChanger sceneChanger = new SceneChanger();
    @FXML
    JFXButton albBtn;
    @FXML JFXButton engBtn;
    @FXML Label languageLabel;
    @FXML
    Button loginBtn;
    @FXML Button signUpBtn;

    LanguageController lang = new LanguageController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        albBtn.setDisable(true);
        Session.isInEnglish = false;
        languageLabel.setText(lang.LANGUAGE_TEXT);
        loginBtn.setText(lang.LOGIN_TEXT);
        signUpBtn.setText(lang.SIGNUP_TEXT);
        nameField.setPromptText(lang.USERNAME_TEXT);
        passwordField.setPromptText(lang.PASSWORD_TEXT);
    }

    /**
     *  This function checks the user credentials and logs the user in
     * @param event It is taken by default from the program to retrieve the parent node
     */
    public void loginButtonPushed(ActionEvent event)
    {
        try {
            user = new UserDao().readUserByName(nameField.getText());
            if (user != null)
            {
                if (passwordField.getText().equals(user.getPassword()))
                {
                    Session.userSession = user;
                    sceneChanger.changeScene(event,"/view/MainView.fxml","Main Page",Session.userSession);
                }else {
                    errorLabel.setText("Password is wrong");
                }
            }else errorLabel.setText("This user does not exist");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            ErrorHandler.generateWarning(passwordField, "There is an error with the database:; " + e.getMessage(), () -> {

            });
        }
    }

    public void signupButtonPushed(ActionEvent event){
        try {
            user = new User(nameField.getText(),passwordField.getText());

            new UserDao().insertIntoDB(user);
            Session.userSession = user;
            sceneChanger.changeScene(event,"/view/MainView.fxml","Main Page",Session.userSession);

        }catch (Exception e)
        {
            errorLabel.setText(e.getMessage());
        }
    }

    public void toggleButton()
    {
        if (Session.isInEnglish)
        {
            albBtn.setDisable(true);
            Session.isInEnglish=false;
            engBtn.setDisable(false);
        }else {
            albBtn.setDisable(false);
            Session.isInEnglish=true;
            engBtn.setDisable(true);
        }
        lang = new LanguageController();
        languageLabel.setText(lang.LANGUAGE_TEXT);
        loginBtn.setText(lang.LOGIN_TEXT);
        signUpBtn.setText(lang.SIGNUP_TEXT);
        nameField.setPromptText(lang.USERNAME_TEXT);
        passwordField.setPromptText(lang.PASSWORD_TEXT);
    }
}
