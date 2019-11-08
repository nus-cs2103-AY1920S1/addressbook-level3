package seedu.address.storage.event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;

/**
 * An Immutable eventRecord that is serializable to JSON format.
 */
@JsonRootName(value = "events")
class JsonSerializableEvents {
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableQuestions} with the given questions.
     */
    @JsonCreator
    public JsonSerializableEvents(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyEvents} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *               JsonSerializableQuestions}.
     */
    public JsonSerializableEvents(ReadOnlyEvents source) {
        events.addAll(source.getAllEvents().stream().map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts the saved events into the model's {@code eventRecord} object.
     */
    public EventRecord toModelType() throws IllegalValueException {
        List<Event> eventList = new ArrayList<>();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event question = jsonAdaptedEvent.toModelType();
            eventList.add(question);
        }
        return new EventRecord(eventList);
    }
}
