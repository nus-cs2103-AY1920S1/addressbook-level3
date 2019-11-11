package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TutorAid;
import seedu.address.model.note.Notes;

/**
 * A utility class containing a list of {@code Notes} objects to be used in tests.
 */
public class TypicalNotes {
    public static final Notes NOTES_CS2103T = new NotesBuilder().withModuleCode("CS2103T")
            .withClassType("tut").withContent("check report submission").build();

    public static final Notes NOTES_CS3235 = new NotesBuilder().withModuleCode("CS3235")
            .withClassType("lab").withContent("Print poster with size A5").build();


    private TypicalNotes() {} // prevents instantiation

    /**
     * Returns an {@code TutorAid} with all the typical persons.
     */
    public static TutorAid getTypicalTutorAid() {
        TutorAid ab = new TutorAid();
        for (Notes note : getTypicalNotes()) {
            ab.addNotes(note);
        }
        return ab;
    }

    public static List<Notes> getTypicalNotes() {
        return new ArrayList<>(Arrays.asList(NOTES_CS2103T, NOTES_CS3235));
    }
}
