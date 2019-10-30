package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_DOCTOR_IN_CHARGE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_PRIORITY_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PRIORITY_PATIENT_IRENE;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_IRENE;
import static organice.testutil.TypicalPersons.PATIENT_BOB;
import static organice.testutil.TypicalPersons.PATIENT_IRENE;

import org.junit.jupiter.api.Test;

import organice.testutil.PatientBuilder;

public class MatchedPatientTest {
    private static final MatchedPatient MATCHED_PATIENT_IRENE = new MatchedPatient(PATIENT_IRENE);

    @Test
    public void alternativeConstructor_success() {
        //MatchedPatient should accurately represent the Patient. All attributes should be the same.
        assertEquals(VALID_NRIC_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getNric().toString());

        assertEquals(VALID_AGE_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getAge().toString());

        assertEquals(VALID_BLOOD_TYPE_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getBloodType().toString());

        assertEquals(VALID_PHONE_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getPhone().toString());

        assertEquals(VALID_PRIORITY_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getPriority().toString());

        assertEquals(VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getDoctorInCharge().toString());

        assertEquals(VALID_TISSUE_TYPE_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getTissueType().toString());

        assertEquals(VALID_ORGAN_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getOrgan().toString());

        assertEquals(VALID_NAME_PATIENT_IRENE, MATCHED_PATIENT_IRENE.getName().toString());
    }

    @Test
    public void equals() {
        MatchedPatient editedMatchedPatient;

        // same values -> returns true
        MatchedPatient ireneCopy = new MatchedPatient(PATIENT_IRENE);
        assertTrue(ireneCopy.equals(ireneCopy));

        // same object -> returns true
        assertTrue(MATCHED_PATIENT_IRENE.equals(MATCHED_PATIENT_IRENE));

        // null -> returns false
        assertFalse(MATCHED_PATIENT_IRENE.equals(null));

        // different type -> returns false
        assertFalse(MATCHED_PATIENT_IRENE.equals(5));

        // different person -> returns false
        assertFalse(MATCHED_PATIENT_IRENE.equals(PATIENT_BOB));

        // different name -> returns false
        Patient editedIrene = new PatientBuilder(PATIENT_IRENE).withName(VALID_NAME_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedIrene));

        // different phone -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedIrene));

        // different nric -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withNric(VALID_NRIC_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedIrene));

        // different age -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withAge(VALID_AGE_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedMatchedPatient));

        // different priority -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withPriority(VALID_PRIORITY_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedMatchedPatient));

        // different blood type -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withBloodType(VALID_BLOOD_TYPE_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedMatchedPatient));

        // different tissue type -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withTissueType(VALID_TISSUE_TYPE_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedMatchedPatient));

        // different doctor in charge -> returns false
        editedIrene = new PatientBuilder(PATIENT_IRENE).withDoctorInCharge(VALID_DOCTOR_IN_CHARGE_PATIENT_BOB).build();
        editedMatchedPatient = new MatchedPatient(editedIrene);
        assertFalse(MATCHED_PATIENT_IRENE.equals(editedMatchedPatient));
    }

    @Test
    public void toStringTest() {
        MatchedPatient irene = new MatchedPatient(PATIENT_IRENE);
        assertEquals("Irene Person Type: patient Nric: S1111112A Phone: 85355255 "
                + "Age: 21 Priority: high Blood Type: O Tissue Type: 1,4,7,10,11,12 "
                + "Organ: kidney Doctor In Charge: S1231231B "
                + "Status: not processing", irene.toString());
    }

    @Test
    public void hashCodeTest() {
        MatchedPatient irene = new MatchedPatient(PATIENT_IRENE);
        Patient differentPatient = new PatientBuilder(PATIENT_IRENE).withPhone(VALID_PHONE_PATIENT_BOB).build();
        MatchedPatient differentMatchedPatient = new MatchedPatient(differentPatient);

        assertEquals(irene.hashCode(), new MatchedPatient(PATIENT_IRENE).hashCode());
        assertNotEquals(irene.hashCode(), differentMatchedPatient.hashCode());
    }
}
