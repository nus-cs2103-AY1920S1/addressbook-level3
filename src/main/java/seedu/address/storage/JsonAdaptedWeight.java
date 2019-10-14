package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.Weight;
import seedu.address.model.details.unit.WeightUnit;

public class JsonAdaptedWeight<Float> extends JsonAdaptedExerciseDetail {

    private final String unit;

    /**
     * Constructs a {@code JsonAdaptedWeight} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedWeight(float magnitude, WeightUnit unit){
        super(magnitude);
        this.unit = unit.toString();
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     */
    public JsonAdaptedWeight(Weight source) {
        super(source);
        this.unit = source.getUnit().toString();
    }

    @JsonValue
    public String getUnit() {
        return unit;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ExerciseDetail toModelType() throws IllegalValueException{
        if (!ExerciseDetail.isValidExerciseDetail(unit)) {
            throw new IllegalValueException(ExerciseDetail.MESSAGE_CONSTRAINTS);
        }
        WeightUnit modelUnit = ParserUtil.parseWeightUnit(unit);
        return new Weight((java.lang.Float) super.getMagnitude(), modelUnit);
    }
}
