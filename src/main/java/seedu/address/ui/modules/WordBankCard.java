package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class WordBankCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;

    /**
     * Card containing the details of the word bank.
     *
     * @param card The card representing its corresponding word bank.
     * @param displayedIndex The index of the word bank.
     */
    public WordBankCard(Card card, int displayedIndex) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        name.setText(card.getWord().value);
        description.setText(card.getMeaning().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WordBankCard)) {
            return false;
        }

        // state check
        WordBankCard card = (WordBankCard) other;
        return id.getText().equals(card.id.getText())
                && this.card.equals(card.card);
    }
}
