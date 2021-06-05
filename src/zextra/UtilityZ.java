package zextra;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UtilityZ {

    /**
     * This is a helper function that sets the image to the FXML image component
     * @param imageFile this is the image File
     * @param imagePlaceholder the FXML image object
     */
    public static void setImage(File imageFile, ImageView imagePlaceholder)
    {
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage,null);
            imagePlaceholder.setImage(image);

        } catch (IOException e) {
            //Logg into the system
            System.out.println("The file is not found this should not happen"+e.getMessage());
        }
    }
}
