package dukecooks.storage.workout.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.Repetitions;

/**
 * Jackson-friendly version of {@link Repetitions}.
 */
public class JsonAdaptedRepetitions<Integer> extends JsonAdaptedExerciseDetail {

    /**
     * Constructs a {@code JsonAdaptedRepetitions} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedRepetitions(@JsonProperty("magnitude") int magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedRepetitions(Repetitions source) {
        this.magnitude = source.getMagnitude();
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     */
    public ExerciseDetail toModelType() {
        return new Repetitions((java.lang.Integer) magnitude);
    }

}
