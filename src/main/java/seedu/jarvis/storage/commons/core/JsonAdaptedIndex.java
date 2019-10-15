package seedu.jarvis.storage.commons.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link Index}.
 */
public class JsonAdaptedIndex {
    public static final String MESSAGE_INVALID_INDEX = "This index is out of bounds";
    private final int zeroBasedIndex;

    /**
     * Constructs a {@code JsonAdaptedIndex} with the given {@code zeroBasedIndex}.
     *
     * @param zeroBasedIndex {@code zeroBasedIndex} integer.
     */
    @JsonCreator
    public JsonAdaptedIndex(int zeroBasedIndex) {
        this.zeroBasedIndex = zeroBasedIndex;
    }

    /**
     * Converts a given {@code Index} into this class for Jackson use.
     *
     * @param index {@code Index} to be converted to {@code JsonAdaptedIndex}.
     */
    public JsonAdaptedIndex(Index index) {
        zeroBasedIndex = index.getZeroBased();
    }

    @JsonValue
    public int getZeroBasedIndex() {
        return zeroBasedIndex;
    }

    /**
     * Converts this Jackson-friendly adapted index object into the model's {@code Index} object.
     *
     * @return {@code Index} object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted index.
     */
    public Index toModelType() throws IllegalValueException {
        try {
            return Index.fromZeroBased(zeroBasedIndex);
        } catch (IndexOutOfBoundsException iobe) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
    }
}
