package seedu.guilttrip.ui.reminder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.reminders.EntryReminder;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "reminder/ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final Reminder reminder;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;

    private Reminder.Status status;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        String descWithStatus = getReminderType() + "  " + reminder.getHeader().fullDesc;
        desc.setText(descWithStatus);
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
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }

    private String getReminderType() {
        if (reminder instanceof GeneralReminder) {
            return "General Reminder";
        } else {
            EntryReminder entryReminder = (EntryReminder) reminder;
            return "Entry Reminder";
        }
    }

}
