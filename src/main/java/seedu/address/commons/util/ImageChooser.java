package seedu.address.commons.util;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Utility class for opening the OS file gui to choose an image file.
 * A new {@link Stage} is used to back the OS file gui chooser.
 */
public class ImageChooser {

    private final Stage fileChooserStage;
    private final FileChooser fileChooser;

    public ImageChooser() {
        fileChooserStage = new Stage();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
    }

    /**
     * Shows the OS file gui, waiting for the user to select an image file.
     *
     * @return The image {@link File} selected.
     */
    public File showDialog() {
        File f = fileChooser.showOpenDialog(fileChooserStage);
        fileChooserStage.close();

        return f;
    }
}

