package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.components.UniqueExerciseList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class WorkoutPlanner implements ReadOnlyWorkoutPlanner {

    private final UniqueExerciseList exercises;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        exercises = new UniqueExerciseList();
    }

    public WorkoutPlanner() {}

    /**
     * Creates a Workout Planner using the Exercises in the {@code toBeCopied}
     */
    public WorkoutPlanner(ReadOnlyWorkoutPlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setExercise(List<Exercise> exercises) {
        this.exercises.setExercises(exercises);
    }

    /**
     * Replaces the given exercise {@code target} in the list with {@code editedExercise}.
     * {@code target} must exist in the WorkoutPlanner.
     * The exercise identity of {@code editedExercise}
     * must not be the same as another existing person in Workout Planner.
     */
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireNonNull(editedExercise);

        exercises.setPerson(target, editedExercise);
    }

    /**
     * Resets the existing data of this {@code WorkoutPLanner} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutPlanner newData) {
        requireNonNull(newData);

        setExercise(newData.getExerciseList());
    }

    //// person-level operations

    /**
     * Returns true if an Exercise with the same identity as {@code exercise}
     * exists in the Workout Planner.
     */
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exercises.contains(exercise);
    }

    /**
     * Adds an exercise to Workout Planner.
     * The exercise must not already exist in the Workout Planner.
     */
    public void addExercise(Exercise p) {
        exercises.add(p);
    }

    /**
     * Removes {@code key} from this {@code WorkoutPLanner}.
     * {@code key} must exist in the Workout Planner.
     */
    public void removePerson(Exercise key) {
        exercises.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return exercises.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutPlanner // instanceof handles nulls
                && exercises.equals(((WorkoutPlanner) other).exercises));
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }
}
