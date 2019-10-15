package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.Sets;

/**
 * Jackson-friendly version of {@link Sets}.
 */
public class JsonAdaptedSets<Integer> extends JsonAdaptedExerciseDetail {

    /**
     * Constructs a {@code JsonAdaptedRepetitions} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedSets(@JsonProperty("magnitude") int magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedSets(Sets source) {
        this.magnitude = source.getMagnitude();
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     */
    public ExerciseDetail toModelType() {
        return new Sets((java.lang.Integer) magnitude);
    }

}
