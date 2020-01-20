package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.timetable.TimeRange;

import java.time.format.DateTimeFormatter;

/**
 * Jackson-friendly version of {@link TimeRange}.
 */
class JsonAdaptedTimeRange {

    private final String timeRange;

    /**
     * Constructs a {@code JsonAdaptedTimeRange} with the given {@code timeRange}.
     */
    @JsonCreator
    public JsonAdaptedTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * Converts a given {@code TimeRange} into this class for Jackson use.
     */
    public JsonAdaptedTimeRange(TimeRange source) {
        timeRange = source.getStart().getDay().toString() + " " + source.getStart().getTime().format(DateTimeFormatter.ofPattern("HHmm")) + " "
                + source.getEnd().getDay().toString() + " " + source.getEnd().getTime().format(DateTimeFormatter.ofPattern("HHmm"));
    }

    @JsonValue
    public String getTimerange() {
        return timeRange;
    }

    /**
     * Converts this Jackson-friendly adapted timeRange object into the model's {@code TimeRange} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timeRange.
     */
    public TimeRange toModelType() throws IllegalValueException {
        return ParserUtil.parseTimeRange(timeRange);
    }

}
