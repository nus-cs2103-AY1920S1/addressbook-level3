package seedu.moneygowhere.testutil;

import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.reminder.ReminderMessage;
import seedu.moneygowhere.model.spending.Date;

/**
 * A utility class to help with building Spending objects.
 */
public class ReminderBuilder {
    public static final String DEFAULT_DEADLINE = "25/12/2019";
    public static final String DEFAULT_REMINDER_MESSAGE = "Pay Bill";

    private Date deadline;
    private ReminderMessage reminderMessage;

    public ReminderBuilder() {
        deadline = new Date(DEFAULT_DEADLINE);
        reminderMessage = new ReminderMessage(DEFAULT_REMINDER_MESSAGE);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        deadline = reminderToCopy.getDeadline();
        reminderMessage = reminderToCopy.getReminderMessage();
    }

    /**
     * Sets the {@code Deadline} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDeadline(String deadline) {
        this.deadline = new Date(deadline);
        return this;
    }

    /**
     * Sets the {@code ReminderMessage} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withRemark(String reminderMessage) {
        this.reminderMessage = new ReminderMessage(reminderMessage);
        return this;
    }

    public Reminder build() {
        return new Reminder(deadline, reminderMessage);
    }
}
