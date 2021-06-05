package error;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class ErrorHandler {
    public static Alert warningAlert;
    public static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public static Alert confimationAlert;





    public static void generateWarning(Node node, String warning, ErrorCallBack errorCallBack)
    {
        warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setContentText(warning);
        warningAlert.initModality(Modality.WINDOW_MODAL);
        warningAlert.initOwner(node.getScene().getWindow());
        warningAlert.show();
        warningAlert.setOnCloseRequest(event -> errorCallBack.executeErrorCallBack());
    }
    public static void generateError(String error,ErrorCallBack errorCallBack)
    {
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.initModality(Modality.WINDOW_MODAL);
        errorAlert.setTitle("Grave error");
        errorAlert.setContentText(error);
        errorAlert.show();
        if (errorCallBack==null) return;
        errorAlert.setOnCloseRequest(event -> errorCallBack.executeErrorCallBack());
    }

    public static void generateConfirmation(Node node,String teksti,ErrorCallBack errorCallBack)
    {
        confimationAlert= new Alert(Alert.AlertType.CONFIRMATION);
        confimationAlert.setTitle("Confirmation Dialog");
        confimationAlert.setHeaderText("Read carefully!!!");
        confimationAlert.setContentText(teksti);
//        confimationAlert.initModality(Modality.WINDOW_MODAL);
//        confimationAlert.initOwner(node.getScene().getWindow());

        Optional<ButtonType> result = confimationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            errorCallBack.executeErrorCallBack();
        } else {
            confimationAlert.close();
        }

    }
}
