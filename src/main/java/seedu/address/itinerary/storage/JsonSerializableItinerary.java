package seedu.address.itinerary.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.itinerary.model.ReadOnlyItinerary;
import seedu.address.itinerary.model.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonRootName(value = "itinerary")
public class JsonSerializableItinerary {
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCalendar} with the given events.
     */
    @JsonCreator
    public JsonSerializableItinerary(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyItinerary} into this class for use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableItinerary}
     */
    public JsonSerializableItinerary(ReadOnlyItinerary source) {
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this calendar into the model's {@code ReadOnlyCalendar} object.
     *
     * @throws IllegalValueException
     */
    public ReadOnlyItinerary toModelType() throws IllegalValueException {
        List<Event> copyOfEvents = new ArrayList<>();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            copyOfEvents.add(jsonAdaptedEvent.toModelType());
        }
        return new ReadOnlyItinerary(copyOfEvents);
    }
}
