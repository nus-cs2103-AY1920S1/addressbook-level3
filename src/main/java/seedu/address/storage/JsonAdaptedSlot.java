package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Slot;

/**
 * Jackson-friendly version of (@link Slot}.
 */
public class JsonAdaptedSlot {

    private final String date;
    private final String start;
    private final String end;

    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code slot}s.
     */
    @JsonCreator
    public JsonAdaptedSlot(String date, String start, String end) {
        this.date = date;
        this.start = start;
        this.end = end;
    }

    /**
     * Converts a given {@code Slot} into this class for Jackson use.
     */
    public JsonAdaptedSlot(Slot source) {
        date = source.date;
        start = source.start;
        end = source.end;
    }

    @JsonValue
    public String getSlot() {
        return String.format(Slot.STRING_FORMAT, date, start, end);
    }

    /**
     * Converts this Jackson-friendly adapted department object into the model's {@code Department} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted department.
     */
    public Slot toModelType() throws IllegalValueException {
        if (!Slot.isValidSlot(String.format(Slot.STRING_FORMAT, date, start, end))) {
            throw new IllegalValueException(Slot.MESSAGE_CONSTRAINTS);
        }
        return new Slot(date, start, end);
    }
}
