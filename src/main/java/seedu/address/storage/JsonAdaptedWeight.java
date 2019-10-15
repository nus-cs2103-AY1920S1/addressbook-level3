package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.Weight;
import seedu.address.model.details.unit.WeightUnit;

public class JsonAdaptedWeight<Float> extends JsonAdaptedExerciseDetail {

    private final String unit;
    private static final String DETAILTYPE = "Weight";

    /**
     * Constructs a {@code JsonAdaptedWeight} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedWeight(@JsonProperty("magnitude") float magnitude,
                             @JsonProperty("unit") WeightUnit unit){
        this.type = DETAILTYPE;
        this.magnitude = magnitude;
        this.unit = unit.toJson();
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     */
    public JsonAdaptedWeight(Weight source) {
        this.type = DETAILTYPE;
        this.magnitude = source.getMagnitude();
        this.unit = source.getUnit().toJson();
    }

//    @JsonValue
//    public String getUnit() {
//        return unit;
//    }
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ExerciseDetail toModelType() throws IllegalValueException{
//        if (!ExerciseDetail.isValidExerciseDetail(unit)) {
//            throw new IllegalValueException(ExerciseDetail.MESSAGE_CONSTRAINTS);
//        }
        WeightUnit modelUnit = ParserUtil.parseWeightUnit(unit);
        return new Weight((java.lang.Float) magnitude, modelUnit);
    }
}
