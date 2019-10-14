package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exercise.MuscleType;

public class JsonAdaptedMuscleType {

    private final String muscleType;

    /**
     * Constructs a {@code JsonAdaptedMuscleType} with the given {@code muscleType}.
     */
    @JsonCreator
    public JsonAdaptedMuscleType(String muscleType) {
        this.muscleType = muscleType;
    }

    /**
     * Converts a given {@code MuscleType} into this class for Jackson use.
     */
    public JsonAdaptedMuscleType (MuscleType source) {
        muscleType = source.getMuscleType();
    }

    @JsonValue
    public String getMuscleType() {
        return muscleType;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code MuscleType} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted MuscleType.
     */
    public MuscleType toModelType() throws IllegalValueException{
        if (!MuscleType.isValidMuscleType(muscleType)) {
            throw new IllegalValueException(MuscleType.MESSAGE_CONSTRAINTS);
        }
        return new MuscleType(muscleType);
    }
}
