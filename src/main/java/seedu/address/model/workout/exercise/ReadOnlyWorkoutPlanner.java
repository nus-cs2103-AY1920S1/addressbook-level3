package seedu.address.model.workout.exercise;

import javafx.collections.ObservableList;
import seedu.address.model.workout.exercise.components.Exercise;

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
