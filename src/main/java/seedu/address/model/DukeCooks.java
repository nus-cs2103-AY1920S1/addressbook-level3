package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.UniqueExerciseList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class DukeCooks implements ReadOnlyDukeCooks {

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

    public DukeCooks() {}

    /**
     * Creates a DukeCooks using the Persons in the {@code toBeCopied}
     */
    public DukeCooks(ReadOnlyDukeCooks toBeCopied) {
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
     * Resets the existing data of this {@code DukeCooks} with {@code newData}.
     */
    public void resetData(ReadOnlyDukeCooks newData) {
        requireNonNull(newData);

        setExercise(newData.getExerciseList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in Duke Cooks.
     */
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exercises.contains(exercise);
    }

    /**
     * Adds a person to Duke Cooks.
     * The person must not already exist in Duke Cooks.
     */
    public void addExercise(Exercise p) {
        exercises.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in Duke Cooks.
     * The person identity of {@code editedPerson} must not be the same as another existing person in Duke Cooks.
     */
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireNonNull(editedExercise);

        exercises.setPerson(target, editedExercise);
    }

    /**
     * Removes {@code key} from this {@code DukeCooks}.
     * {@code key} must exist in Duke Cooks.
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
                || (other instanceof DukeCooks // instanceof handles nulls
                && exercises.equals(((DukeCooks) other).exercises));
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }
}
