package seedu.address.ui.panels;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.note.Note;

/**
 * An UI component that displays information of a {@code Note}.
 */
public class NoteListCard extends PanelComponent<Region> {
    private static final String FXML = "NoteListCard.fxml";

    public final Note note;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label content;
    @FXML
    private ImageView image;

    private final double nativeImageWidth;

    public NoteListCard(Note note, int displayedIndex) {
        super(FXML);
        this.note = note;
        id.setText(displayedIndex + ". ");
        title.setText(note.getTitle().title);
        content.setText(note.getContent().content);
        image.setImage(note.getImage());
        nativeImageWidth = note.getImageUrl().equals("none") ? Double.MAX_VALUE : note.getImage().getWidth();
        cardPane.widthProperty().addListener((obs, oldValue, newValue) -> {
            image.setFitWidth(Math.min(newValue.doubleValue() - 20, nativeImageWidth));
        });
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
        return id.getText().equals(card.id.getText())
                && note.equals(card.note);
    }
}
