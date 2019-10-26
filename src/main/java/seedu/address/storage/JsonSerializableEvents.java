package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyEvents;
import seedu.address.model.performance.Event;

public class JsonSerializableEvents {

    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEvents} with the given events.
     */
    @JsonCreator
    public JsonSerializableEvents(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code stream} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEvents}.
     */
    public JsonSerializableEvents(ReadOnlyEvents source) {
        events.addAll(source.getEvents().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this event list into the model's {@code EventList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventList toModelType() throws IllegalValueException {
        EventList eventList = new EventList();
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
