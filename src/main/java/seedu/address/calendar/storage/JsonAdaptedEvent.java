package seedu.address.calendar.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.event.Holiday;
import seedu.address.calendar.model.event.Name;
import seedu.address.calendar.model.event.SchoolBreak;
import seedu.address.calendar.model.event.Trip;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link Event}
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String startDate;
    private final String endDate;
    private final String eventType;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("startDate") String startDate,
                            @JsonProperty("endDate") String endDate, @JsonProperty("eventType") String eventType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventType = eventType;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getNameStr();
        startDate = source.getStartDateStr();
        endDate = source.getEndDateStr();
        eventType = source.getEventTypeStr();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event}
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name of event"));
        }

        if (!Name.isValidNameString(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINT);
        }

        final Name eventName = new Name(name);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date of event"));
        }
        final Date startDate = Date.getInstanceFromString(this.startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date of event"));
        }
        final Date endDate = Date.getInstanceFromString(this.endDate);

        if (!EventQuery.isValidEventTime(startDate, endDate)) {
            throw new IllegalValueException("Start date cannot be after end date");
        }

        if (eventType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "event type"));
        }

        final EventType eventType = EventType.getInstanceFromString(this.eventType);

        if (eventType.equals(EventType.COMMITMENT)) {
            return new Commitment(eventName, startDate, endDate);
        } else if (eventType.equals(EventType.HOLIDAY)) {
            return new Holiday(eventName, startDate, endDate);
        } else if (eventType.equals(EventType.SCHOOL_BREAK)) {
            return new SchoolBreak(eventName, startDate, endDate);
        } else {
            assert eventType.equals(EventType.TRIP) : "There are only 4 types of events permitted";
            return new Trip(eventName, startDate, endDate);
        }
    }
}
