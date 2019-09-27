package thrift.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.EditCommand.EditTransactionDescriptor;
import thrift.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTransactionDescriptor descriptorWithSameValues =
                new EditTransactionDescriptor(CommandTestUtil.DESC_MEAL);
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
        EditTransactionDescriptor editedTransaction = new EditTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(editedTransaction));

        // different value -> returns false
        editedTransaction = new EditTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(editedTransaction));

        // different date -> returns false
        editedTransaction = new EditTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withDate(CommandTestUtil.VALID_DATE_AIRPODS).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(editedTransaction));

        // different tags -> returns false
        editedTransaction = new EditTransactionDescriptorBuilder(CommandTestUtil.DESC_MEAL)
                .withTags(CommandTestUtil.VALID_TAG_BRUNCH).build();
        assertFalse(CommandTestUtil.DESC_MEAL.equals(editedTransaction));
    }
}
