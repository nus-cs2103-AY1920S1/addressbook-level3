package seedu.system.storage;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.Data;
import seedu.system.model.UniqueElement;

/**
 * Jackson-friendly version of {@link Data}.
 */
public interface JsonAdaptedData<T extends UniqueElement> {
    /**
     * Converts this Jackson-friendly adapted unique element object into the model's {@code Data} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted element.
     */
    public T toModelType() throws IllegalValueException;
}
