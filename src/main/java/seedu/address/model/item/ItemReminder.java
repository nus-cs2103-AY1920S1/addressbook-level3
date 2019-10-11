package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Reminder of an Item
 */
public class ItemReminder {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be blank.";
    public final String itemReminder;

    public ItemReminder(String reminder) {
        requireNonNull(reminder);
        checkArgument(isValidReminder(reminder), MESSAGE_CONSTRAINTS);
        itemReminder = reminder;
    }

    // Can add more checks to see if datetime is valid
    public static boolean isValidReminder(String reminder) {
        return (!reminder.isEmpty());
    }

    public boolean isPresent() {
        return (!itemReminder.isEmpty());
    }

    public String getDate() {
        return itemReminder;
    }

    @Override
    public String toString() {
        return itemReminder;
    }
}
