package tagline.ui.note;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import tagline.model.note.Note;
import tagline.ui.UiPart;

/**
 * An UI component that displays information of a {@code Note}.
 */
public class NoteListCard extends UiPart<Region> {

    public static final String UNTITLED_NOTE_STRING = "Untitled Note";
    private static final String FXML = "NoteListCard.fxml";

    public final Note note;

    @FXML
    private VBox card;
    @FXML
    private HBox noteListCardPane;
    @FXML
    private HBox tagBox;
    @FXML
    private FlowPane tagsContainer;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label content;
    @FXML
    private Label tags;

    public NoteListCard(Note note) {
        super(FXML);
        this.note = note;
        id.setText("#" + note.getNoteId().value);

        if (note.getTitle().titleDescription.isEmpty()) {
            title.setText(UNTITLED_NOTE_STRING);
        } else {
            title.setText(note.getTitle().titleDescription);
        }

        time.setText(note.getTimeCreated().getTime().toString());
        content.setText(note.getContent().value);

        if (note.getTags().size() == 0) {
            card.getChildren().remove(tagBox);
        } else {
            note.getTags().stream().map(tag -> new Label(tag.toString()))
                .forEach(tagLabel -> tagsContainer.getChildren().add(tagLabel));
        }
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
