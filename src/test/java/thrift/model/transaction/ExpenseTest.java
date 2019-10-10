package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.CommandTestUtil;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TypicalTransactions;

public class ExpenseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Expense expense = new ExpenseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> expense.getTags().remove(0));
    }

    @Test
    public void isSameExpense() {
        // same object -> returns true
        assertTrue(TypicalTransactions.LAKSA.isSameTransaction(TypicalTransactions.LAKSA));

        // null -> returns false
        assertFalse(TypicalTransactions.LAKSA.isSameTransaction(null));

        // different description and value -> returns false
        Expense updatedExpense = new ExpenseBuilder(TypicalTransactions.LAKSA)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(TypicalTransactions.LAKSA.isSameTransaction(updatedExpense));

        // different description -> returns false
        updatedExpense = new ExpenseBuilder(TypicalTransactions.LAKSA)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(TypicalTransactions.LAKSA.isSameTransaction(updatedExpense));

        // same name, same value, different attributes -> returns true
        updatedExpense = new ExpenseBuilder(TypicalTransactions.LAKSA)
                .withTags(CommandTestUtil.VALID_TAG_BRUNCH).build();
        assertTrue(TypicalTransactions.LAKSA.isSameTransaction(updatedExpense));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense expenseCopy = new ExpenseBuilder(TypicalTransactions.LAKSA).build();
        assertTrue(TypicalTransactions.LAKSA.equals(expenseCopy));

        // same object -> returns true
        assertTrue(TypicalTransactions.LAKSA.equals(TypicalTransactions.LAKSA));

        // null -> returns false
        assertFalse(TypicalTransactions.LAKSA.equals(null));

        // different type -> returns false
        assertFalse(TypicalTransactions.LAKSA.equals(5));

        // different transaction -> returns false
        assertFalse(TypicalTransactions.LAKSA.equals(TypicalTransactions.PENANG_LAKSA));

        // different name -> returns false
        Expense updatedExpense = new ExpenseBuilder(TypicalTransactions.LAKSA)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(TypicalTransactions.LAKSA.equals(updatedExpense));

        // different value -> returns false
        updatedExpense = new ExpenseBuilder(TypicalTransactions.LAKSA)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS).build();
        assertFalse(TypicalTransactions.LAKSA.equals(updatedExpense));

        // different tags -> returns false
        updatedExpense = new ExpenseBuilder(TypicalTransactions.LAKSA)
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY).build();
        assertFalse(TypicalTransactions.LAKSA.equals(updatedExpense));
    }
}
