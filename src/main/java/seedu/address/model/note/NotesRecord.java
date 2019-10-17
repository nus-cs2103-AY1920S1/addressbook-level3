package seedu.address.model.note;

import javafx.collections.ObservableList;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the notes-record level
 * Duplicates are not allowed (by .isSameNote comparison)
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
     * {@code notes} must not contain duplicate Note.
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
     * Returns true if a Note with the same identity as {@code Note} exists in the notes record.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a Note to the notes record.
     * The Note must not already exist in the notes record.
     */
    public void addNote(Note n) {
        notes.add(n);
    }

    /**
     * Replaces the given Note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the notes record.
     * The Note identity of {@code editedNote} must not be the same as another
     * existing Note in the notes record.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);
        notes.setNote(target, editedNote);
    }

    /**
     * Removes {@code key} from this {@code NotesRecord}.
     * {@code key} must exist in the address book.
     */
    public void removeNote(Note key) {
        notes.remove(key);
    }

    //// util methods
    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList().size() + " Notes";
        // TODO: refine later
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
