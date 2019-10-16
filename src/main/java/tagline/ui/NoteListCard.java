package tagline.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tagline.model.note.Note;

/**
 * An UI component that displays information of a {@code Note}.
 */
public class NoteListCard extends UiPart<Region> {

    private static final String FXML = "NoteListCard.fxml";
    public final Note note;

    @FXML
    private HBox noteListCardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label content;

    public NoteListCard(Note note) {
        super(FXML);
        this.note = note;
        id.setText("#" + note.getNoteId().value);

        if (note.getTitle().titleDescription.isEmpty()) {
            title.setText("Untitled Note");
        } else {
            title.setText(note.getTitle().titleDescription);
        }

        time.setText(note.getTimeCreated().getTime().toString());
        content.setText(note.getContent().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteListCard)) {
            return false;
        }

        // state check
        NoteListCard card = (NoteListCard) other;
        return note.equals(card.note);
    }
}
