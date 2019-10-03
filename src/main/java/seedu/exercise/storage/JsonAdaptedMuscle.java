package seedu.exercise.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.tag.Muscle;

/**
 * Jackson-friendly version of {@link Muscle}.
 */
class JsonAdaptedMuscle {

    private final String muscle;

    /**
     * Constructs a {@code JsonAdaptedMuscle} with the given {@code muscle}.
     */
    @JsonCreator
    public JsonAdaptedMuscle(String muscle) {
        this.muscle = muscle;
    }

    /**
     * Converts a given {@code Muscle} into this class for Jackson use.
     */
    public JsonAdaptedMuscle(Muscle source) {
        muscle = source.muscleName;
    }

    @JsonValue
    public String getMuscle() {
        return muscle;
    }

    /**
     * Converts this Jackson-friendly adapted muscle object into the model's {@code Muscle} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted muscle.
     */
    public Muscle toModelType() throws IllegalValueException {
        if (!Muscle.isValidMuscleName(muscle)) {
            throw new IllegalValueException(Muscle.MESSAGE_CONSTRAINTS);
        }
        return new Muscle(muscle);
    }

}
