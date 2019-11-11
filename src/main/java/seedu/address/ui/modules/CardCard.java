package seedu.address.ui.modules;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class CardCard extends UiPart<Region> {

    private static final String FXML = "CardCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Card card;

    @FXML
    private HBox cardPane;
    @FXML
    private Label word;
    @FXML
    private Label id;
    @FXML
    private Label meaning;
    @FXML
    private FlowPane tags;

    public CardCard(Card card, int displayedIndex) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        word.setText(card.getWord().getValue());
        meaning.setText(card.getMeaning().toString());

        card.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.getTagName()))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getTagName())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CardCard)) {
            return false;
        }

        // state check
        CardCard card = (CardCard) other;
        return id.getText().equals(card.id.getText())
                && this.card.equals(card.card);
    }
}
