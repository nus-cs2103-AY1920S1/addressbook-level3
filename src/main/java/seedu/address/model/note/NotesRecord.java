package seedu.address.model.note;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the notes-record level
 * Notes with same identity are not allowed as compared by {@code Note#isSameNote(Note)}.
 */
public class NotesRecord implements ReadOnlyNotesRecord {

    private final UniqueNotesList notes;
    {
        notes = new UniqueNotesList();
    }

    public NotesRecord() {}

    /**
     * Creates an NotesRecord using the Notes in the {@code toBeCopied}
     */
    public NotesRecord(ReadOnlyNotesRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    /**
     * Replaces the contents of the Notes list with {@code notes}.
     * {@code notes} must not contain duplicate Note as compared by {@code Note#isSameNote(Note)}.
     */
    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }

    /**
     * Resets the existing data of this {@code NotesRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyNotesRecord newData) {
        requireNonNull(newData);
        setNotes(newData.getNotesList());
    }

    //// Notes-level operations
    /**
     * Returns true if a Note with the same identity as {@code note}
     * exists in the notes record.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a Note to the notes record.
     * A note with the with the same identity as compared by
     * {@code Note#isSameNote(Note)} must not already exist in the notes record.
     */
    public void addNote(Note n) {
        notes.add(n);
    }

    /**
     * Replaces the given Note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the notes record.
     * The Note identity of {@code editedNote} must not have the same identity as another
     * existing Note in the notes record as compared by {@code Note#isSameNote(Note)}.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);
        notes.setNote(target, editedNote);
    }

    /**
     * Removes {@code key} from this {@code NotesRecord}.
     * {@code key} must exist in the notes record.
     */
    public void removeNote(Note key) {
        notes.remove(key);
    }

    /**
     * Sorts the notes record using the Comparator provided.
     * @param noteComparator the Comparator for comparing Note objects.
     */
    public void sortNotes(Comparator<Note> noteComparator) {
        List<Note> notesToSort = new ArrayList<>(getNotesList());
        notesToSort.sort(noteComparator);
        setNotes(notesToSort);
    }

    //// util methods
    @Override
    public String toString() {
        return "You have " + notes.asUnmodifiableObservableList().size()
                + " Notes\n"
                + notes;
    }

    @Override
    public ObservableList<Note> getNotesList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotesRecord // instanceof handles nulls
                && notes.equals(((NotesRecord) other).notes));
    }

    @Override
    public int hashCode() {
        return notes.hashCode();
    }

}
