package dukecooks.commons.util;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Utility class to open an ImagePicker for users to select images
 */
public class ImagePicker {

    private final Stage imagePickerStage;
    private final FileChooser imagePicker;

    public ImagePicker() {
        imagePickerStage = new Stage();
        imagePicker = new FileChooser();
        imagePicker.setTitle("Select an image");
        imagePicker.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
    }

    /**
     * Opens the imagePicker and allows user to select a file.
     * @return The image {@link File} selected.
     */
    public File getImageFile() {
        File file = imagePicker.showOpenDialog(imagePickerStage);
        imagePickerStage.close();
        return file;
    }
}
