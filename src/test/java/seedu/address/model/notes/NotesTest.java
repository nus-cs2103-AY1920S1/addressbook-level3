package seedu.address.model.notes;

import org.junit.jupiter.api.Test;
import seedu.address.model.note.Notes;
import seedu.address.testutil.NotesBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_NOTE_CALVIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NOTES_LAB;
import static seedu.address.testutil.TypicalNotes.NOTES_CS2103T;
import static seedu.address.testutil.TypicalNotes.NOTES_CS3235;

public class NotesTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(NOTES_CS2103T.isSameNote(NOTES_CS2103T));

        // null -> returns false
        assertFalse(NOTES_CS2103T.isSameNote(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Notes copyCS2103T = new NotesBuilder(NOTES_CS2103T).build();
        assertTrue(NOTES_CS2103T.equals(copyCS2103T));

        // same object -> returns true
        assertTrue(NOTES_CS2103T.equals(NOTES_CS2103T));

        // null -> returns false
        assertFalse(NOTES_CS2103T.equals(null));

        // different module code -> returns false
        assertFalse(NOTES_CS2103T.equals(2));

        // different modules code -> returns false
        assertFalse(NOTES_CS2103T.equals(NOTES_CS3235));

        // different modulecode -> returns false
        Notes editedCS3235 = new NotesBuilder(NOTES_CS3235).withModuleCode(VALID_CLASSID_NOTE_CALVIN).build();
        assertFalse(NOTES_CS3235.equals(editedCS3235));

        // different type -> returns false
        Notes editedCS1231 = new NotesBuilder(NOTES_CS2103T).withClassType(VALID_TYPE_NOTES_LAB).build();
        assertFalse(NOTES_CS2103T.equals(editedCS1231));
    }
}
