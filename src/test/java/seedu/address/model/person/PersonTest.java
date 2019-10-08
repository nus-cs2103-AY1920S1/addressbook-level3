package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameNote(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameNote(null));

        // different title -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE.isSameNote(editedAlice));

        // only different content -> returns true
        editedAlice = new PersonBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertTrue(ALICE.isSameNote(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withTitle(VALID_TITLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withContent(VALID_CONTENT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
