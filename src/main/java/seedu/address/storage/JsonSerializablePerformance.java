package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Performance;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.performance.Event;

/**
 * An Immutable EventList that is serializable to JSON format.
 */
public class JsonSerializablePerformance {

    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEvents} with the given events.
     */
    @JsonCreator
    public JsonSerializablePerformance(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code stream} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEvents}.
     */
    public JsonSerializablePerformance(ReadOnlyPerformance source) {
        events.addAll(source.getPerformance().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this event list into the model's {@code EventList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Performance toModelType() throws IllegalValueException {
        Performance eventList = new Performance();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (eventList.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventList.addEvent(event);
        }
        return eventList;
    }


}
