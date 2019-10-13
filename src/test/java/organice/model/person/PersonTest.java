package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_PATIENT_BOB;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.PATIENT_BOB;
import static organice.testutil.TypicalPersons.PERSON_HOON;

import org.junit.jupiter.api.Test;

import organice.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(DOCTOR_ALICE.isSamePerson(DOCTOR_ALICE));

        // null -> returns false
        assertFalse(DOCTOR_ALICE.isSamePerson(null));

        // different type -> returns true
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withType(VALID_TYPE_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different nric -> returns false
        editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different phone -> returns true
        editedAlice = new PersonBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different name -> returns true
        editedAlice = new PersonBuilder(DOCTOR_ALICE).withName(VALID_NAME_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different nric, same attributes -> returns false
        editedAlice = new PersonBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.isSamePerson(editedAlice));

        // same nric, different attributes -> returns false
        editedAlice = new PersonBuilder(DOCTOR_ALICE).withName(VALID_NAME_PATIENT_BOB)
                .withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person hoonCopy = new PersonBuilder(PERSON_HOON).build();
        assertTrue(PERSON_HOON.equals(hoonCopy));

        // same object -> returns true
        assertTrue(PERSON_HOON.equals(PERSON_HOON));

        // null -> returns false
        assertFalse(PERSON_HOON.equals(null));

        // different type -> returns false
        assertFalse(PERSON_HOON.equals(5));

        // different person -> returns false
        assertFalse(PERSON_HOON.equals(PATIENT_BOB));

        // different name -> returns false
        Person editedHoon = new PersonBuilder(PERSON_HOON).withName(VALID_NAME_PATIENT_BOB).build();
        assertFalse(PERSON_HOON.equals(editedHoon));

        // different phone -> returns false
        editedHoon = new PersonBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedHoon));

        // different nric -> returns false
        editedHoon = new PersonBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedHoon));

        // different person type -> returns false
        editedHoon = new PersonBuilder(DOCTOR_ALICE).withType(VALID_TYPE_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedHoon));
    }

    @Test
    public void toStringTest() {
        Person alice = new PersonBuilder(DOCTOR_ALICE).build();
        assertEquals(alice.toString().trim() , "Alice Pauline Person Type: doctor Nric: S1532142A Phone: 94351253");
    }

    @Test
    public void hashCodeTest() {
        Person alice = new PersonBuilder(DOCTOR_ALICE).build();

        assertEquals(alice.hashCode(), new PersonBuilder(DOCTOR_ALICE).build().hashCode());
        assertNotEquals(alice.hashCode(),
                new PersonBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build().hashCode());
    }
}
