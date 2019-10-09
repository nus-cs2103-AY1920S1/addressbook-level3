package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateNoteException;
import seedu.address.model.person.exceptions.NoteNotFoundException;

/**
 * A list of lecture notes that enforces uniqueness between their titles, using {@code Person#isSameNote(Person)}
 * for adding/updating and {@code Person#equals(Object)} for deleting. Supports a minimal set of list operations.
 *
 * @see Person#isSameNote(Person)
 */
public class UniqueNoteList implements Iterable<Person> {
    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains a lecture note sharing title with the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameNote);
    }

    /**
     * Adds a lecture note to the list. The title must be different from all existing ones.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateNoteException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the existing lecture note {@code target} in the list with {@code edited}.
     * The new title must be different from all existing ones.
     */
    public void setNote(Person target, Person edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new NoteNotFoundException();
        }

        if (!target.isSameNote(edited) && contains(edited)) {
            throw new DuplicateNoteException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent, existing lecture note from the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new NoteNotFoundException();
        }
    }

    public void setPersons(UniqueNoteList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code notes} containing no duplicates.
     */
    public void setPersons(List<Person> notes) {
        requireAllNonNull(notes);
        if (!notesAreUnique(notes)) {
            throw new DuplicateNoteException();
        }

        internalList.setAll(notes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueNoteList // instanceof handles nulls
                        && internalList.equals(((UniqueNoteList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code note} contains only unique notes.
     */
    private boolean notesAreUnique(List<Person> notes) {
        for (int i = 0; i < notes.size() - 1; i++) {
            for (int j = i + 1; j < notes.size(); j++) {
                if (notes.get(i).isSameNote(notes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
