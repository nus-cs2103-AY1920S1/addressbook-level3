package dukecooks.storage.workout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.WorkoutCatalogue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Workout Catalogue that is serializable to JSON format.
 */

@JsonRootName(value = "workoutCatalogue")
public class JsonSerializableWorkoutCatalogue {

    public static final String MESSAGE_DUPLICATE_WORKOUT = "Workouts list contains duplicate workout(s).";

    private final List<JsonAdaptedWorkout> workouts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWorkoutCatalogue} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWorkoutCatalogue(@JsonProperty("workouts") List<JsonAdaptedWorkout> workouts) {
        this.workouts.addAll(workouts);
    }

    /**
     * Converts a given {@code ReadOnlyWorkoutCatalogue} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWorkoutCatalogue}.
     */
    public JsonSerializableWorkoutCatalogue(ReadOnlyWorkoutCatalogue source) {
        workouts.addAll(source.getWorkoutList().stream().map(JsonAdaptedWorkout::new).collect(Collectors.toList()));
    }

    /**
     *  Converts this Workout Catalogue into the model's {@code WorkoutCatalogue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WorkoutCatalogue toModelType() throws IllegalValueException {
        WorkoutCatalogue workoutCatalogue = new WorkoutCatalogue();
        for (JsonAdaptedWorkout jsonAdaptedWorkout : workouts) {
            Workout workout = jsonAdaptedWorkout.toModelType();
            if (workoutCatalogue.hasWorkout(workout)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORKOUT);
            }
            workoutCatalogue.addWorkout(workout);
        }
        return workoutCatalogue;
    }
}

