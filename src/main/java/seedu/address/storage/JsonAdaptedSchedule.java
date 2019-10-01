package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;

/**
 * Jackson-friendly version of {@link Schedule}.
 */
public class JsonAdaptedSchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Schedule's %s field is missing!";

    private final String personId;
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given Schedule details.
     */
    @JsonCreator
    public JsonAdaptedSchedule(@JsonProperty("personId") String personId,
                               @JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.personId = personId;
        if (events != null) {
            this.events.addAll(events);
        }
    }

    /**
     * Converts a given {@code Schedule} into this class for Jackson use.
     */
    public JsonAdaptedSchedule(Schedule source) {
        personId = source.getPersonId().toString();
        events.addAll(source.getEvents().stream()
                .map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule.
     */
    public Schedule toModelType() throws IllegalValueException {
        final ArrayList<Event> scheduleEvents = new ArrayList<>();
        for (JsonAdaptedEvent event : events) {
            scheduleEvents.add(event.toModelType());
        }

        if (personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }
        final PersonId modelPersonId = new PersonId(personId);

        return new Schedule(modelPersonId, scheduleEvents);
    }


}
