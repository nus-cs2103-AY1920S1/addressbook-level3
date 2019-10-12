package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.event.Event;

/**
 * An Immutable EventBook that is serializable to JSON format.
 */
@JsonRootName(value = "eventbook")
class JsonSerializableEventBook {

    public static final String MESSAGE_DUPLICATE_EVENTS = "Events list contains duplicate event(s).";
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableEventBook(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableEventBook(ReadOnlyEventBook source) {
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventBook toModelType() throws IllegalValueException {
        EventBook eventBook = new EventBook();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (eventBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENTS);
            }
            eventBook.addEvent(event);
        }
        return eventBook;
    }

}
