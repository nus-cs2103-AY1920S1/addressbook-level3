package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.DESC_AMY;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.DESC_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditPatientDescriptor descriptorWithSameValues = new EditCommand.EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
