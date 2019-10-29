package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DateTime;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Event;
import seedu.address.model.calendar.Reminder;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent extends JsonAdaptedCalendarEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final Optional<String> endingDateTime;
    private final Optional<JsonAdaptedReminder> autoReminder;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("description") String description,
                            @JsonProperty("dateTime") String dateTime,
                            @JsonProperty("endingDateTime") Optional<String> endingDateTime,
                            @JsonProperty("autoReminder") Optional<JsonAdaptedReminder> autoReminder) {
        super(description, dateTime);
        this.endingDateTime = endingDateTime;
        this.autoReminder = autoReminder;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        super(source);
        if (source.getEndingDateTime().isPresent()) {
            this.endingDateTime = Optional.of(source.getEndingDateTime().get().toString());
        } else {
            this.endingDateTime = Optional.empty();
        }

        if (source.getAutoReminder().isPresent()) {
            this.autoReminder = Optional.of(new JsonAdaptedReminder(source.getAutoReminder().get()));
        } else {
            this.autoReminder = Optional.empty();
        }
    }

    @Override
    public CalendarEntry toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);
        Event modelEvent = new Event(modelDescription, modelDateTime);

        if (endingDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateTime.class.getSimpleName()));
        }
        final DateTime modelEndingDateTime;
        if (endingDateTime.isPresent()) {
            if (!DateTime.isValidDateTime(endingDateTime.get())) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
            }
            modelEvent.setEndingDateTime(new DateTime(endingDateTime.get()));
        }

        if (autoReminder.isPresent()) {
            Reminder modelReminder = autoReminder.get().toModelType();
            modelEvent.setAutoReminder(modelReminder);
        }

        return modelEvent;
    }
}
