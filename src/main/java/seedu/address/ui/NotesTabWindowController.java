package seedu.address.ui;

import java.awt.TextField;

import javafx.fxml.FXML;
import seedu.address.model.note.Note;

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
