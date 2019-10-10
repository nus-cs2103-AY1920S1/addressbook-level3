package seedu.address.commons.core;

import java.util.Date;

/**
 * Reminder class to notify user with deadlines.
 */
public class Reminder {
    private String reminderMessage;
    private Date deadline;

    public Reminder(String reminderMessage, Date deadline) {
        this.reminderMessage = reminderMessage;
        this.deadline = deadline;
    }

    public String getReminderMessage() {
        return this.reminderMessage;
    }

    public Date getDeadline() {
        return this.deadline;
    }
}
