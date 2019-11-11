package seedu.jarvis.storage;

import seedu.jarvis.commons.exceptions.IllegalValueException;

/**
 * Interface for JsonAdapted classes.
 */
public interface JsonAdapter<T> {

    /**
     * Adapts a JsonAdapted class into the specified class type.
     *
     * @return object of type {@code T}.
     * @throws IllegalValueException If any data constraints are violated.
     */
    T toModelType() throws IllegalValueException;
}
