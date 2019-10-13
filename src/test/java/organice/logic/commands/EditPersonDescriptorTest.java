package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.DESC_DOCTOR_AMY;
import static organice.logic.commands.CommandTestUtil.DESC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_PATIENT_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_PATIENT_BOB;

import org.junit.jupiter.api.Test;

import organice.logic.commands.EditCommand.EditPersonDescriptor;
import organice.testutil.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_DOCTOR_AMY);
        assertTrue(DESC_DOCTOR_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DOCTOR_AMY.equals(DESC_DOCTOR_AMY));

        // null -> returns false
        assertFalse(DESC_DOCTOR_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_DOCTOR_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_DOCTOR_AMY.equals(DESC_PATIENT_BOB));

        // different nric -> returns false
        EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_DOCTOR_AMY)
                .withName(VALID_NRIC_PATIENT_BOB).build();
        assertFalse(DESC_DOCTOR_AMY.equals(editedAmy));

        // different name -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_DOCTOR_AMY).withName(VALID_NAME_PATIENT_BOB).build();
        assertFalse(DESC_DOCTOR_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_DOCTOR_AMY).withPhone(VALID_PHONE_PATIENT_BOB).build();
        assertFalse(DESC_DOCTOR_AMY.equals(editedAmy));

    }
}
