package seedu.address.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * An UI component that displays information of a {@code SpendLogEntry}.
 */
public class SpendLogEntryCard extends UiPart<Region> {

    private static final String FXML = "SpendCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final SpendLogEntry logEntry;

    @FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label tDate;
    @FXML
    private Label description;
    @FXML
    private Label tMethod;
    @FXML
    private FlowPane cats;
    @FXML
    private Label place;

    public SpendLogEntryCard(SpendLogEntry logEntry, int displayedIndex) {
        super(FXML);
        this.logEntry = logEntry;
        id.setText(displayedIndex + ". ");
        amount.setText(logEntry.getAmount().amount);
        tDate.setText(logEntry.getTransactionDate().value);
        description.setText(logEntry.getDescription().value);
        tMethod.setText(logEntry.getTransactionMethod().value);
        logEntry.getCategories().stream()
                .forEach(cat -> cats.getChildren().add(new Label(cat.catName)));
        place.setText(logEntry.getPlace().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SpendLogEntryCard)) {
            return false;
        }

        // state check
        SpendLogEntryCard card = (SpendLogEntryCard) other;
        return id.getText().equals(card.id.getText())
                && logEntry.equals(card.logEntry);
    }
}
