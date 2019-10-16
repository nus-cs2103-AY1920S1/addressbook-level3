package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wordbank.WordBank;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class WordBankCard extends UiPart<Region> {

    private static final String FXML = "WordBankCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final WordBank wordBank;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    /**
     * Card containing the details of the word bank.
     *
     * @param wordBank The card representing its corresponding word bank.
     * @param displayedIndex The index of the word bank.
     */
    public WordBankCard(WordBank wordBank, int displayedIndex) {
        super(FXML);
        System.out.println("++++ 1 called");
        this.wordBank = wordBank;
        id.setText(displayedIndex + ". ");
        System.out.println(wordBank.getName());
        name.setText(wordBank.getName());
        System.out.println("++++ 2 called");
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
        WordBankCard wordBankCard = (WordBankCard) other;
        return false;
    }
}
