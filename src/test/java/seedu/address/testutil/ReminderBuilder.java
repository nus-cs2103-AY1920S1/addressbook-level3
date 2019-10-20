package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.calendar.DateTime;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;

/**
* A utility class to help with building Reminder objects.
*/
public class ReminderBuilder {
    public static final String DEFAULT_DESCRIPTION = "Insulin injection";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2019, 10, 12);
    public static final LocalTime DEFAULT_TIME = LocalTime.of(11, 30);
    public static final Repetition DEFAULT_REPETITION = Repetition.Daily;

    private Description description;
    private DateTime dateTime;
    private Repetition repetition;

    public ReminderBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        dateTime = new DateTime(DEFAULT_DATE, DEFAULT_TIME);
        repetition = DEFAULT_REPETITION;
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        description = reminderToCopy.getDescription();
        dateTime = reminderToCopy.getDateTime();
        repetition = reminderToCopy.getRepetition();
    }

    /**
     * Sets the {@code Description} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDateTime(LocalDate date, LocalTime time) {
        this.dateTime = new DateTime(date, time);
        return this;
    }

    /**
     * Sets the {@code repetition} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withRepetition(Repetition repetition) {
        this.repetition = repetition;
        return this;
    }

    public Reminder build() {
        return new Reminder(description, dateTime, repetition);
    }

}
