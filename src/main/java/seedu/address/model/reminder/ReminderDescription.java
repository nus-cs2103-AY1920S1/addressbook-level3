package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Task's description in the calendar.
 */
public class ReminderDescription {
    public final String fullReminderDescription;

    /**
     * Constructs a {@code ReminderDescription}.
     *
     * @param reminderDescription A valid description.
     */
    public ReminderDescription(String reminderDescription) {
        requireNonNull(reminderDescription);
        fullReminderDescription = reminderDescription;
    }

    @Override
    public String toString() {
        return fullReminderDescription + "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDescription // instanceof handles nulls
                && fullReminderDescription.equals(((ReminderDescription) other).fullReminderDescription)); // state
    }

    @Override
    public int hashCode() {
        return fullReminderDescription.hashCode();
    }

}
