package seedu.address.testutil.event;

import java.time.LocalDateTime;

import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "My Event";
    public static final LocalDateTime DEFAULT_START_TIME = LocalDateTime.now();
    public static final LocalDateTime DEFAULT_END_TIME = LocalDateTime.now().plusHours(2);
    public static final String DEFAULT_COLOR_CATEGORY = "group00";
    public static final String DEFAULT_UNIQUE_IDENTIFIER = "dummyUniqueIdentifier" + LocalDateTime.now().toString();
    public static final RecurrenceType DEFAULT_RECURRENCE_TYPE = RecurrenceType.NONE;

    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String colorCategory;
    private String uniqueIdentifier;
    private RecurrenceType recurrenceType;

    public EventBuilder() {
        eventName = DEFAULT_EVENT_NAME;
        startDateTime = DEFAULT_START_TIME;
        endDateTime = DEFAULT_END_TIME;
        colorCategory = DEFAULT_COLOR_CATEGORY;
        uniqueIdentifier = DEFAULT_UNIQUE_IDENTIFIER;
        recurrenceType = DEFAULT_RECURRENCE_TYPE;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        uniqueIdentifier = eventToCopy.getUniqueIdentifier();
        colorCategory = eventToCopy.getColorCategory();
        recurrenceType = eventToCopy.getRecurrenceType();
    }

    /**
     * Sets the {@code eventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    /**
     * Sets the {@code uniqueIdentifier} of the {@code Event} that we are building.
     */
    public EventBuilder withUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }

    /**
     * Sets the {@code startDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    /**
     * Sets the {@code colorCategory} of the {@code Event} that we are building.
     */
    public EventBuilder withColorCategory(String colorCategory) {
        this.colorCategory = colorCategory;
        return this;
    }

    /**
     * Sets the {@code recurrenceType} of the {@code Event} that we are building.
     */
    public EventBuilder withRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
        return this;
    }

    public Event build() {
        return new Event(eventName, startDateTime, endDateTime, colorCategory, uniqueIdentifier, recurrenceType);
    }
}
