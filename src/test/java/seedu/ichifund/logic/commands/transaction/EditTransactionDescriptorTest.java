package seedu.ichifund.logic.commands.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESC_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.DESC_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_BUS;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.ichifund.testutil.EditTransactionDescriptorBuilder;

public class EditTransactionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTransactionDescriptor descriptorWithSameValues = new EditTransactionDescriptor(DESC_ALLOWANCE);
        assertTrue(DESC_ALLOWANCE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALLOWANCE.equals(DESC_ALLOWANCE));

        // null -> returns false
        assertFalse(DESC_ALLOWANCE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALLOWANCE.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALLOWANCE.equals(DESC_BUS));

        // different description -> returns false
        EditTransactionDescriptor editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE)
                .withDescription(VALID_DESCRIPTION_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));

        // different amount -> returns false
        editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE).withAmount(VALID_AMOUNT_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));

        // different day -> returns false
        editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE).withDay(VALID_DAY_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));

        // different month -> returns false
        editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE).withMonth(VALID_MONTH_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));

        // different year -> returns false
        editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE).withYear(VALID_YEAR_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));

        // different category -> returns false
        editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE)
                .withCategory(VALID_CATEGORY_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));

        // different transactionType -> returns false
        editedAllowance = new EditTransactionDescriptorBuilder(DESC_ALLOWANCE)
                .withTransactionType(VALID_TRANSACTION_TYPE_BUS).build();
        assertFalse(DESC_ALLOWANCE.equals(editedAllowance));
    }
}
