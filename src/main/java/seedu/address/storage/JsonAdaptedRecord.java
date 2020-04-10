package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.Record;
import seedu.address.model.performance.Timing;

/**
 * Jackson-friendly version of {@link Record}.
 */
public class JsonAdaptedRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Record's %s field is missing!";

    private final String date;
    private final String timing;

    /**
     * Constructs a {@code JsonAdaptedRecord} with the given record details.
     */
    @JsonCreator
    public JsonAdaptedRecord(@JsonProperty("date") String date, @JsonProperty("timing") String timing) {
        this.date = date;
        this.timing = timing;
    }

    /**
     * Converts a given {@code Record} into this class for Jackson use.
     */
    public JsonAdaptedRecord(Record source) {
        date = source.getDate().getUnparsed();
        timing = source.getTiming().getUnparsed();
    }

    /**
     * Converts this Jackson-friendly adapted record object into the model's {@code Record} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted record.
     */
    public Record toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        final AthletickDate modelDate = ParserUtil.parseDate(date);

        if (timing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "timing"));
        }

        final Timing modelTiming = ParserUtil.parseTiming(timing);

        return new Record(modelDate, modelTiming);
    }
}
