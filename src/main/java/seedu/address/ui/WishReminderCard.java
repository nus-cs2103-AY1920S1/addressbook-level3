package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.WishReminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class WishReminderCard extends UiPart<Region> {

    private static final String FXML = "WishReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final WishReminder wishReminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label desc;
    @FXML
    private Label id;
    @FXML
    private Label amt;
    @FXML
    private Label date;

    public WishReminderCard(WishReminder wishReminder, int displayedIndex) {
        super(FXML);
        this.wishReminder = wishReminder;
        id.setText(displayedIndex + ". ");

        desc.setText(wishReminder.getMessage());
        //date.setText(wishReminder.getDate());
        //amt.setText(wishReminder.getSum() + " / " + wishReminder.getQuota());

        /*String type = reminder.getType().toLowerCase();
        String descWithType = "[" + type + "] " + entry.getDesc().fullDesc;
        desc.setText(descWithType);
        date.setText(entry.getDate().toString());
        amt.setText("$" + entry.getAmount().value);

        entry.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));*/
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WishReminderCard)) {
            return false;
        }

        // state check
        WishReminderCard card = (WishReminderCard) other;
        return id.getText().equals(card.id.getText())
                && wishReminder.equals(card.wishReminder);
    }
}
