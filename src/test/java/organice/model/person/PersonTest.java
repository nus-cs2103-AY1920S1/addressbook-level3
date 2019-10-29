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
        Person person = new PersonBuilder().build();
        Person personCopy = new PersonBuilder(person).build();
        assertTrue(person.equals(personCopy));

        // same object -> returns true
        assertTrue(person.equals(person));

        // null -> returns false
        assertFalse(person.equals(null));

        // different object type -> returns false
        assertFalse(person.equals(5));

        // different person -> returns false
        assertFalse(person.equals(PATIENT_BOB));

        // different name -> returns false
        Person editedPerson = new PersonBuilder(person).withName(VALID_NAME_PATIENT_BOB).build();
        assertFalse(person.equals(editedPerson));

        // different phone -> returns false
        editedPerson = new PersonBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedPerson));

        // different nric -> returns false
        editedPerson = new PersonBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedPerson));

        // different person type -> returns false
        editedPerson = new PersonBuilder(DOCTOR_ALICE).withType(VALID_TYPE_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedPerson));
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
