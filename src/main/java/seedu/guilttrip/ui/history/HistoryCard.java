package seedu.guilttrip.ui.history;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class HistoryCard extends UiPart<Region> {

    private static final String FXML = "history/HistoryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final String history;

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;

    public HistoryCard(String history, int displayedIndex) {
        super(FXML);
        this.history = history;
        id.setText(displayedIndex + ". ");

        desc.setText(history);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryCard)) {
            return false;
        }

        // state check
        HistoryCard card = (HistoryCard) other;
        return id.getText().equals(card.id.getText())
                && history.equals(card.history);
    }
}
