package view;

import error.ErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
                    sceneChanger.changeScene(event,"/view/MainView.fxml","Main Page");
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
            sceneChanger.changeScene(event,"/view/MainView.fxml","Main Page");

        }catch (Exception e)
        {
            errorLabel.setText(e.getMessage());
        }
    }
}
