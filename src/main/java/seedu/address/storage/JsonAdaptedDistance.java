package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.WorkoutPlannerParserUtil;
import seedu.address.model.details.Distance;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.unit.DistanceUnit;

/**
 * Jackson-friendly version of {@link Distance}.
 */

public class JsonAdaptedDistance<Float> extends JsonAdaptedExerciseDetail {

    private final String unit;

    /**
     * Constructs a {@code JsonAdaptedDistance} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedDistance(@JsonProperty("magnitude") float magnitude,
                               @JsonProperty("unit") DistanceUnit unit) {
        this.magnitude = magnitude;
        this.unit = unit.toJson();
    }

    /**
     * Converts a given {@code Distance} into this class for Jackson use.
     */
    public JsonAdaptedDistance(Distance source) {
        this.magnitude = source.getMagnitude();
        this.unit = source.getUnit().toJson();
    }

    /**
     * Configures the class type.
     *
     */

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")


    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetails} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ExerciseDetail toModelType() throws IllegalValueException {
        DistanceUnit modelUnit = WorkoutPlannerParserUtil.parseDistanceUnit(unit);
        return new Distance((java.lang.Float) magnitude, modelUnit);
    }
}
