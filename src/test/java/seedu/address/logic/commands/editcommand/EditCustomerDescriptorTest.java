package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.testutil.EditCustomerDescriptorBuilder;

public class EditCustomerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCustomerDescriptor descriptorWithSameValues = new EditCustomerDescriptor(DESC_ALICE);
        assertTrue(DESC_ALICE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALICE.equals(DESC_ALICE));

        // null -> returns false
        assertFalse(DESC_ALICE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALICE.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALICE.equals(DESC_BEN));

        // different name -> returns false
        EditCustomerDescriptor editedAlice = new EditCustomerDescriptorBuilder(DESC_ALICE)
                .withCustomerName(VALID_NAME_BEN).build();
        assertFalse(DESC_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new EditCustomerDescriptorBuilder(DESC_ALICE).withContactNumber(VALID_CONTACT_NUMBER_BEN).build();
        assertFalse(DESC_ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new EditCustomerDescriptorBuilder(DESC_ALICE).withEmail(VALID_EMAIL_BEN).build();
        assertFalse(DESC_ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EditCustomerDescriptorBuilder(DESC_ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_ALICE.equals(editedAlice));
    }
}
