package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.FlashCard;

/**
 * An UI component that displays information of a {@code FlashCard}.
 */
public class FlashCardPanel extends UiPart<Region> {

    private static final String FXML = "FlashCardListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final FlashCard flashCard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private Label id;
    @FXML
    private Label answer;
    @FXML
    private FlowPane rating;
    @FXML
    private FlowPane categories;

    public FlashCardPanel(FlashCard flashCard, int displayedIndex) {
        super(FXML);
        this.flashCard = flashCard;
        id.setText(displayedIndex + ". ");
        question.setText(flashCard.getQuestion().fullQuestion);
        answer.setText("Answer: " + flashCard.getAnswer().fullAnswer);
        rating.getChildren().add(new Label(flashCard.getRating().value));
        flashCard.getCategories().stream()
                .sorted(Comparator.comparing(category -> category.categoryName))
                .forEach(category -> categories.getChildren().add(new Label(category.categoryName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlashCardPanel)) {
            return false;
        }

        // state check
        FlashCardPanel card = (FlashCardPanel) other;
        return id.getText().equals(card.id.getText())
                && flashCard.equals(card.flashCard);
    }
}
