package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.CommandTestUtil;
import thrift.testutil.IncomeBuilder;
import thrift.testutil.TypicalTransactions;

public class IncomeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Income income = new IncomeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> income.getTags().remove(0));
    }

    @Test
    public void isSameIncome() {
        // same object -> returns true
        assertTrue(TypicalTransactions.BURSARY.isSameTransaction(TypicalTransactions.BURSARY));

        // null -> NPE
        assertThrows(NullPointerException.class, () -> TypicalTransactions.BURSARY.isSameTransaction(null));

        // different description and value -> returns false
        Income updatedIncome = new IncomeBuilder(TypicalTransactions.BURSARY)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(TypicalTransactions.BURSARY.isSameTransaction(updatedIncome));

        // different description -> returns false
        updatedIncome = new IncomeBuilder(TypicalTransactions.BURSARY)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(TypicalTransactions.BURSARY.isSameTransaction(updatedIncome));

        // same name, same value, different attributes -> returns true
        updatedIncome = new IncomeBuilder(TypicalTransactions.BURSARY)
                .withTags(CommandTestUtil.VALID_TAG_BRUNCH).build();
        assertTrue(TypicalTransactions.BURSARY.isSameTransaction(updatedIncome));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Income incomeCopy = new IncomeBuilder(TypicalTransactions.BURSARY).build();
        assertTrue(TypicalTransactions.BURSARY.equals(incomeCopy));

        // same object -> returns true
        assertTrue(TypicalTransactions.BURSARY.equals(TypicalTransactions.BURSARY));

        // null -> returns false
        assertFalse(TypicalTransactions.BURSARY.equals(null));

        // different type -> returns false
        assertFalse(TypicalTransactions.BURSARY.equals(5));

        // different transaction -> returns false
        assertFalse(TypicalTransactions.BURSARY.equals(TypicalTransactions.PENANG_LAKSA));

        // different name -> returns false
        Income updatedIncome = new IncomeBuilder(TypicalTransactions.BURSARY)
                .withDescription(CommandTestUtil.VALID_DESCRIPTION_AIRPODS).build();
        assertFalse(TypicalTransactions.BURSARY.equals(updatedIncome));

        // different value -> returns false
        updatedIncome = new IncomeBuilder(TypicalTransactions.BURSARY)
                .withValue(CommandTestUtil.VALID_VALUE_AIRPODS).build();
        assertFalse(TypicalTransactions.BURSARY.equals(updatedIncome));

        // different tags -> returns false
        updatedIncome = new IncomeBuilder(TypicalTransactions.BURSARY)
                .withTags(CommandTestUtil.VALID_TAG_ACCESSORY).build();
        assertFalse(TypicalTransactions.BURSARY.equals(updatedIncome));
    }
}
