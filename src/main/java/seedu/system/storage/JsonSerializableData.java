package seedu.system.storage;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.Data;

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
