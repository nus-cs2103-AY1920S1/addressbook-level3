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

    private static final Note note1 = new NoteBuilder().withNote("Sunday Morning")
            .withDesc("Buy books").withPriority(Priority.MEDIUM).build();
    private static final Note note2 = new NoteBuilder().withNote("Tuesday Morning")
            .withDesc("Cook").withPriority(Priority.MEDIUM).build();
    private static final Note note3 = new NoteBuilder().withNote("Monday")
            .withDesc("Grade papers").withPriority(Priority.MEDIUM).build();
    private static final Note note4 = new NoteBuilder().withNote("Tonight")
            .withDesc("Give consultation").withPriority(Priority.MEDIUM).build();
    private static final Note note5 = new NoteBuilder().withNote("Morning")
            .withDesc("Order food").withPriority(Priority.MEDIUM).build();
    private static final Note note6 = new NoteBuilder().withNote("Conference")
            .withDesc("Return paper").withPriority(Priority.MEDIUM).build();
    private static final Note note7 = new NoteBuilder().withNote("Family matter")
            .withDesc("Discuss").withPriority(Priority.MEDIUM).build();

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
        return new ArrayList<>(Arrays.asList(note1, note2, note3, note4, note5, note6, note7));
    }
}
