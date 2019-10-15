package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Data;

/**
 * An Immutable Data that is serializable to JSON format.
 */
public interface JsonSerializableData {

    /**
     * Converts the stored data into the model's {@code Data} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Data toModelType() throws IllegalValueException;
}
