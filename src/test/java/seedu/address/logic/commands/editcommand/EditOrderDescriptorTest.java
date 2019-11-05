package seedu.address.logic.commands.editcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORDER_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORDER_SAMSUNG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_SAMSUNG;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.TypicalCustomers;
import seedu.address.testutil.TypicalPhones;

public class EditOrderDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditOrderDescriptor descriptorWithSameValues = new EditOrderDescriptor(DESC_ORDER_IPHONE);
        assertTrue(DESC_ORDER_IPHONE.equals(DESC_ORDER_IPHONE));

        // same object -> returns true
        assertTrue(DESC_ORDER_IPHONE.equals(DESC_ORDER_IPHONE));

        // null -> returns false
        assertFalse(DESC_ORDER_IPHONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ORDER_IPHONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_ORDER_IPHONE.equals(DESC_ORDER_SAMSUNG));

        // different price -> returns false
        EditOrderDescriptor editedOrder = new EditOrderDescriptorBuilder(DESC_ORDER_IPHONE)
                .withPrice(VALID_PRICE_SAMSUNG).build();
        assertFalse(DESC_ORDER_IPHONE.equals(editedOrder));

        // different phone -> returns false
        editedOrder = new EditOrderDescriptorBuilder(DESC_ORDER_IPHONE).withPhone(TypicalPhones.IPHONEXR).build();
        assertFalse(DESC_ORDER_IPHONE.equals(editedOrder));

        // different customer -> returns false
        editedOrder = new EditOrderDescriptorBuilder(DESC_ORDER_IPHONE).withCustomer(TypicalCustomers.ELLE).build();
        assertFalse(DESC_ORDER_IPHONE.equals(editedOrder));

        // different tags -> returns false
        editedOrder = new EditOrderDescriptorBuilder(DESC_ORDER_IPHONE).withTags().build();
        assertFalse(DESC_ORDER_IPHONE.equals(editedOrder));
    }
}
