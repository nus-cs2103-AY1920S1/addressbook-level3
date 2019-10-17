package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.NoteBook;
import seedu.address.model.note.Note;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalNotes {

    public static final Note SECRETDOC = new NoteBuilder().withTitle("Top Secret Document")
            .withDescription("xyz project")
            .withContent("1 + 1 = 2")
            .withTags("work").build();
    public static final Note SECRETDIARY = new NoteBuilder().withTitle("Diary")
            .withDescription("use for personal diary")
            .withContent("this is my personal life")
            .withTags("personal").build();
    public static final Note SECRETRECORDS = new NoteBuilder().withTitle("Records")
            .withDescription("personal records")
            .withContent("sold 500 teddy bears")
            .withTags("personal").build();


    private TypicalNotes() {} // prevents instantiation

    /**
     * Returns an {@code NoteBook} with all the typical persons.
     */
    public static NoteBook getTypicalNoteBook() {
        NoteBook ab = new NoteBook();
        for (Note note : getTypicalNotes()) {
            ab.addNote(note);
        }
        return ab;
    }

    public static List<Note> getTypicalNotes() {
        return new ArrayList<>(Arrays.asList(SECRETDIARY, SECRETDOC, SECRETRECORDS));
    }

}
