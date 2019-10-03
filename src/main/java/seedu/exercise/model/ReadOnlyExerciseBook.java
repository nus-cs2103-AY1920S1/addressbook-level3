package seedu.exercise.model;

import javafx.collections.ObservableList;
import seedu.exercise.model.exercise.Exercise;

/**
 * Unmodifiable view of an exercise book
 */
public interface ReadOnlyExerciseBook {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<Exercise> getExerciseList();

}
