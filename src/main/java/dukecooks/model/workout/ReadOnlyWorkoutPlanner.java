package dukecooks.model.workout;

import dukecooks.model.workout.exercise.components.Exercise;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Workout Planner
 */
public interface ReadOnlyWorkoutPlanner {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Exercise> getExerciseList();

}
