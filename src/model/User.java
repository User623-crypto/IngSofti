package model;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import language.LanguageController;
import model.dao.UserDao;
import zextra.SceneChanger;
import zextra.Session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class User {
    private int id;
    private String name;
    private String password;
    private boolean notification_on = true;
    private File imageFile;

    LanguageController lang = new LanguageController();
    /**
     * User constructor that will initialize a user object
     * @param name The user name
     * @param password The user password
     * @throws IllegalArgumentException It throws exception if the name or password is too long or too short
     */
    public User(String name, String password) throws IllegalArgumentException{
        setName(name);
        setPassword(password);
        setImageFile(new File("./src/res/default.png"));
    }

    /**
     *
     * @return This will return the copy of the user
     */
    public User copyUser()
    {
        User newUser = new User(this.getName(),this.getPassword());
        newUser.setId(this.getId());
        newUser.setImageFile(this.getImageFile());
        newUser.setNotification_on(this.isNotification_on());
        return newUser;
    }
    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }



    /**
     * This function will save the image that the users upload into the local directory
     * @throws IOException Throws exception if there are problems in writing the image file
     */
    public void saveImageFileLocally() throws IOException {
        Path sourcePath = imageFile.toPath();
        String fileName = imageFile.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        String newFileName = Session.userSession.getName() + "." + fileExtension;

        Path targetPath = Paths.get("./src/res/"+ newFileName);
        Files.copy(sourcePath,targetPath, StandardCopyOption.REPLACE_EXISTING);

        imageFile = new File(targetPath.toString());
    }




    public String getName() {
        return name;
    }


    public void setName(String name) {
        if (name.length()>45)
            throw new IllegalArgumentException(lang.USERNAME_LENGTH_ERROR);
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length()>45 || password.length()<2)
            throw new IllegalArgumentException(lang.PASSWORD_LENGTH_ERROR_TEXT);
        this.password = password;
    }

    public boolean isNotification_on() {

        return notification_on;
    }

    public void setNotification_on(boolean notification_on) {
        this.notification_on = notification_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * This function initialize a TableView with  the users from the db
     * @param friendsTable The table that will be initialized
     * @param nameCol The column that will hold the names
     * @param friends the list that will interact with the table
     */

    public static void initFriendsTable(TableView<User> friendsTable, TableColumn<User,String> nameCol,
                                        ObservableList<User> friends)
    {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        friendsTable.setOnMousePressed(new EventHandler<MouseEvent>() {

            /**To be generated*/
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    User a = friendsTable.getSelectionModel().getSelectedItem();
                    if (a == null) return;
                    try{
                        new SceneChanger().createStage("/view/user/ProfileView.fxml","Course",a, friendsTable);

                    }catch (Exception ignore){
                        ignore.printStackTrace();
                    }
                }
            }
        });
        friendsTable.setItems(friends);
        try {
            friends.addAll(new UserDao().readAllUserFriends(Session.userSession.getId()));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
