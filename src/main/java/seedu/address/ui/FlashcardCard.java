package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.Flashcard;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class FlashcardCard extends UiPart<Region> {

    private static final String FXML = "FlashcardCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Flashcard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerLabel;

    @FXML
    private FlowPane tags;

    public FlashcardCard(Flashcard flashcard) {
        super(FXML);
        this.flashcard = flashcard;
        this.questionLabel.setText(flashcard.getQuestion().toString());
        this.answerLabel.setText(flashcard.getAnswer().toString());
        //        person.getTags().stream()
        //                .sorted(Comparator.comparing(tag -> tag.tagName))
        //                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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

        // <------------------------------TO BE CHANGED------------------------------>
        return false;
    }
}
