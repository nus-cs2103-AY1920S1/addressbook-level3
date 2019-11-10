package dukecooks.storage.workout.exercise;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.Timing;

/**
 * Jackson-friendly version of {@link Timing}.
 */
public class JsonAdaptedTiming extends JsonAdaptedExerciseDetail {

    /**
     * Constructs a {@code JsonAdaptedTiming} with the given {@code magnitude}
     */
    @JsonCreator
    public JsonAdaptedTiming(@JsonProperty("magnitude") Duration magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedTiming(Timing source) {
        this.magnitude = source.getMagnitude();
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     */
    public ExerciseDetail toModelType() {
        return new Timing((java.time.Duration) magnitude);
    }

}
