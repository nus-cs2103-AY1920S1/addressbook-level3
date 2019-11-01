package dukecooks.model.workout;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Workout Catalogue.
 */
public interface ReadOnlyWorkoutCatalogue {

    /**
     * Returns an unmodifiable view of the workouts list.
     * This list will not contain any duplicate workouts.
     */
    ObservableList<Workout> getWorkoutList();

}
