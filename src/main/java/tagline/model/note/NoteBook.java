package tagline.model.note;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import tagline.model.tag.Tag;

/**
 * Wraps all data at the note book level
 * Duplicates are not allowed (by .isSameNote comparison)
 */
public class NoteBook implements ReadOnlyNoteBook {

    private final UniqueNoteList notes;

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
    }

    /**
     * Creates an NoteBook using the Notes in the {@code toBeCopied}
     */
    public NoteBook(ReadOnlyNoteBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the note list with {@code notes}.
     * {@code notes} must not contain duplicate notes.
     */
    public void setNotes(List<Note> notes) {
        this.notes.setNotes(notes);
    }

    /**
     * Resets the existing data of this {@code NoteBook} with {@code newData}.
     */
    public void resetData(ReadOnlyNoteBook newData) {
        requireNonNull(newData);

        setNotes(newData.getNoteList());
    }

    //// note-level operations

    /**
     * Returns true if a note with the same identity as {@code note} exists in the address book.
     */
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return notes.containsNote(note);
    }

    /**
     * Adds a note to the address book.
     * The note must not already exist in the address book.
     */
    public void addNote(Note p) {
        notes.addNote(p);
    }

    /**
     * Finds a {@code Note} based on the {@code noteId}.
     *
     * @return Optional object if corresponding note is found, empty otherwise
     */
    public Optional<Note> findNote(NoteId noteId) {
        return notes.findNote(noteId);
    }

    /**
     * Tags a note inside the address book.
     */
    public void tagNote(Note note, Tag tag) {
        Optional<Note> foundNote = findNote(note.getNoteId());

        assert (foundNote.isPresent()) : "Tagging a non-existing note.";

        foundNote.get().addTag(tag);
    }

    /**
     * Untags a note inside the address book.
     */
    public void untagNote(Note note, Tag tag) {
        Optional<Note> foundNote = findNote(note.getNoteId());

        assert (foundNote.isPresent()) : "Untagging a non-existing note.";

        foundNote.get().removeTag(tag);
    }

    /**
     * Replaces the given note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the address book.
     * The note identity of {@code editedNote} must not be the same as another existing note in the address book.
     */
    public void setNote(Note target, Note editedNote) {
        requireNonNull(editedNote);

        notes.setNote(target, editedNote);
    }

    /**
     * Removes {@code key} from this {@code NoteBook}.
     * {@code key} must exist in the address book.
     */
    public void removeNote(Note key) {
        notes.removeNote(key);
    }

    //// util methods

    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList().size() + " notes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Note> getNoteList() {
        return notes.asUnmodifiableObservableList();
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
}
