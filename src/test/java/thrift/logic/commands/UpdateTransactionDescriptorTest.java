package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import thrift.testutil.UpdateTransactionDescriptorBuilder;

public class UpdateTransactionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateCommand.UpdateTransactionDescriptor descriptorWithSameValues =
                new UpdateTransactionDescriptor(CommandTestUtil.DESC_MEAL);
        assertTrue(CommandTestUtil.DESC_MEAL.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(CommandTestUtil.DESC_MEAL.equals(CommandTestUtil.DESC_MEAL));

        // null -> returns false
        assertFalse(CommandTestUtil.DESC_MEAL.equals(null));

        // different types -> returns false
        assertFalse(CommandTestUtil.DESC_MEAL.equals(5));

        // different values -> returns false
        assertFalse(CommandTestUtil.DESC_MEAL.equals(CommandTestUtil.DESC_PURCHASE));

        // different description -> returns false
        UpdateTransactionDescriptor updatedTransaction =
                new UpdateTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(updatedTransaction));

        // different value -> returns false
        updatedTransaction = new UpdateTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(updatedTransaction));

        // different date -> returns false
        updatedTransaction = new UpdateTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withDate(CommandTestUtil.VALID_DATE_AIRPODS).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(updatedTransaction));

        // different tags -> returns false
        updatedTransaction = new UpdateTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withTags(CommandTestUtil.VALID_TAG_BRUNCH).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(updatedTransaction));
    }
}
