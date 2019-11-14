package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static organice.logic.commands.CommandTestUtil.VALID_AGE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_BLOOD_TYPE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_ORGAN_EXPIRY_DATE_DONOR_JOHNY;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TISSUE_TYPE_PATIENT_BOB;
import static organice.testutil.TypicalPersons.DONOR_IRENE_DONOR;
import static organice.testutil.TypicalPersons.PATIENT_BOB;

import org.junit.jupiter.api.Test;

import organice.testutil.DonorBuilder;

public class MatchedDonorTest {
    private static final MatchedDonor MATCHED_DONOR_IRENE_DONOR = new MatchedDonor(DONOR_IRENE_DONOR);

    @Test
    public void alternativeConstructor_success() {
        //MatchedPatient should accurately represent the Patient. All attributes should be the same.
        String ireneDonorNric = DONOR_IRENE_DONOR.getNric().toString();
        assertEquals(ireneDonorNric, MATCHED_DONOR_IRENE_DONOR.getNric().toString());

        String ireneDonorAge = DONOR_IRENE_DONOR.getAge().toString();
        assertEquals(ireneDonorAge, MATCHED_DONOR_IRENE_DONOR.getAge().toString());

        String ireneDonorBloodType = DONOR_IRENE_DONOR.getBloodType().toString();
        assertEquals(ireneDonorBloodType, MATCHED_DONOR_IRENE_DONOR.getBloodType().toString());

        String ireneDonorPhone = DONOR_IRENE_DONOR.getPhone().toString();
        assertEquals(ireneDonorPhone, MATCHED_DONOR_IRENE_DONOR.getPhone().toString());

        String ireneDonorTissueType = DONOR_IRENE_DONOR.getTissueType().toString();
        assertEquals(ireneDonorTissueType, MATCHED_DONOR_IRENE_DONOR.getTissueType().toString());

        String ireneDonorOrgan = DONOR_IRENE_DONOR.getOrgan().toString();
        assertEquals(ireneDonorOrgan, MATCHED_DONOR_IRENE_DONOR.getOrgan().toString());

        String ireneDonorName = DONOR_IRENE_DONOR.getName().toString();
        assertEquals(ireneDonorName, MATCHED_DONOR_IRENE_DONOR.getName().toString());

        String ireneDonorOrganExpiryDate = DONOR_IRENE_DONOR.getOrganExpiryDate().toString();
        assertEquals(ireneDonorOrganExpiryDate, MATCHED_DONOR_IRENE_DONOR.getOrganExpiryDate().toString());
    }

    @Test
    public void equals() {
        MatchedDonor editedMatchedDonor;

        // same values -> returns true
        MatchedDonor ireneCopy = new MatchedDonor(DONOR_IRENE_DONOR);
        assertEquals(ireneCopy, MATCHED_DONOR_IRENE_DONOR);

        // same object -> returns true
        assertEquals(MATCHED_DONOR_IRENE_DONOR, MATCHED_DONOR_IRENE_DONOR);

        // null -> returns false
        assertNotEquals(null, MATCHED_DONOR_IRENE_DONOR);

        // different object class -> returns false
        assertNotEquals(5, MATCHED_DONOR_IRENE_DONOR);

        // different person -> returns false
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, PATIENT_BOB);

        // different name -> returns false
        Donor editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withName(VALID_NAME_PATIENT_BOB).build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedIrene);

        // different phone -> returns false
        editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withPhone(VALID_PHONE_PATIENT_BOB).build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedMatchedDonor);

        // different nric -> returns false
        editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withNric(VALID_NRIC_PATIENT_BOB).build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedMatchedDonor);

        // different age -> returns false
        editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withAge(VALID_AGE_PATIENT_BOB).build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedMatchedDonor);

        // different blood type -> returns false
        editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withBloodType(VALID_BLOOD_TYPE_PATIENT_BOB).build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedMatchedDonor);

        // different tissue type -> returns false
        editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withTissueType(VALID_TISSUE_TYPE_PATIENT_BOB).build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedMatchedDonor);

        //different organ expiry date -> return false
        editedIrene = new DonorBuilder(DONOR_IRENE_DONOR).withOrganExpiryDate(VALID_ORGAN_EXPIRY_DATE_DONOR_JOHNY)
                .build();
        editedMatchedDonor = new MatchedDonor(editedIrene);
        assertNotEquals(MATCHED_DONOR_IRENE_DONOR, editedMatchedDonor);
    }

    @Test
    public void toStringTest() {
        assertEquals("Irene Donor Person Type: donor Nric: S9155102J Phone: 85355255 "
                + "Age: 21 Blood Type: O+ Tissue Type: 1,4,7,10,11,12 "
                + "Organ: kidney Organ Expiry Date: 20-Jan-2020 "
                + "Status: not processing", MATCHED_DONOR_IRENE_DONOR.toString());
    }

    @Test
    public void hashCodeTest() {
        MatchedDonor ireneDonor = new MatchedDonor(DONOR_IRENE_DONOR);
        Donor differentDonor = new DonorBuilder(DONOR_IRENE_DONOR).withPhone(VALID_PHONE_PATIENT_BOB).build();
        MatchedDonor differentMatchedDonor = new MatchedDonor(differentDonor);

        assertEquals(ireneDonor.hashCode(), new MatchedDonor(DONOR_IRENE_DONOR).hashCode());
        assertNotEquals(ireneDonor.hashCode(), differentMatchedDonor.hashCode());
    }
}
