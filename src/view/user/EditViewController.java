package view.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import error.ErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import language.LanguageController;
import model.User;
import model.dao.UserDao;
import zextra.ControllerClass;
import zextra.SceneChanger;
import zextra.Session;
import zextra.UtilityZ;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditViewController implements Initializable, ControllerClass {

    public LanguageController lang = new LanguageController();

    @FXML
    JFXButton albBtn;
    @FXML JFXButton engBtn;
    @FXML Label languageLabel;

    @FXML public
    Label name;
    @FXML public Label errorLabel;
    @FXML public
    TextField nameField;
    @FXML public
    PasswordField passwordField;
    @FXML public
    JFXRadioButton noNotification;
    @FXML public JFXRadioButton yesNotification;
    @FXML public ImageView image;
    public User user;
    public SceneChanger sceneChanger = new SceneChanger();

    public ToggleGroup notificationGroup = new ToggleGroup();

    @FXML
    Button goBackBtn;
    @FXML
    Button updateBtn;

    @FXML Label nameLabel;
    @FXML Label passwordLabel;
    @FXML Label notificationStatusLabel;
    @FXML Button changeImgBtn;

    public boolean didChangeImage = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = Session.userSession.copyUser();
        noNotification.setToggleGroup(notificationGroup);
        yesNotification.setToggleGroup(notificationGroup);

        if (Session.isInEnglish)
        {
            engBtn.setDisable(true);
        }else {
            albBtn.setDisable(true);
        }

        languageLabel.setText(lang.LANGUAGE_TEXT);
        nameLabel.setText(lang.USERNAME_TEXT);
        passwordLabel.setText(lang.PASSWORD_TEXT);
        notificationStatusLabel.setText(lang.NOTIFICATION_STATUS_TEXT);
        yesNotification.setText(lang.YES_TEXT);
        noNotification.setText(lang.NO_TEXT);
        goBackBtn.setText(lang.GO_BACK_TEXT);
        updateBtn.setText(lang.UPDATE_TEXT);
        changeImgBtn.setText(lang.CHANGE_IMAGE_TEXT);
    }

    @Override
    public void preloadData(Object object) {
            name.setText(user.getName());
            nameField.setText(user.getName());
            passwordField.setText(user.getPassword());
            UtilityZ.setImage(new File("./src/res/"+user.getImageFile().getName()),image);
            if (user.isNotification_on()) yesNotification.setSelected(true);
            else noNotification.setSelected(true);
            errorLabel.setText("");
    }

    public void updateButtonPushed()
    {
        try{
            user.setName(nameField.getText());
            user.setPassword(passwordField.getText());
            user.setNotification_on(yesNotification.isSelected());

            if (!Session.userSession.getName().equals(user.getName())) {
                boolean doesExist = new UserDao().doesExist(user.getName());
                if (doesExist) throw new Exception("The user Exist");
            }
            if (didChangeImage) {
                user.saveImageFileLocally();
            }
            new UserDao().updateFromDb(user);
            Session.userSession = user;
            ErrorHandler.generateConfirmation(name,"Updated",null);
            errorLabel.setText("");

        }catch (Exception e){
            errorLabel.setText(e.getMessage());
        }
    }
    public void goBack(ActionEvent event){
        try {
            sceneChanger.changeScene(event,"/view/MainView.fxml","MainView",Session.userSession);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            ErrorHandler.generateError(e.getMessage(),()->{

            });
        }
    }

    /**
     * This method will change the image of the user when clicked
     * @param event Stage
     */
    public void changeImage(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)","*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)","*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter,pngFilter);

        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        if (!userDirectory.canRead())
            userDirectory = new File(System.getProperty("user.home"));

        fileChooser.setInitialDirectory(userDirectory);

        File imageFile = fileChooser.showOpenDialog(stage);

        if (imageFile !=null){
            if (imageFile.isFile())
            {
                try {
                    UtilityZ.setImage(imageFile,image);
                    user.setImageFile(imageFile);
                    didChangeImage = true;
                }catch (Exception e)
                {
                    ErrorHandler.generateError("Can not set the image"+e.getMessage(),()->{});
                }
            }
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
        nameLabel.setText(lang.USERNAME_TEXT);
        passwordLabel.setText(lang.PASSWORD_TEXT);
        notificationStatusLabel.setText(lang.NOTIFICATION_STATUS_TEXT);
        yesNotification.setText(lang.YES_TEXT);
        noNotification.setText(lang.NO_TEXT);
        goBackBtn.setText(lang.GO_BACK_TEXT);
        updateBtn.setText(lang.UPDATE_TEXT);
        changeImgBtn.setText(lang.CHANGE_IMAGE_TEXT);
    }

}
