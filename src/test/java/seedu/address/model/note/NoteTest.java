package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.testutil.TypicalAppData.ALICE;
import static seedu.address.testutil.TypicalAppData.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.NoteBuilder;

public class NoteTest {
    @Test
    public void isSameNote() {
        // same object -> returns true
        assertTrue(ALICE.isSameNote(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameNote(null));

        // different title -> returns false
        Note editedAlice = new NoteBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE.isSameNote(editedAlice));

        // only different content -> returns true
        editedAlice = new NoteBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertTrue(ALICE.isSameNote(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Note aliceCopy = new NoteBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different note -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Note editedAlice = new NoteBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new NoteBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
