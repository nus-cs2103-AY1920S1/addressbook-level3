package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.calendar.Event;

/**
 * A list of reminders that is serializable to JSON format.
 */
@JsonRootName(value = "eventlist")
public class JsonSerializableEventList {
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedEvent> eventList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEventList} with the given events.
     */
    @JsonCreator
    public JsonSerializableEventList(@JsonProperty("eventList") List<JsonAdaptedEvent> events) {
        this.eventList.addAll(events);
    }

    /**
     * Converts a given list of events into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created list of events.
     */
    public JsonSerializableEventList(ReadOnlyCalendar source) {
        this.eventList.addAll(source.getCalendarEntryList().stream()
            .map(calendarEntry -> (Event) calendarEntry)
            .map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this list of reminders into the sugarmummy.recmfood.model's {@code Reminder} objects.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReadOnlyCalendar toModelType() throws IllegalValueException {
        Calendar events = new Calendar();

        for (JsonAdaptedEvent jsonAdaptedEvent : eventList) {
            Event event = (Event) jsonAdaptedEvent.toModelType();
            if (events.hasCalendarEntry(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            events.addCalendarEntry(event);
        }
        return events;
    }
}
