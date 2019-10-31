package dukecooks.storage.workout.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "exerciseCatalogue")
class JsonSerializableExerciseCatalogue {

    public static final String MESSAGE_DUPLICATE_EXERCISE = "Exercises list contains duplicate exercise(s).";

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
    public JsonSerializableExerciseCatalogue(ReadOnlyExerciseCatalogue source) {
        exercises.addAll(source.getExerciseList().stream().map(JsonAdaptedExercise::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code DukeCooks} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public ExerciseCatalogue toModelType() throws IllegalValueException {
        ExerciseCatalogue workoutPlanner = new ExerciseCatalogue();
        for (JsonAdaptedExercise jsonAdaptedExercise : exercises) {
            Exercise exercise = jsonAdaptedExercise.toModelType();
            if (workoutPlanner.hasExercise(exercise)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXERCISE);
            }
            workoutPlanner.addExercise(exercise);
        }
        return workoutPlanner;
    }
}
