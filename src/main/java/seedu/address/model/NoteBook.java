package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.note.Note;
import seedu.address.model.note.SortByCond;
import seedu.address.model.note.UniqueNoteList;




/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class NoteBook implements ReadOnlyNoteBook {

    private final UniqueNoteList notes;
    private SortByCond sortByCond;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        notes = new UniqueNoteList();
    }

    public NoteBook() {
        this.sortByCond = new SortByCond();
    }

    public NoteBook(SortByCond sortByCond) {
        this.sortByCond = sortByCond;
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
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
        setSortByCond(newData.getSortByCond());
    }

    /**
     * Sorts the notes of the existing data according using (@code SortByCond).
     */
    public void sortNotes() {
        notes.sortNotes(sortByCond);

    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NoteBook // instanceof handles nulls
                && notes.equals(((NoteBook) other).notes));
    }
    @Override
    public int hashCode() {
        return notes.hashCode();
    }


    //=========== Notes =============================================================
    //// list overwrite operations

    public SortByCond getSortByCond() {
        return sortByCond;
    }

    public void setSortByCond(SortByCond sortByCond) {
        this.sortByCond = sortByCond;
    }
    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
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
