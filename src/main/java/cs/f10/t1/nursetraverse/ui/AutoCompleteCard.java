//@@author francislow

package cs.f10.t1.nursetraverse.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code AutoCompleteWord}.
 */
public class AutoCompleteCard extends UiPart<Region> {

    private static final String FXML = "AutoCompleteListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/patientBook-level4/issues/336">The issue on PatientBook level 4</a>
     */

    public final String wordShown;

    @FXML
    private Label suggestedWord;

    public AutoCompleteCard(String wordShown) {
        super(FXML);
        this.wordShown = wordShown;
        suggestedWord.setText(wordShown);
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
        return wordShown.equals(card.wordShown);
    }
}
