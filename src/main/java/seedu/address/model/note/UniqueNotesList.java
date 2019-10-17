package seedu.address.model.note;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.exceptions.DuplicateNoteException;
import seedu.address.model.note.exceptions.NoteNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of notes that enforces uniqueness between its elements and does not allow nulls.
 * A note is considered unique by comparing using {@code Note#isSameNote(Note)}. As such, adding and updating of
 * notes uses Note#isSameNote(Note) for equality so as to ensure that the note being added or updated is
 * unique in terms of identity in the UniqueNotesList. However, the removal of a note uses Note#equals(Object) so
 * as to ensure that the note with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Note#isSameNote(Note)
 */
public class UniqueNotesList implements Iterable<Note> {

    private final ObservableList<Note> internalList = FXCollections.observableArrayList();
    private final ObservableList<Note> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Note toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameNote);
    }

    /**
     * Adds a note to the list.
     * The note must not already exist in the list.
     */
    public void add(Note toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateNoteException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the list.
     * The note identity of {@code editedNote} must not be the same as another existing note in the list.
     */
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new NoteNotFoundException();
        }

        if (!target.isSameNote(editedNote) && contains(editedNote)) {
            throw new DuplicateNoteException();
        }

        internalList.set(index, editedNote);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Note toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new NoteNotFoundException();
        }
    }

    public void setNotes(UniqueNotesList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code notes}.
     * {@code notes} must not contain duplicate notes.
     */
    public void setNotes(List<Note> notes) {
        requireAllNonNull(notes);
        if (!notesAreUnique(notes)) {
            throw new DuplicateNoteException();
        }
        internalList.setAll(notes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Note> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Note> iterator() {
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
     * Returns true if {@code notes} contains only unique notes.
     */
    private boolean notesAreUnique(List<Note> notes) {
        for (int i = 0; i < notes.size() - 1; i++) {
            for (int j = i + 1; j < notes.size(); j++) {
                if (notes.get(i).isSameNote(notes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Note note : internalList) {
            sb.append(note + "\n");
        }
        return sb.toString();
    }

}
