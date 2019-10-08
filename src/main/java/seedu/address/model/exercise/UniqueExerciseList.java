package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.exercise.exceptions.DuplicateExerciseException;
import seedu.address.model.exercise.exceptions.ExerciseNotFoundException;

/**
 * A list of exercises that enforces uniqueness between its elements and does not allow nulls.
 * A exercise is considered unique by comparing using {@code Exercise#equals(Exercise)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see Exercise#equals(Object)
 */
public class UniqueExerciseList implements Iterable<Exercise> {
    private final ObservableList<Exercise> internalList = FXCollections.observableArrayList();
    private final ObservableList<Exercise> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent exercise as the given argument.
     */
    public boolean contains(Exercise toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a exercise to the list.
     * The exercise must not already exist in the list.
     */
    public void add(Exercise toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExerciseException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the exercise {@code target} in the list with {@code editedExercise}.
     * {@code target} must exist in the list.
     * The exercise identity of {@code editedExercise} must not be the same as another existing exercise in the list.
     */
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExerciseNotFoundException();
        }

        if (!target.equals(editedExercise) && contains(editedExercise)) {
            throw new DuplicateExerciseException();
        }

        internalList.set(index, editedExercise);
    }

    /**
     * Removes the equivalent exercise from the list.
     * The exercise must exist in the list.
     */
    public void remove(Exercise toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExerciseNotFoundException();
        }
    }

    public void setExercises(seedu.address.model.exercise.UniqueExerciseList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code exercises}.
     * {@code exercises} must not contain duplicate exercises.
     */
    public void setExercises(List<Exercise> exercises) {
        requireAllNonNull(exercises);
        if (!exercisesAreUnique(exercises)) {
            throw new DuplicateExerciseException();
        }

        internalList.setAll(exercises);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Exercise> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Exercise> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.exercise.UniqueExerciseList // instanceof handles nulls
                && internalList.equals(((seedu.address.model.exercise.UniqueExerciseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code exercises} contains only unique exercises.
     */
    private boolean exercisesAreUnique(List<Exercise> exercises) {
        for (int i = 0; i < exercises.size() - 1; i++) {
            for (int j = i + 1; j < exercises.size(); j++) {
                if (exercises.get(i).equals(exercises.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
