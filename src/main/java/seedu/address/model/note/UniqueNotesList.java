package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.exceptions.DuplicateNotesExceptions;
import seedu.address.model.note.exceptions.NotesNotFoundException;

/**
 * A list of notes that enforces uniqueness between its elements and does not allow nulls.
 * A note is considered unique by comparing using {@code Note#isSameNote(Note)}. As such, adding and updating of
 * notes uses Note#isSameNote(Note) for equality so as to ensure that the note being added or updated is
 * unique in terms of identity in the UniqueNotesList. However, the removal of a person uses Note#equals(Object) so
 * as to ensure that the note with exactly the same fields will be removed.
 *
 */
public class UniqueNotesList implements Iterable<Notes> {
    private final ObservableList<Notes> internalList = FXCollections.observableArrayList();
    private final ObservableList<Notes> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Notes toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameNote);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Notes toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateNotesExceptions();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setNote(Notes target, Notes editedNote) {
        requireAllNonNull(target, editedNote);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new NotesNotFoundException();
        }

        if (!target.isSameNote(editedNote) && contains(editedNote)) {
            throw new DuplicateNotesExceptions();
        }

        internalList.set(index, editedNote);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Notes toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new NotesNotFoundException();
        }
    }

    public void setNotes(UniqueNotesList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setNotes(List<Notes> notes) {
        requireAllNonNull(notes);
        if (!notesAreUnique(notes)) {
            throw new DuplicateNotesExceptions();
        }

        internalList.setAll(notes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Notes> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Notes> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueNotesList // instanceof handles nulls
                && internalList.equals(((UniqueNotesList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean notesAreUnique(List<Notes> notes) {
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
