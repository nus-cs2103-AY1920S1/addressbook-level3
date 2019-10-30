package dukecooks.storage.workout.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.ReadOnlyWorkoutPlanner;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.WorkoutPlanner;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.storage.workout.JsonAdaptedWorkout;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "workoutPlanner")
class JsonSerializableWorkoutPlanner {

    public static final String MESSAGE_DUPLICATE_EXERCISE = "Exercises list contains duplicate exercise(s).";
    public static final String MESSAGE_DUPLICATE_WORKOUT = "Workouts list contains duplicate workout(s).";

    private final List<JsonAdaptedExercise> exercises = new ArrayList<>();
    private final List<JsonAdaptedWorkout> workouts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWorkoutPlanner} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWorkoutPlanner(@JsonProperty("exercises") List<JsonAdaptedExercise> exercises,
                                          @JsonProperty("workouts") List<JsonAdaptedWorkout> workouts) {
        this.exercises.addAll(exercises);
        this.workouts.addAll(workouts);
    }

    /**
    * Converts a given {@code ReadOnlyDukeCooks} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableExerciseCatalogue}.
    */
    public JsonSerializableWorkoutPlanner(ReadOnlyWorkoutPlanner source) {
        exercises.addAll(source.getExerciseList().stream().map(JsonAdaptedExercise::new).collect(Collectors.toList()));
        workouts.addAll(source.getWorkoutList().stream().map(JsonAdaptedWorkout::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code DukeCooks} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public WorkoutPlanner toModelType() throws IllegalValueException {
        WorkoutPlanner workoutPlanner = new WorkoutPlanner();
        for (JsonAdaptedExercise jsonAdaptedExercise : exercises) {
            Exercise exercise = jsonAdaptedExercise.toModelType();
            if (workoutPlanner.hasExercise(exercise)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXERCISE);
            }
            workoutPlanner.addExercise(exercise);
        }
        for (JsonAdaptedWorkout jsonAdaptedWorkout : workouts) {
            Workout workout = jsonAdaptedWorkout.toModelType();
            if (workoutPlanner.hasWorkout(workout)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORKOUT);
            }
            workoutPlanner.addWorkout(workout);
        }
        return workoutPlanner;
    }
}
