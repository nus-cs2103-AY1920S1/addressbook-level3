package seedu.ichifund.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.repeater.MonthOffset;

/**
 * Jackson-friendly version of {@link MonthOffset}.
 */
class JsonAdaptedMonthOffset {

    private final String offset;

    /**
     * Constructs a {@code JsonAdaptedMonthOffset} with the given {@code monthOffset}.
     */
    @JsonCreator
    public JsonAdaptedMonthOffset (String offset) {
        this.offset = offset;
    }

    /**
     * Converts a given {@code MonthOffset} into this class for Jackson use.
     */
    public JsonAdaptedMonthOffset(MonthOffset source) {
        this.offset = source.offset.toString();
    }

    @JsonValue
    public String getOffset() {
        return this.offset;
    }

    /**
     * Converts this Jackson-friendly adapted month offset object into the model's {@code MonthOffset} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted month offset.
     */
    public MonthOffset toModelType() throws IllegalValueException {
        if (!MonthOffset.isValidOffset(this.offset)) {
            throw new IllegalValueException(MonthOffset.MESSAGE_CONSTRAINTS);
        }
        return new MonthOffset(this.offset);
    }

}
