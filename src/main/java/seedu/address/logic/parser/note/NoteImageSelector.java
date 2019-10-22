package seedu.address.logic.parser.note;

import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * Contains the method used by Add/EditCommandParser to get an image from the file system
 * to put as a lecture note's associated image in NUStudy.
 */
interface NoteImageSelector {
    /**
     * Brings up a file chooser dialog for the user to select an image (.png, .jpg, .jpeg, .gif).
     * Returns the image chosen.
     *
     * @return the image chosen by the user, which is null iff the dialog was closed without choosing
     */
    static Image selectImage() {
        FileChooser dialogue = new FileChooser();
        dialogue.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files",
                "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = dialogue.showOpenDialog(null);
        return file != null ? new Image("file:" + file.getAbsolutePath()) : null;
    }
}
