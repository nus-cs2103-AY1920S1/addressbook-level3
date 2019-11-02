package dukecooks.model.workout;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import dukecooks.model.workout.exceptions.DuplicateWorkoutException;
import dukecooks.model.workout.exceptions.WorkoutNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of workouts that enforces uniqueness between its elements and does not allow nulls.
 * A workout is considered unique by comparing using {@code Workout#isSameWorkout(Workout)}. As such, adding
 * and updating of workouts uses Workout#isSameWorkout(Workout) for equality so as to ensure that
 * the workout being added or updated is unique in terms of identity in the UniqueWorkoutList. However, the
 * removal of a workout uses Workout#equals(Object) so  as to ensure that the workout with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Workout#isSameWorkout(Workout)
 */
public class UniqueWorkoutList implements Iterable<Workout> {


    private final ObservableList<Workout> internalList = FXCollections.observableArrayList();
    private final ObservableList<Workout> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent workout as the given argument.
     */
    public boolean contains(Workout toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWorkout);
    }

    /**
     * Adds a workout to the list.
     * The workout must not already exist in the list.
     */
    public void add(Workout toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWorkoutException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the workout {@code target} in the list with {@code editedWorkout}.
     * {@code target} must exist in the list.
     * The workout identity of {@code editedWorkout} must not be the same as another existing workout in the list.
     */
    public void setWorkout(Workout target, Workout editedWorkout) {
        requireAllNonNull(target, editedWorkout);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new WorkoutNotFoundException();
        }

        if (!target.isSameWorkout(editedWorkout) && contains(editedWorkout)) {
            throw new DuplicateWorkoutException();
        }

        internalList.set(index, editedWorkout);
    }

    /**
     * Removes the equivalent workout from the list.
     * The person must exist in the list.
     */
    public void remove(Workout toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WorkoutNotFoundException();
        }
    }

    public void setWorkouts(UniqueWorkoutList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code workouts}.
     * {@code workouts} must not contain duplicate workouts.
     */
    public void setWorkouts(List<Workout> workouts) {
        requireAllNonNull(workouts);
        if (!workoutsAreUnique(workouts)) {
            throw new DuplicateWorkoutException();
        }

        internalList.setAll(workouts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Workout> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Workout> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWorkoutList // instanceof handles nulls
                && internalList.equals(((UniqueWorkoutList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean workoutsAreUnique(List<Workout> workouts) {
        for (int i = 0; i < workouts.size() - 1; i++) {
            for (int j = i + 1; j < workouts.size(); j++) {
                if (workouts.get(i).isSameWorkout(workouts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
