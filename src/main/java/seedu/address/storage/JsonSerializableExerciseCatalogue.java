package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.exercise.Exercise;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "exercisecatalogue")
class JsonSerializableExerciseCatalogue {

    public static final String MESSAGE_DUPLICATE_PERSON = "Exercises list contains duplicate person(s).";

    private final List<JsonAdaptedExercise> exercises = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExerciseCatalogue} with the given persons.
     */
    @JsonCreator
    public JsonSerializableExerciseCatalogue(@JsonProperty("exercises") List<JsonAdaptedExercise> exercises) {
        this.exercises.addAll(exercises);
    }

    /**
    * Converts a given {@code ReadOnlyDukeCooks} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableExerciseCatalogue}.
    */
    public JsonSerializableExerciseCatalogue(ReadOnlyDukeCooks source) {
        exercises.addAll(source.getExerciseList().stream().map(JsonAdaptedExercise::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code DukeCooks} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public DukeCooks toModelType() throws IllegalValueException {
        DukeCooks dukeCooks = new DukeCooks();
        for (JsonAdaptedExercise jsonAdaptedExercise : exercises) {
            Exercise exercise = jsonAdaptedExercise.toModelType();
            if (dukeCooks.hasExercise(exercise)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            dukeCooks.addExercise(exercise);
        }
        return dukeCooks;
    }
}
