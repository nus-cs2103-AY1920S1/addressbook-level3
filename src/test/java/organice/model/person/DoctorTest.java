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

import organice.testutil.DoctorBuilder;

public class DoctorTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(DOCTOR_ALICE.isSamePerson(DOCTOR_ALICE));

        // null -> returns false
        assertFalse(DOCTOR_ALICE.isSamePerson(null));

        Doctor editedAlice;

        // different nric -> returns false
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different phone -> returns true
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different name -> returns true
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withName(VALID_NAME_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));

        // different nric, same attributes -> returns false
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.isSamePerson(editedAlice));

        // same nric, different attributes -> returns true
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withName(VALID_NAME_PATIENT_BOB)
                .withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertTrue(DOCTOR_ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Doctor aliceCopy = new DoctorBuilder(DOCTOR_ALICE).build();
        assertTrue(DOCTOR_ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(DOCTOR_ALICE.equals(DOCTOR_ALICE));

        // null -> returns false
        assertFalse(DOCTOR_ALICE.equals(null));

        // different type -> returns false
        assertFalse(DOCTOR_ALICE.equals(5));

        // different doctor -> returns false
        assertFalse(DOCTOR_ALICE.equals(PATIENT_BOB));

        // different name -> returns false
        Doctor editedAlice = new DoctorBuilder(DOCTOR_ALICE).withName(VALID_NAME_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedAlice));

        // different nric -> returns false
        editedAlice = new DoctorBuilder(DOCTOR_ALICE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedAlice));

        // different person type -> returns false
        editedAlice = (Doctor) new DoctorBuilder(DOCTOR_ALICE).withType(VALID_TYPE_PATIENT_BOB).build();
        assertFalse(DOCTOR_ALICE.equals(editedAlice));
    }

    @Test
    public void toStringTest() {
        Doctor alice = new DoctorBuilder(DOCTOR_ALICE).build();
        assertEquals(alice.toString().trim() , "Alice Pauline Person Type: doctor"
                + " Nric: S1532142A Phone: 94351253");
    }

    @Test
    public void hashCodeTest() {
        Doctor alice = new DoctorBuilder(DOCTOR_ALICE).build();

        assertEquals(alice.hashCode(), new DoctorBuilder(DOCTOR_ALICE).build().hashCode());
        assertNotEquals(alice.hashCode(),
                new DoctorBuilder(DOCTOR_ALICE).withPhone(VALID_PHONE_PATIENT_BOB).build().hashCode());
    }
}
