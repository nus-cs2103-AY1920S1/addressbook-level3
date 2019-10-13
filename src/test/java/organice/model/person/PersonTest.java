package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_BOB;
import static organice.testutil.TypicalPersons.ALICE;
import static organice.testutil.TypicalPersons.BOB;
import static organice.testutil.TypicalPersons.HOON;

import org.junit.jupiter.api.Test;

import organice.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different type -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withType(VALID_TYPE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different nric -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different phone -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different nric, same attributes -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same nric, different attributes -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person hoonCopy = new PersonBuilder(HOON).build();
        assertTrue(HOON.equals(hoonCopy));

        // same object -> returns true
        assertTrue(HOON.equals(HOON));

        // null -> returns false
        assertFalse(HOON.equals(null));

        // different type -> returns false
        assertFalse(HOON.equals(5));

        // different person -> returns false
        assertFalse(HOON.equals(BOB));

        // different name -> returns false
        Person editedHoon = new PersonBuilder(HOON).withName(VALID_NAME_BOB).build();
        assertFalse(HOON.equals(editedHoon));

        // different phone -> returns false
        editedHoon = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedHoon));

        // different nric -> returns false
        editedHoon = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.equals(editedHoon));

        // different person type -> returns false
        editedHoon = new PersonBuilder(ALICE).withType(VALID_TYPE_BOB).build();
        assertFalse(ALICE.equals(editedHoon));
    }

    @Test
    public void toStringTest() {
        Person alice = new PersonBuilder(ALICE).build();
        assertEquals(alice.toString().trim() , "Alice Pauline Person Type: doctor Nric: S1532142A Phone: 94351253");
    }

    @Test
    public void hashCodeTest() {
        Person alice = new PersonBuilder(ALICE).build();

        assertEquals(alice.hashCode(), new PersonBuilder(ALICE).build().hashCode());
        assertNotEquals(alice.hashCode(),
                new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build().hashCode());
    }
}
