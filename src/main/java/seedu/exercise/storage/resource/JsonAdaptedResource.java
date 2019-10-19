package seedu.exercise.storage.resource;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.resource.Resource;

/**
 * Represents a Jackson-friendly version of {@code Resource}.
 */
public abstract class JsonAdaptedResource<T extends Resource> {

    /**
     * Converts the given {@code JsonAdaptedResource} into the model's {@code Resource} object.
     *
     * @throws IllegalValueException if there were any violations in the data constraints
     */
    public abstract T toModelType() throws IllegalValueException;
}
