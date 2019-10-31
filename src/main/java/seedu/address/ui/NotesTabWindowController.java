package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import seedu.address.model.note.Note;

/**
 * Controller class that handles what happens within the Notes Tab within the Activity Window.
 */
public class NotesTabWindowController {

    @FXML
    private TextArea titleTextArea;

    @FXML
    private TextArea contentTextArea;

    /**
     * Displays the question of the note specified in the note tab window.
     * @param note note to be displayed
     */
    public void loadNote(Note note) {
        titleTextArea.setText(note.getTitle().toString() + "\nTags:\n" + note.getTags().toString());
        contentTextArea.setText(note.getContent().toString());
    }
}
