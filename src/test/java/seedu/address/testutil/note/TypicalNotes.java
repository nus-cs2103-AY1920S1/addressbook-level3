package seedu.address.testutil.note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.Priority;

/**
 * A utility class containing a list of {@code Note} objects to be used in tests.
 */
public class TypicalNotes {

    public static final Note NOTE1 = new NoteBuilder().withNote("Sunday Morning")
            .withDesc("Buy books").withPriority(Priority.MEDIUM).build();
    public static final Note NOTE2 = new NoteBuilder().withNote("Tuesday Morning")
            .withDesc("Cook").withPriority(Priority.MEDIUM).build();
    public static final Note NOTE3 = new NoteBuilder().withNote("Monday")
            .withDesc("Grade papers").withPriority(Priority.HIGH).build();
    public static final Note NOTE4 = new NoteBuilder().withNote("Tonight")
            .withDesc("Give consultation").withPriority(Priority.UNMARKED).build();

    public static final Note NOT_IN_TYPICAL = new NoteBuilder().withNote("note test title")
            .withDesc("Give consultation").withPriority(Priority.UNMARKED).build();

    private TypicalNotes() {}

    /**
     * Returns an {@code NotesRecord} with all the typical notes.
     */
    public static NotesRecord getTypicalNotesRecord() {
        NotesRecord ab = new NotesRecord();
        for (Note note : getTypicalNotes()) {
            ab.addNote(note);
        }
        return ab;
    }

    public static List<Note> getTypicalNotes() {
        return new ArrayList<>(Arrays.asList(NOTE1, NOTE2, NOTE3, NOTE4));
    }
}
