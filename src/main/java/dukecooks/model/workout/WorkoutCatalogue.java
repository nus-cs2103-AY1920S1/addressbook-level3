package dukecooks.model.workout;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the workout catalogue level
 * Duplicates are not allowed (by .isSameWorkout comparison)
 */
public class WorkoutCatalogue implements ReadOnlyWorkoutCatalogue {

    private final UniqueWorkoutList workouts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        workouts = new UniqueWorkoutList();
    }

    public WorkoutCatalogue() {}

    /**
     * Creates a Workout Catalogue using the Workouts in the {@code toBeCopied}
     */
    public WorkoutCatalogue(ReadOnlyWorkoutCatalogue toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setWorkout(List<Workout> workouts) {
        this.workouts.setWorkouts(workouts);
    }

    /**
     * Replaces the given exercise {@code target} in the list with {@code editedWorkout}.
     * {@code target} must exist in the WorkoutPlanner.
     * The exercise identity of {@code editedWorkout}
     * must not be the same as another existing person in Workout Planner.
     */
    public void setWorkout(Workout target, Workout editedWorkout) {
        requireNonNull(editedWorkout);

        workouts.setWorkout(target, editedWorkout);
    }

    /**
     * Resets the existing data of this {@code WorkoutCatalogue} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutCatalogue newData) {
        requireNonNull(newData);

        setWorkout(newData.getWorkoutList());
    }

    //// workout-level operations

    /**
     * Returns true if a Workout with the same identity as {@code workout}
     * exists in the Workout Planner.
     */
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return workouts.contains(workout);
    }

    /**
     * Adds a workout to Workout Planner.
     * The workout must not already exist in the Workout Planner.
     */
    public void addWorkout(Workout p) {
        workouts.add(p);
    }

    /**
     * Removes {@code key} from this {@code WorkoutPLanner}.
     * {@code key} must exist in the Workout Planner.
     */
    public void removeWorkout(Workout key) {
        workouts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return workouts.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Workout> getWorkoutList() {
        return workouts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutCatalogue // instanceof handles nulls
                && workouts.equals(((WorkoutCatalogue) other).workouts));
    }

    @Override
    public int hashCode() {
        return workouts.hashCode();
    }

}
