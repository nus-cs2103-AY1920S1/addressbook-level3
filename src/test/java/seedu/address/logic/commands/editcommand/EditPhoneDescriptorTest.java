package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BRAND_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NAME_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_REGULAR;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.testutil.EditPhoneDescriptorBuilder;

public class EditPhoneDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPhoneDescriptor descriptorWithSameValues = new EditPhoneDescriptor(DESC_IPHONE);
        assertTrue(DESC_IPHONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_IPHONE.equals(DESC_IPHONE));

        // null -> returns false
        assertFalse(DESC_IPHONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_IPHONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_IPHONE.equals(DESC_SAMSUNG));

        // different name -> returns false
        EditPhoneDescriptor editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE)
                .withPhoneName(VALID_PHONE_NAME_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));

        // different identity number -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE)
                .withIdentityNumber(VALID_IDENTITY_NUMBER_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));

        // different serial number -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE)
                .withSerialNumber(VALID_SERIAL_NUMBER_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));

        // different brand -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE).withBrand(VALID_BRAND_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));

        // different capacity -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE).withCapacity(VALID_CAPACITY_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));

        // different cost -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE).withCost(VALID_COST_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));

        // different colour -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE).withColour(VALID_COLOUR_SAMSUNG).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));


        // different tags -> returns false
        editedIphone = new EditPhoneDescriptorBuilder(DESC_IPHONE).withTags(VALID_TAG_REGULAR).build();
        assertFalse(DESC_IPHONE.equals(editedIphone));
    }
}
