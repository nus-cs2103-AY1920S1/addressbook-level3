package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.ReminderStub;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    public final ReminderStub reminder;

    @FXML
    private HBox reminderCardPane;
    @FXML
    private Label title;
    @FXML
    private Label date;


    public ReminderCard(ReminderStub reminder) {
        super(FXML);
        this.reminder = reminder;
        title.setText(reminder.getDescription());
        date.setText(reminder.getDaysString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return reminder.equals(card.reminder);
    }
}
