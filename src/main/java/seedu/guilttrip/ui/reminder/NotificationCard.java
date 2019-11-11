package seedu.guilttrip.ui.reminder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.guilttrip.model.reminders.messages.Notification;
import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code GeneralReminder}.
 */
public class NotificationCard extends UiPart<Region> {
    private static final String FXML = "reminder/ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final Notification notification;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;

    public NotificationCard(Notification notification, int displayedIndex) {
        super(FXML);
        this.notification = notification;
        id.setText(displayedIndex + ". ");
        desc.setText(notification.toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotificationCard)) {
            return false;
        }

        // state check
        NotificationCard card = (NotificationCard) other;
        return id.getText().equals(card.id.getText())
                && notification.equals(card.notification);
    }
}
