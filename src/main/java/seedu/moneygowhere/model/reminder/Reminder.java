package seedu.moneygowhere.model.reminder;

import static seedu.moneygowhere.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.moneygowhere.commons.util.DateUtil;
import seedu.moneygowhere.model.spending.Date;

/**
 * Reminder class to notify user with deadlines.
 */
public class Reminder {
    private ReminderMessage reminderMessage;
    private Date deadline;
    private long remainingDays;

    public Reminder(Date deadline, ReminderMessage reminderMessage) {
        requireAllNonNull(deadline, reminderMessage);
        this.reminderMessage = reminderMessage;
        this.deadline = deadline;
        this.remainingDays = DateUtil.getDaysBetween(DateUtil.getTodayDate(), deadline.dateValue);
    }

    public ReminderMessage getReminderMessage() {
        return reminderMessage;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    /**
     * Returns the due date description of a reminder for displaying on UI.
     */
    public String getDueDateDescription() {
        if (remainingDays == 0) {
            return "Today";
        } else if (remainingDays == 1) {
            return "Tomorrow";
        } else if (remainingDays < 0) {
            return "Overdue\n" + DateUtil.twoDigitYearFormatDate(deadline.value);
        } else {
            return "in " + remainingDays + " days\n" + DateUtil.twoDigitYearFormatDate(deadline.value);
        }
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

    /**
     * Returns true if both reminders have the same identity and data fields.
     * This defines a stronger notion of equality between two reminders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherSpending = (Reminder) other;
        return otherSpending.getDeadline().equals(getDeadline())
                && otherSpending.getReminderMessage().equals(getReminderMessage());
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
