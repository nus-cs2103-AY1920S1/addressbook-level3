package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.note.Note;

/**
 * An UI component that displays information of a {@code Note}.
 */
public class NotesCard extends UiPart<Region> {

    private static final String FXML = "NotesListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Note note;

    @FXML
    private HBox cardPane;
    @FXML
    private Label noteTitle;
    @FXML
    private Label description;

    public NotesCard(Note note, int displayedIndex) {
        super(FXML);
        this.note = note;
        noteTitle.setText(note.getNote());
        description.setText(note.getDescription());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotesCard)) {
            return false;
        }

        // state check
        NotesCard card = (NotesCard) other;
        return noteTitle.getText().equals(card.noteTitle.getText())
                && note.equals(card.note);
    }
}
