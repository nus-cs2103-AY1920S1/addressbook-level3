package seedu.system.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.system.testutil.TypicalPersons.ALICE;
import static seedu.system.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.system.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameElement(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameElement(null));

        // different DOB and gender, but same name -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withDateOfBirth(VALID_DOB_BOB)
                .withGender(VALID_GENDER_BOB).build();
        assertTrue(ALICE.isSameElement(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameElement(editedAlice));

        // same name, same DOB, different gender -> returns true
        editedAlice = new PersonBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
        assertTrue(ALICE.isSameElement(editedAlice));

        // same name, different DOB, same gender -> returns true
        editedAlice = new PersonBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
        assertTrue(ALICE.isSameElement(editedAlice));

        // different name, same DOB, same gender -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameElement(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different DOB -> returns true
        editedAlice = new PersonBuilder(ALICE).withDateOfBirth(VALID_DOB_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different gender -> returns true
        editedAlice = new PersonBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

    }
}
