package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_DOCTOR_IN_CHARGE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PRIORITY_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_BOB;
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

        // different age -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withAge(VALID_AGE_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different priority -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withPriority(VALID_PRIORITY_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different blood type -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withBloodType(VALID_BLOOD_TYPE_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different tissue type -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withTissueType(VALID_TISSUE_TYPE_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different organ -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withOrgan(VALID_ORGAN_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different doctor in charge -> returns true
        editedIrene = new PatientBuilder(PATIENT_IRENE).withDoctorInCharge(VALID_DOCTOR_IN_CHARGE_PATIENT_BOB).build();
        assertTrue(PATIENT_IRENE.isSamePerson(editedIrene));

        // different nric, same attributes -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.isSamePerson(editedIrene));

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

        // different age -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withAge(VALID_AGE_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different priority -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withPriority(VALID_PRIORITY_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different blood type -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withBloodType(VALID_BLOOD_TYPE_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different tissue type -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withTissueType(VALID_TISSUE_TYPE_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));

        // different doctor in charge -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withDoctorInCharge(VALID_DOCTOR_IN_CHARGE_PATIENT_BOB).build();
        assertFalse(PATIENT_IRENE.equals(editedIrene));
    }

    @Test
    public void toStringTest() {
        Patient irene = new PatientBuilder(PATIENT_IRENE).build();
        assertEquals("Irene Person Type: patient Nric: S9605440H Phone: 85355255 "
                + "Age: 21 Priority: high Blood Type: O+ Tissue Type: 1,4,7,10,11,12 "
                + "Organ: kidney Doctor In Charge: F1289064T "
                + "Status: not processing", irene.toString());
    }

    @Test
    public void hashCodeTest() {
        Patient irene = new PatientBuilder(PATIENT_IRENE).build();

        assertEquals(irene.hashCode(), new PatientBuilder(PATIENT_IRENE).build().hashCode());
        assertNotEquals(irene.hashCode(),
                new PatientBuilder(PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_BOB).build().hashCode());
    }
}
