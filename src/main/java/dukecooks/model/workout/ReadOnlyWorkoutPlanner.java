package dukecooks.model.workout;

import dukecooks.model.workout.exercise.components.Exercise;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Workout Planner
 */
public interface ReadOnlyWorkoutPlanner {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<Exercise> getExerciseList();

    /**
     * Returns an unmodifiable view of the workouts list.
     * This list will not contain any duplicate workouts.
     */
    ObservableList<Workout> getWorkoutList();

}
