package seedu.address.testutil;

import java.util.TreeSet;

import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.model.reminder.ReminderTime;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {
    public static final String DEFAULT_DESCRIPTION = "Tutorial 4";

    private ReminderDescription reminderDescription;
    private TreeSet<ReminderTime> reminderTimes;

    public ReminderBuilder() {
        reminderDescription = new ReminderDescription(DEFAULT_DESCRIPTION);
        reminderTimes = new TreeSet<>();
    }

    /**
     * Initializes the ReminerBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        reminderDescription = reminderToCopy.getDescription();
        reminderTimes = reminderToCopy.getTime();
    }

    /**
     * Sets the {@code classId} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDescription(String description) {
        this.reminderDescription = new ReminderDescription(description);
        return this;
    }

    /**
     * Parses the {@code reminderTimes} into a {@code Set<ReminderTime>}
     * and set it to the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminderTimes(String ... reminderTimes) {
        this.reminderTimes = SampleDataUtil.getReminderTimeSet(reminderTimes);
        return this;
    }

    public Reminder build() {
        return new Reminder(reminderDescription, reminderTimes);
    }
}
