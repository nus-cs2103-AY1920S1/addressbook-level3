package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.note.MultipleSortByCond;
import seedu.address.model.note.Note;
import seedu.address.model.note.UniqueNoteList;




/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class NoteBook implements ReadOnlyNoteBook {

    private final UniqueNoteList notes;
    private MultipleSortByCond sortByConds;

    public NoteBook() {
        this.sortByConds = new MultipleSortByCond(new String[]{"DateModified"});
        notes = new UniqueNoteList();
    }

    public NoteBook(MultipleSortByCond sortByCond) {
        this.sortByConds = sortByCond;
        notes = new UniqueNoteList();
    }

    public NoteBook(UniqueNoteList notes, MultipleSortByCond sortByCond) {
        this.notes = notes;
        this.sortByConds = sortByCond;
    }
    /**
     * Creates an AddressBook using the Notes in the {@code toBeCopied}
     */
    public NoteBook(ReadOnlyNoteBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyNoteBook newData) {
        requireNonNull(newData);
        setNotes(newData.getNoteList());
        setSortByCond(newData.getSortByConds());
    }

    /**
     * Sorts the notes of the existing data according using (@code SortByConds).
     */
    public void sortNotes() {
        notes.sortNotes(sortByConds);

    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedNoteBook // instanceof handles nulls
                && notes.equals(((VersionedNoteBook) other).getNotes()));
    }
    @Override
    public int hashCode() {
        return notes.hashCode();
    }


    //=========== Notes =============================================================
    //// list overwrite operations

    public MultipleSortByCond getSortByConds() {
        return sortByConds;
    }

    public void setSortByCond(MultipleSortByCond sortByConds) {
        this.sortByConds = sortByConds;
    }
    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }

    public UniqueNoteList getNotes() {
        return notes;
    }

    //// person-level operations

    /**
     * Returns true if a note with the same Title as {@code note} exists in the note book.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a note to the note book.
     * The note must not already exist in the note book.
     */
    public void addNote (Note n) {
        notes.add(n);
    }

    /**
     * Replaces the given note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the note book.
     * The note identity of {@code editedNote} must not be the same as another existing note in the note book.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);

        notes.setNote(target, editedNote);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeNote(Note key) {
        notes.remove(key);
    }

    /**
     * Gets the Index of the note in the note list.
     */
    public Index getNoteIndex(Note note) {
        return notes.getNoteIndex(note);
    }

    //// util methods

    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList() + " notes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Note> getNoteList() {
        return notes.asUnmodifiableObservableList();
    }



}
