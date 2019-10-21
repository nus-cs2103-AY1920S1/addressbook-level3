package seedu.address.ui;

import java.awt.TextField;

import javafx.fxml.FXML;
import seedu.address.model.note.Note;

/**
 * Controller class that handles what happens within the Notes Tab within the Activity Window.
 */
public class NotesTabWindowController {

    @FXML
    private TextField noteCardTitleTextField;

    @FXML
    private TextField noteCardContentTextField;

    public void displayNoteCard(Note note) {
        noteCardTitleTextField.setText(note.getTitle().toString());
        noteCardContentTextField.setText(note.getContent().toString());
    }
}
