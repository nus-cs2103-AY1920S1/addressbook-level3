package seedu.address.testutil;

import seedu.address.model.NoteBook;
import seedu.address.model.note.Note;

/**
 * A utility class to help with building Notebook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new NoteBookBuilder().withNote("secretDoc","secretDiary").build();}
 */
public class NoteBookBuilder {

    private NoteBook noteBook;

    public NoteBookBuilder() {
        noteBook = new NoteBook();
    }

    public NoteBookBuilder(NoteBook noteBook) {
        this.noteBook = noteBook;
    }

    /**
     * Adds a new {@code Note} to the {@code NoteBook} that we are building.
     */
    public NoteBookBuilder withNote(Note note) {
        noteBook.addNote(note);
        return this;
    }

    public NoteBook build() {
        return noteBook;
    }
}
