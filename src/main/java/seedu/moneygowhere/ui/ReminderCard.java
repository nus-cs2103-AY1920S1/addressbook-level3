package seedu.moneygowhere.ui;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.moneygowhere.model.reminder.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private Label message;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        deadline.setText(Reminder.getReminderDueDateDescription(this.reminder));

        switch (this.reminder.getType()) {
        case DEADLINED_TODAY:
            deadline.setTextFill(Color.ORANGE);
            break;
        case DEADLINED_TOMORROW:
            deadline.setTextFill(Color.valueOf("#7B9918"));
            break;
        case OVERDUE:
            deadline.setTextFill(Color.RED);
            break;
        default:
            break;
        }
        message.setText(reminder.getReminderMessage().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReminderCard)) {
            return false;
        }

        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}
