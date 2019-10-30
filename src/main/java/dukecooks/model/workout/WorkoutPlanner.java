package dukecooks.model.workout;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.UniqueExerciseList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class WorkoutPlanner implements ReadOnlyWorkoutPlanner {

    private final UniqueExerciseList exercises;
    private final UniqueWorkoutList workouts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        exercises = new UniqueExerciseList();
        workouts = new UniqueWorkoutList();
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
     * Replaces the contents of the person list with {@code exercises}.
     * {@code exercises} must not contain duplicate exercises.
     */
    public void setExercise(List<Exercise> exercises) {
        this.exercises.setExercises(exercises);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setWorkout(List<Workout> workouts) {
        this.workouts.setWorkouts(workouts);
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
     * Resets the existing data of this {@code WorkoutPlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutPlanner newData) {
        requireNonNull(newData);

        setExercise(newData.getExerciseList());
        setWorkout(newData.getWorkoutList());
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
        exercises.add(p);
    }

    /**
     * Removes {@code key} from this {@code WorkoutPLanner}.
     * {@code key} must exist in the Workout Planner.
     */
    public void removeExercise(Exercise key) {
        exercises.remove(key);
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
        return exercises.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Workout> getWorkoutList() { return  workouts.asUnmodifiableObservableList(); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutPlanner // instanceof handles nulls
                && exercises.equals(((WorkoutPlanner) other).exercises))
                && workouts.equals(((WorkoutPlanner) other).exercises);
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }
}
