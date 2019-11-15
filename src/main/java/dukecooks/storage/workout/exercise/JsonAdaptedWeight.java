package dukecooks.storage.workout.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.logic.parser.exercise.WorkoutPlannerParserUtil;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.ExerciseWeight;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;


/**
 * Jackson-friendly version of {@link ExerciseWeight}.
 */

public class JsonAdaptedWeight<Float> extends JsonAdaptedExerciseDetail {

    private final String unit;

    /**
     * Constructs a {@code JsonAdaptedWeight} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedWeight(@JsonProperty("magnitude") float magnitude,
                             @JsonProperty("unit") WeightUnit unit) {
        this.magnitude = magnitude;
        this.unit = unit.toJson();
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     */
    public JsonAdaptedWeight(ExerciseWeight source) {
        this.magnitude = source.getMagnitude();
        this.unit = source.getUnit().toJson();
    }


    /**
     * Configures the class type.
     *
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ExerciseDetail toModelType() throws IllegalValueException {
        WeightUnit modelUnit = WorkoutPlannerParserUtil.parseWeightUnit(unit);
        return new ExerciseWeight((java.lang.Float) magnitude, modelUnit);
    }
}
