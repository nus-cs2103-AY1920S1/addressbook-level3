package dukecooks.ui;

import dukecooks.model.health.components.Record;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Record}.
 */
public class RecordListCard extends UiPart<Region> {

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
    private Label id;
    @FXML
    private Label type;
    @FXML
    private Label timestamp;
    @FXML
    private Label value;
    @FXML
    private FlowPane remarkPages;

    public RecordListCard(Record record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        type.setText(record.getType().name());
        timestamp.setText(record.getTimestamp().toString());
        value.setText(record.getValue().value + record.getType().getUnit());

        record.getRemarks().stream()
                .forEach(remark -> remarkPages.getChildren().add(new Label(remark.remarkName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordListCard)) {
            return false;
        }

        // state check
        RecordListCard card = (RecordListCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}
