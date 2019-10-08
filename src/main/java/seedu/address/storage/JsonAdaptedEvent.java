package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String eventName;
    private final List<JsonAdaptedTimeSlot> timeSlots = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given Event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName,
                            @JsonProperty("timeSlots") List<JsonAdaptedTimeSlot> timeSlots) {

        this.eventName = eventName;
        if (timeSlots != null) {
            this.timeSlots.addAll(timeSlots);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventName = source.getEventName();
        timeSlots.addAll(source.getTimeslots().stream()
                .map(JsonAdaptedTimeSlot::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final ArrayList<Timeslot> eventTimeslots = new ArrayList<>();
        for (JsonAdaptedTimeSlot timeSlot : timeSlots) {
            eventTimeslots.add(timeSlot.toModelType());
        }

        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Event.class.getSimpleName()));
        }
        final String modelEventName = eventName;

        return new Event(modelEventName, eventTimeslots);
    }

}
