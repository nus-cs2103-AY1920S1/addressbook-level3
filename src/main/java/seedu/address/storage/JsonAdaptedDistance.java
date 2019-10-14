package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.details.Distance;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.unit.DistanceUnit;

public class JsonAdaptedDistance<Float> extends JsonAdaptedExerciseDetail {

    private final String unit;

    /**
     * Constructs a {@code JsonAdaptedDistance} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedDistance(float magnitude, DistanceUnit unit){
        super(magnitude);
        this.unit = unit.toString();
    }

    /**
     * Converts a given {@code Distance} into this class for Jackson use.
     */
    public JsonAdaptedDistance(Distance source) {
        super(source);
        this.unit = source.getUnit().toString();
    }

    @JsonValue
    public String getUnit() {
        return unit;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetails} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ExerciseDetail toModelType() throws IllegalValueException{
        if (!ExerciseDetail.isValidExerciseDetail(unit)) {
            throw new IllegalValueException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
        DistanceUnit modelUnit = ParserUtil.parseDistanceUnit(unit);
        return new Distance((java.lang.Float) super.getMagnitude(), modelUnit);
    }
}
