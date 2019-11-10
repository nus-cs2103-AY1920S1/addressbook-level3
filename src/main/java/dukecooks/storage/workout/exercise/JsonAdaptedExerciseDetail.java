package dukecooks.storage.workout.exercise;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.exercise.details.ExerciseDetail;

/**
 * Jackson-friendly version of {@link ExerciseDetail}.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typ")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedSets.class, name = "SET"),
        @JsonSubTypes.Type(value = JsonAdaptedDistance.class, name = "DST"),
        @JsonSubTypes.Type(value = JsonAdaptedRepetitions.class, name = "REP"),
        @JsonSubTypes.Type(value = JsonAdaptedWeight.class, name = "WGT"),
        @JsonSubTypes.Type(value = JsonAdaptedTiming.class, name = "TIM")}
)
public abstract class JsonAdaptedExerciseDetail<T> {

    protected T magnitude;

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise detail.
     */
    public abstract ExerciseDetail toModelType() throws IllegalValueException;

}
