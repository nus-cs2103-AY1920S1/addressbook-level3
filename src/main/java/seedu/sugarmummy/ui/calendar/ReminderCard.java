package seedu.sugarmummy.ui.calendar;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.time.DateTime;
import seedu.sugarmummy.ui.UiPart;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @FXML
    private VBox reminderPane;

    @FXML
    private Label dateTime;

    @FXML
    private Label description;

    public ReminderCard(Reminder reminder) {
        super(FXML);
        this.reminder = reminder;
        dateTime.setText(new DateTime(LocalDate.now(), reminder.getTime()).toString());
        description.setText(reminder.getDescription().value);
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
        ReminderCard reminderCard = (ReminderCard) other;
        return reminder.equals(reminderCard.reminder);
    }
}
