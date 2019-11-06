package seedu.sugarmummy.storage.calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.sugarmummy.commons.exceptions.IllegalValueException;
import seedu.sugarmummy.model.calendar.Description;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.calendar.Repetition;
import seedu.sugarmummy.model.time.DateTime;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder extends JsonAdaptedCalendarEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String repetition;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("description") String description,
            @JsonProperty("dateTime") String dateTime,
                    @JsonProperty("repetition") String repetition) {
        super(description, dateTime);
        this.repetition = repetition;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        super(source);
        this.repetition = source.getRepetition().toString();
    }

    @Override
    public Reminder toModelType() throws IllegalValueException {
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

        if (repetition == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Repetition.class.getSimpleName()));
        }
        if (!Repetition.isValidRepetition(repetition)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Repetition modelRepetition = Repetition.of(repetition);
        return new Reminder(modelDescription, modelDateTime, modelRepetition);
    }
}
