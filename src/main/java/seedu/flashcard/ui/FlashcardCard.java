package seedu.flashcard.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.flashcard.model.flashcard.Flashcard;

import java.util.Comparator;

public class FlashcardCard extends UiPart<Region> {

    // TODO: make it points to the right fxml file.
    private static final String FXML = "FlashcardCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Flashcard flashcard;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label word;
    @FXML
    private Label id;
    @FXML
    private Label definition;
    @FXML
    private FlowPane tags;

    public FlashcardCard(Flashcard flashcard, int displayedIndex) {
        super(FXML);
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        word.setText(flashcard.getWord().word);
        definition.setText(flashcard.getDefinition().definition);
        flashcard.getTags().stream()
                   .sorted(Comparator.comparing(tag -> tag.tagName))
                   .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlashcardCard)) {
            return false;
        }

        // state check
        FlashcardCard card = (FlashcardCard) other;
        return id.getText().equals(card.id.getText())
                && flashcard.equals(card.flashcard);
    }
}
