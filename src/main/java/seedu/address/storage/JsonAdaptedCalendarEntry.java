package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.CalendarEntry;

/**
 * Jackson-friendly version of {@link CalendarEntry}.
 */
public abstract class JsonAdaptedCalendarEntry {
    protected final String description;
    protected final String dateTime;

    /**
     * Constructs a {@code JsonAdaptedCalendarEntry} with the given calendar entry details.
     */
    @JsonCreator
    public JsonAdaptedCalendarEntry(@JsonProperty("description") String description,
                                    @JsonProperty("dateTime") String dateTime) {
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code CalendarEntry} into this class for Jackson use.
     */
    public JsonAdaptedCalendarEntry(CalendarEntry source) {
        this.description = source.getDescription().value;
        this.dateTime = source.getDateTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted calendar entry object into the sugarmummy.recmfood.model's {@code
     * CalendarEntry} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the calendar entry.
     */
    public abstract CalendarEntry toModelType() throws IllegalValueException;

}
