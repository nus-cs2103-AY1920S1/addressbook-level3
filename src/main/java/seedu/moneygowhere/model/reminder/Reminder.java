package seedu.moneygowhere.model.reminder;

import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.moneygowhere.model.spending.Date;

/**
 * Reminder class to notify user with deadlines.
 */
public class Reminder {
    private ReminderMessage reminderMessage;
    private Date deadline;

    public Reminder(Date deadline, ReminderMessage reminderMessage) {
        requireAllNonNull(deadline, reminderMessage);
        this.reminderMessage = reminderMessage;
        this.deadline = deadline;
    }

    public ReminderMessage getReminderMessage() {
        return reminderMessage;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    /**
     * Returns true if both Reminder of the same date have the same reminder message.
     * This defines a weaker notion of equality between two spending.
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getDeadline().equals(getDeadline())
                && otherReminder.getReminderMessage().equals(getReminderMessage());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(deadline, reminderMessage);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Deadline: ")
                .append(getDeadline())
                .append(" Message: ")
                .append(getReminderMessage());
        return builder.toString();
    }

}
