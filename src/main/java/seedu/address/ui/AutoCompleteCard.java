package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.autocomplete.AutoCompleteWord;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AutoCompleteCard extends UiPart<Region> {

    private static final String FXML = "AutoCompleteListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final AutoCompleteWord acWord;

    @FXML
    private Label suggestedWord;

    public AutoCompleteCard(AutoCompleteWord acWord) {
        super(FXML);
        this.acWord = acWord;
        suggestedWord.setText(acWord.getSuggestedWord());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AutoCompleteCard)) {
            return false;
        }

        // state check
        AutoCompleteCard card = (AutoCompleteCard) other;
        return acWord.equals(card.acWord);
    }
}
