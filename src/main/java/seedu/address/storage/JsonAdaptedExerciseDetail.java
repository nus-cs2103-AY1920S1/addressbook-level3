package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.details.ExerciseDetail;

/**
 * Jackson-friendly version of {@link ExerciseDetail}.
 */
abstract class JsonAdaptedExerciseDetail<T> {

    protected T magnitude;

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise detail.
     */
    abstract public ExerciseDetail toModelType() throws IllegalValueException;

}
