package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.records.Record;

/**
 * An UI component that displays information of a {@code Record}.
 */
public class RecordCard extends UiPart<Region> {

    private static final String FXML = "RecordListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable recordIds cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Record record;

    @FXML
    private HBox cardPane;
    @FXML
    private Label recordId;
    @FXML
    private Label id;
    @FXML
    private Label timestamp;
    @FXML
    private Label type;
    @FXML
    private Label value;

    public RecordCard(Record record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        recordId.setText(String.valueOf(record.getRecordId()));
        timestamp.setText(record.getTimestamp().timestamp);
        type.setText(record.getType().type);
        value.setText(record.getValue().value + record.getType().unit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordCard)) {
            return false;
        }

        // state check
        RecordCard card = (RecordCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}
