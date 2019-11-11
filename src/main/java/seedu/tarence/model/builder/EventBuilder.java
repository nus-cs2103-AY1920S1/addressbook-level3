package seedu.tarence.model.builder;

import java.util.Date;

import seedu.tarence.model.tutorial.Event;


/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Event Name";
    public static final Date DEFAULT_START_TIME = new Date(0);
    public static final Date DEFAULT_END_TIME = new Date();

    private String eventName;
    private Date startTime;
    private Date endTime;

    public EventBuilder() {
        this.eventName = DEFAULT_EVENT_NAME;
        this.startTime = DEFAULT_START_TIME;
        this.endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.eventName;
        startTime = eventToCopy.startTime;
        endTime = eventToCopy.endTime;
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Event build() {
        return new Event(eventName, startTime, endTime);
    }

}
