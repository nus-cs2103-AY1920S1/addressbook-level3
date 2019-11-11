package seedu.address.calendar.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.calendar.model.ReadOnlyCalendar;
import seedu.address.calendar.model.event.Event;
import seedu.address.commons.exceptions.IllegalValueException;

@JsonRootName(value = "calendar")
/**
 * Serializable version of the calendar.
 */
public class JsonSerializableCalendar {
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCalendar} with the given events.
     */
    @JsonCreator
    public JsonSerializableCalendar(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyCalendar} into this class for use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCalendar}
     */
    public JsonSerializableCalendar(ReadOnlyCalendar source) {
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this calendar into the model's {@code ReadOnlyCalendar} object.
     *
     * @throws IllegalValueException
     */
    public ReadOnlyCalendar toModelType() throws IllegalValueException {
        List<Event> copyOfEvents = new ArrayList<>();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            copyOfEvents.add(jsonAdaptedEvent.toModelType());
        }
        return new ReadOnlyCalendar(copyOfEvents);
    }
}
