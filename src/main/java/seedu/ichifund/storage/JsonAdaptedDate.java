package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

/**
 * Jackson-friendly version of {@link Date}.
 */
public class JsonAdaptedDate {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Date's %s field is missing!";

    private final String day;
    private final String month;
    private final String year;

    /**
     * Constructs a {@code JsonAdaptedDate} with the given date details.
     */
    @JsonCreator
    public JsonAdaptedDate(@JsonProperty("day") String day, @JsonProperty("month") String month,
                           @JsonProperty("year") String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Converts a given {@code Date} into this class for Jackson use.
     */
    public JsonAdaptedDate(Date source) {
        day = source.getDay().toString();
        month = source.getMonth().toString();
        year = source.getYear().toString();
    }

    /**
     * Converts this Jackson-friendly adapted date object into the model's {@code Date} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted date.
     */
    public Date toModelType() throws IllegalValueException {
        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        final Day modelDay = new Day(day);

        if (month == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Month.class.getSimpleName()));
        }
        if (!Month.isValidMonth(month)) {
            throw new IllegalValueException(Month.MESSAGE_CONSTRAINTS);
        }
        final Month modelMonth = new Month(month);

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName()));
        }
        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }
        final Year modelYear = new Year(year);

        if (!Date.isValidDate(modelDay, modelMonth, modelYear)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        return new Date(modelDay, modelMonth, modelYear);
    }
}
