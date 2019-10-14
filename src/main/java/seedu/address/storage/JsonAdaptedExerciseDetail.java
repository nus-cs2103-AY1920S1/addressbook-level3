package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.details.ExerciseDetail;

/**
 * Jackson-friendly version of {@link ExerciseDetail}.
 */
abstract class JsonAdaptedExerciseDetail<T> {

    private final T magnitude;

    /**
     * Constructs a {@code JsonAdaptedExerciseDetail} with the given {@code magnitude}.
     */
    @JsonCreator
    public JsonAdaptedExerciseDetail(T magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Converts a given {@code ExerciseDetail} into this class for Jackson use.
     */
    public JsonAdaptedExerciseDetail(ExerciseDetail source) {
        magnitude = (T) source.getMagnitude();
    }

    @JsonValue
    public T getMagnitude() {
        return magnitude;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise detail.
     */
    abstract public ExerciseDetail toModelType() throws IllegalValueException;

}
