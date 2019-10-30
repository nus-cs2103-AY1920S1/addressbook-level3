package seedu.flashcard.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * The command feed back to the user. Just that window below the text box.
 */
public class FlashcardDisplay extends UiPart<Region> {

    private static final String FXML = "FlashcardDisplay.fxml";

    @FXML
    private TextArea flashcardDisplay;

    public FlashcardDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        flashcardDisplay.setText(feedbackToUser);
    }
}
