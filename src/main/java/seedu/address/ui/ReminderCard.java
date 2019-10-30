package seedu.address.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminders.Reminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> implements PropertyChangeListener {
    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;

    Reminder.Status status;

    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");

        this.status = reminder.getStatus();
        String descWithStatus = "[" + status.toString() + "] " + reminder.getMessage().fullDesc + "No. of conditions: "
                + reminder.getConditions().size();
        desc.setText(descWithStatus);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.status = (Reminder.Status) evt.getNewValue();
        String descWithStatus = "[" + status.toString() + "] " + reminder.getMessage().fullDesc + "No. of conditions: "
                + reminder.getConditions().size();
        //desc.setText(descWithStatus);
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
}
