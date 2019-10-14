package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.Repetitions;

public class JsonAdaptedRepetitions<Integer> extends JsonAdaptedExerciseDetail {

    /**
     * Constructs a {@code JsonAdaptedRepetitions} with the given {@code magnitude}
     * and {@code unit}.
     */
    @JsonCreator
    public JsonAdaptedRepetitions(int magnitude){
        super(magnitude);
    }

    /**
     * Converts a given {@code Weight} into this class for Jackson use.
     * @param source
     */
    public JsonAdaptedRepetitions(Repetitions source) {
        super(source);
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code ExerciseDetail} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public ExerciseDetail toModelType() {
        return new Repetitions((java.lang.Integer) super.getMagnitude());
    }

}
