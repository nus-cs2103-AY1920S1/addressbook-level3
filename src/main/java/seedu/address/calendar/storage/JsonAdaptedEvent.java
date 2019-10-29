package seedu.address.calendar.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.calendar.model.event.*;
import seedu.address.calendar.model.date.Date;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.Optional;

/**
 * Jackson-friendly version of {@link Event}
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String startDate;
    private final String endDate;
    private final String info;
    private final String eventType;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("startDate") String startDate,
                            @JsonProperty("endDate") String endDate, @JsonProperty("info") String info,
                            @JsonProperty("eventType") String eventType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
        this.eventType = eventType;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getNameStr();
        startDate = source.getStartDateStr();
        endDate = source.getEndDateStr();
        info = source.getInfoStr();
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
        final Name eventName = new Name(name);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date of event"));
        }
        final Date startDate = Date.getInstanceFromString(this.startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date of event"));
        }
        final Date endDate = Date.getInstanceFromString(this.endDate);

        final Optional<Info> info;
        if (this.info == null) {
            info = Optional.empty();
        } else {
            info = Optional.of(new Info(this.info));
        }

        if (eventType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "event type"));
        }

        final EventType eventType = EventType.getInstanceFromString(this.eventType);

        if (eventType.equals(EventType.COMMITMENT)) {
            return new Commitment(eventName, startDate, endDate, info);
        } else if (eventType.equals(EventType.HOLIDAY)) {
            return new Holiday(eventName, startDate, endDate, info);
        } else if (eventType.equals(EventType.SCHOOL_BREAK)) {
            return new SchoolBreak(eventName, startDate, endDate, info);
        } else {
            assert eventType.equals(EventType.TRIP) : "There are only 4 types of events permitted";
            return new Trip(eventName, startDate, endDate, info);
        }
    }
}
