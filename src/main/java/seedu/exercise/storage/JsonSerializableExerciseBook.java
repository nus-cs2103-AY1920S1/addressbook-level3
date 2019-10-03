package seedu.exercise.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.exercise.Exercise;

/**
 * An Immutable ExerciseBook that is serializable to JSON format.
 */
@JsonRootName(value = "exercisebook")
class JsonSerializableExerciseBook {

    public static final String MESSAGE_DUPLICATE_EXERCISE = "Exercise list contains duplicate exercise(s).";

    private final List<JsonAdaptedExercise> exercises = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExerciseBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableExerciseBook(@JsonProperty("exercises") List<JsonAdaptedExercise> exercises) {
        this.exercises.addAll(exercises);
    }

    /**
     * Converts a given {@code ReadOnlyExerciseBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExerciseBook}.
     */
    public JsonSerializableExerciseBook(ReadOnlyExerciseBook source) {
        exercises.addAll(source.getExerciseList().stream().map(JsonAdaptedExercise::new).collect(Collectors.toList()));
    }

    /**
     * Converts this exercise book into the model's {@code ExerciseBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExerciseBook toModelType() throws IllegalValueException {
        ExerciseBook exerciseBook = new ExerciseBook();
        for (JsonAdaptedExercise jsonAdaptedExercise : exercises) {
            Exercise exercise = jsonAdaptedExercise.toModelType();
            if (exerciseBook.hasExercise(exercise)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXERCISE);
            }
            exerciseBook.addExercise(exercise);
        }
        return exerciseBook;
    }

}
