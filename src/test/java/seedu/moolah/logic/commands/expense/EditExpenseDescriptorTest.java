package seedu.moolah.logic.commands.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.DESC_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_TAXI;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.moolah.testutil.EditExpenseDescriptorBuilder;

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

        // different description -> returns false
        EditExpenseDescriptor editedAmy = new EditExpenseDescriptorBuilder(DESC_CHICKEN)
                .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
        assertFalse(DESC_CHICKEN.equals(editedAmy));

        // different price -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_CHICKEN).withPrice(VALID_EXPENSE_PRICE_TAXI).build();
        assertFalse(DESC_CHICKEN.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_CHICKEN).withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();
        assertFalse(DESC_CHICKEN.equals(editedAmy));
    }
}
