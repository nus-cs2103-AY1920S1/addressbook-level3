package seedu.sugarmummy.storage;

import seedu.sugarmummy.commons.exceptions.IllegalValueException;

/**
 * Represents one type of Immutable content that is serializable to JSON format.
 */
public interface JsonSerializableContent<T> {

    /**
     * Converts the content into the sugarmummy.recmfood.model's corresponding object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    T toModelType() throws IllegalValueException;
}
