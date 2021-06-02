package zextra;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.Connection;

public class SceneChanger {
    private Connection connection = null;
    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
    private void closeConnection()  {if (connection != null) try{connection.close();}
    catch (Exception ignored){}
    }
    public void changeStage(ActionEvent event, String viewName, String Title) throws IOException
    {
        closeConnection();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));

        Parent parent=loader.load();

        Scene scene=new Scene(parent);

        Stage stage= new Stage();


        stage.setTitle(Title);
        stage.setScene(scene);
        stage.show();

    }

    public void changeScene(ActionEvent event,String viewName,String Title) throws IOException
    {
        closeConnection();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));

        Parent parent=loader.load();

        Scene scene=new Scene(parent);

        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();


        stage.setTitle(Title);
        stage.setScene(scene);
        stage.show();

    }

    public void changeScene(Node node,String viewName,String Title) throws IOException
    {
        closeConnection();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));

        Parent parent=loader.load();

        Scene scene=new Scene(parent);

        Stage stage= (Stage)(node).getScene().getWindow();


        stage.setTitle(Title);
        stage.setScene(scene);
        stage.show();

    }

    public void changeScene(ActionEvent event, String viewName, String Title, User user, ControllerClass  controllerClass) throws IOException
    {
        closeConnection();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));

        Parent parent=loader.load();

        Scene scene=new Scene(parent);
        controllerClass=loader.getController();
        controllerClass.preloadData(user);

        Stage stage= (Stage)((Node)event.getSource()).getScene().getWindow();


        stage.setTitle(Title);
        stage.setScene(scene);
        stage.show();
    }

    public void createStage(ActionEvent event, String viewName, String Title,
                            Object objekt, ControllerClass controllerClass, Node button) throws IOException {

        if(objekt == null)
            return;
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        controllerClass = loader.getController();

        controllerClass.preloadData(objekt);


//        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage currentStage = (Stage)button.getScene().getWindow();
        newStage.setTitle(Title);

        newStage.setScene(scene);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(currentStage);
        newStage.show();
        newStage.setResizable(false);

//        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                closeConnection();
//            }
//        });



    }

    public void createStageNoModality(ActionEvent event, String viewName, String Title) throws IOException {

        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        newStage.setTitle(Title);

        newStage.setScene(scene);
        newStage.initModality(Modality.NONE);
        newStage.show();

//        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                closeConnection();
//            }
//        });



    }
}
