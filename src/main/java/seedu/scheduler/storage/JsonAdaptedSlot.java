package seedu.scheduler.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.person.Slot;

/**
 * Jackson-friendly version of (@link Slot}.
 */
public class JsonAdaptedSlot {

    private final String slot;

    /**
     * Constructs a {@code JsonAdaptedSlot} with the given {@code slot}s
     */
    @JsonCreator
    public JsonAdaptedSlot(String slot) {
        this.slot = slot;
    }

    /**
     * Converts a given {@code Slot} into this class for Jackson use.
     */
    public JsonAdaptedSlot(Slot source) {
        this.slot = source.toString();
    }

    @JsonValue
    public String getSlot() {
        return this.slot;
    }


    /**
     * Converts this Jackson-friendly adapted slot object into the model's {@code Slot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted slot.
     */
    public Slot toModelType() throws IllegalValueException {
        return Slot.fromString(this.slot);
    }
}
