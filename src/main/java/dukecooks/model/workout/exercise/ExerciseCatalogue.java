package dukecooks.model.workout.exercise;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.UniqueExerciseList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the exercise catalogue level
 * Duplicates are not allowed (by .isSameExercise comparison)
 */
public class ExerciseCatalogue implements ReadOnlyExerciseCatalogue {

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

    public ExerciseCatalogue() {}

    /**
     * Creates a Workout Planner using the Exercises in the {@code toBeCopied}
     */
    public ExerciseCatalogue(ReadOnlyExerciseCatalogue toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code exercises}.
     * {@code exercises} must not contain duplicate exercises.
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

        exercises.setExercise(target, editedExercise);
    }

    /**
     * Resets the existing data of this {@code WorkoutPlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyExerciseCatalogue newData) {
        requireNonNull(newData);

        setExercise(newData.getExerciseList());
    }

    //// exercise-level operations

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
        requireNonNull(p);
        exercises.add(p);
    }

    /**
     * Returns an exercise in the List with the ExerciseName specified.
     */
    public Exercise findExercise(ExerciseName name) {
        requireNonNull(name);
        for (Exercise exercise : exercises) {
            if (exercise.getExerciseName().equals(name)) {
                return exercise;
            }
        }
        return null;
    }

    /**
     * Removes {@code key} from this {@code WorkoutPLanner}.
     * {@code key} must exist in the Workout Planner.
     */
    public void removeExercise(Exercise key) {
        exercises.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return exercises.asUnmodifiableObservableList().size() + " exercises";
        // TODO: refine later
    }

    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExerciseCatalogue // instanceof handles nulls
                && exercises.equals(((ExerciseCatalogue) other).exercises));
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }

}
