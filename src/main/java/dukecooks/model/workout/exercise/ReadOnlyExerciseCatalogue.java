package dukecooks.model.workout.exercise;

import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.components.Exercise;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Exercise Catalogue.
 */
public interface ReadOnlyExerciseCatalogue {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<Exercise> getExerciseList();

}
