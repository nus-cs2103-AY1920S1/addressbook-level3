package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.testutil.TypicalPersons.DOCTOR_ALICE;
import static organice.testutil.TypicalPersons.PATIENT_BOB;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import org.junit.jupiter.api.Test;

import organice.testutil.PatientBuilder;
import organice.testutil.PersonBuilder;

public class PatientTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(PATIENT_IRENE.isSamePerson(PATIENT_IRENE));

        // null -> returns false
        assertFalse(PATIENT_IRENE.isSamePerson(null));

        Patient editedIrene = new PatientBuilder(PATIENT_IRENE).build();

        // different nric -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.isSamePerson(editedIrene));

        // different phone -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different name -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withName(VALID_NAME_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different nric, same attributes -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.isSamePerson(editedIrene));

        // same nric, different attributes -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withName(VALID_NAME_PATIENT_BOB)
                .withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient ireneCopy = new PatientBuilder(PATIENT_IRENE).build();
        assertTrue(PATIENT_IRENE.equals(ireneCopy));

        // same object -> returns true
        assertTrue(PATIENT_IRENE.equals(PATIENT_IRENE));

        // null -> returns false
        assertFalse(PATIENT_IRENE.equals(null));

        // different type -> returns false
        assertFalse(PATIENT_IRENE.equals(5));

        // different person -> returns false
        assertFalse(PATIENT_IRENE.equals(PATIENT_BOB));

        // different name -> returns false
        Patient editedIrene = new PatientBuilder(PATIENT_IRENE).withName(VALID_NAME_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different phone -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different nric -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different person type -> returns false
        Person editedAlice = new PersonBuilder(DOCTOR_ALICE).withType("doctor").build();
        assertFalse(editedAlice.equals(editedIrene));
    }

    @Test
    public void toStringTest() {
        Patient irene = new PatientBuilder(PATIENT_IRENE).build();
        assertEquals(irene.toString().trim() , "Irene Person Type: patient Nric: S1111111A"
                + " Phone: 85355255 Age: 21");
    }

    @Test
    public void hashCodeTest() {
        Patient irene = new PatientBuilder(PATIENT_IRENE).build();

        assertEquals(irene.hashCode(), new PatientBuilder(PATIENT_IRENE).build().hashCode());
        assertNotEquals(irene.hashCode(),
                new PatientBuilder(PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_BOB).build().hashCode());
    }
}
