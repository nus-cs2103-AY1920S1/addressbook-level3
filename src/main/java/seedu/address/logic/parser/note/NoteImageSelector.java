package seedu.address.logic.parser.note;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * Contains the method used by Add/EditCommandParser to get an image from the file system
 * to put as a lecture note's associated image in NUStudy.
 */
interface NoteImageSelector {
    /**
     * Brings up a file chooser dialog for the user to select an image (.png, .jpg, .jpeg, .gif).
     * Returns the image chosen and copies it into this application's data directory to guard against
     * deletion of the image at source. If such copying is not possible, use the source's path as fallback.
     *
     * @return the image chosen by the user, which is null iff the dialog was closed without choosing
     */
    static Image selectImage() {
        FileChooser dialogue = new FileChooser();
        dialogue.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files",
                "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = dialogue.showOpenDialog(null);
        if (file != null) {
            Path sourcePath = Paths.get(file.getAbsolutePath());
            Path destPath = Paths.get("data", file.getName());
            // TODO move the file copy to a higher level, where the user preferences can be taken into account
            try {
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                return new Image("file:" + destPath);
            } catch (IOException e) {
                return new Image(file.getAbsolutePath());
            }
        }
        return null;
    }
}
