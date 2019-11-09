package seedu.elisa.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Reminder;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ReminderListCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Item item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label time;

    public ReminderListCard(Item item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");
        description.setText(item.getItemDescription().toString());
        Reminder reminder = item.getReminder().get();
        date.setText(String.valueOf(reminder.getOccurrenceDateTime().getDayOfMonth())
                + " " + String.valueOf(reminder.getOccurrenceDateTime().getMonth()).substring(0, 3));
        time.setText(String.valueOf(reminder.getOccurrenceDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemCard)) {
            return false;
        }

        // state check
        ReminderListCard card = (ReminderListCard) other;
        return id.getText().equals(card.id.getText())
                && item.equals(card.item);
    }
}
