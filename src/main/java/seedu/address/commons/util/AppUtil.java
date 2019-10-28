package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {
    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Brings up a file chooser dialog for the user to select an image (.png, .jpg, .jpeg, .gif).
     * Returns the image chosen.
     *
     * @return the image chosen by the user, which is null iff the dialog was closed without choosing
     */
    public static Image selectImage() {
        FileChooser dialogue = new FileChooser();
        dialogue.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files",
                "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = dialogue.showOpenDialog(null);
        return file != null ? new Image("file:" + file) : null;
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
