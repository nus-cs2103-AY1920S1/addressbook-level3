package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CLAIMABLE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_CHICKEN);
        assertTrue(DESC_CHICKEN.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CHICKEN.equals(DESC_CHICKEN));

        // null -> returns false
        assertFalse(DESC_CHICKEN.equals(null));

        // different types -> returns false
        assertFalse(DESC_CHICKEN.equals(5));

        // different values -> returns false
        assertFalse(DESC_CHICKEN.equals(DESC_TRANSPORT));

        // different description -> returns false
        EditExpenseDescriptor editedAmy = new EditExpenseDescriptorBuilder(DESC_CHICKEN)
                .withDescription(VALID_DESCRIPTION_TRANSPORT).build();
        assertFalse(DESC_CHICKEN.equals(editedAmy));

        // different price -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_CHICKEN).withPrice(VALID_PRICE_TRANSPORT).build();
        assertFalse(DESC_CHICKEN.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_CHICKEN).withTags(VALID_TAG_CLAIMABLE).build();
        assertFalse(DESC_CHICKEN.equals(editedAmy));
    }
}
