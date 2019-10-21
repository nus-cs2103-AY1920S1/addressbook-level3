package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.model.DateTime;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Event;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_DESCRIPTION = "Appointment";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020, 1, 10);
    public static final LocalTime DEFAULT_TIME = LocalTime.of(14, 45);
    public static final LocalTime DEFAULT_ENDING_TIME = LocalTime.of(16, 0);
    public static final Reminder DEFAULT_AUTO_REMINDER = new Reminder(new Description("auto reminder"),
            new DateTime(DEFAULT_DATE, LocalTime.of(13, 20)), Repetition.Once);


    private Description description;
    private DateTime dateTime;
    private Optional<DateTime> endingDateTime;
    private Optional<Reminder> autoReminder;

    public EventBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        dateTime = new DateTime(DEFAULT_DATE, DEFAULT_TIME);
        endingDateTime = Optional.of(new DateTime(DEFAULT_DATE, DEFAULT_ENDING_TIME));
        autoReminder = Optional.of(DEFAULT_AUTO_REMINDER);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        description = eventToCopy.getDescription();
        dateTime = eventToCopy.getDateTime();
        endingDateTime = eventToCopy.getEndingDateTime();
        autoReminder = eventToCopy.getAutoReminder();
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withDateTime(LocalDate date, LocalTime time) {
        this.dateTime = new DateTime(date, time);
        return this;
    }

    /**
     * Sets the {@code endingDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndingDateTime(LocalDate date, LocalTime time) {
        this.endingDateTime = Optional.ofNullable(new DateTime(date, time));
        return this;
    }

    /**
     * Sets the {@code endingDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndingDateTime(DateTime endingDateTime) {
        this.endingDateTime = Optional.ofNullable(endingDateTime);
        return this;
    }

    /**
     * Sets the {@code autoReminder} of the {@code Event} that we are building.
     */
    public EventBuilder withAutoReminder(Reminder autoReminder) {
        this.autoReminder = Optional.ofNullable(autoReminder);
        return this;
    }

    /**
     * Build a event.
     * @return a event.
     */
    public Event build() {
        Event event = new Event(description, dateTime);
        event.setEndingDateTime(endingDateTime);
        event.setAutoReminder(autoReminder);
        return event;
    }

}
