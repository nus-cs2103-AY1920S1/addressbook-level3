package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.testutil.TypicalAppData.ALICE;
import static seedu.address.testutil.TypicalAppData.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.NoteBuilder;

class NoteTest {
    @Test
    void isSameNote() {
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
    void equals() {
        // same values -> returns true
        Note aliceCopy = new NoteBuilder(ALICE).build();
        assertEquals(ALICE, aliceCopy);

        // same object -> returns true
        assertEquals(ALICE, ALICE);

        // different type -> returns false
        assertNotEquals(5, ALICE);

        // different note -> returns false
        assertNotEquals(ALICE, BOB);

        // different name -> returns false
        Note editedAlice = new NoteBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different address -> returns false
        editedAlice = new NoteBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertNotEquals(ALICE, editedAlice);
    }
}
