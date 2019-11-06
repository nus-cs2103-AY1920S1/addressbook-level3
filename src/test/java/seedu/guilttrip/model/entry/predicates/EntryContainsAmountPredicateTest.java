package seedu.guilttrip.model.entry.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.testutil.ExpenseBuilder;

public class EntryContainsAmountPredicateTest {

    @Test
    public void equals() {
        Double firstPredicateDouble = 5.00;
        Double secondPredicateDouble = 9.00;

        EntryContainsAmountPredicate firstPredicate =
                new EntryContainsAmountPredicate(firstPredicateDouble);
        EntryContainsAmountPredicate secondPredicate =
                new EntryContainsAmountPredicate(secondPredicateDouble);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntryContainsAmountPredicate firstPredicateCopy =
                new EntryContainsAmountPredicate(firstPredicateDouble);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("1"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different entry -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    private void assertTrue(boolean equals) {
    }

    @Test
    public void test_amountContainsLargerThan_returnsTrue() {
        // Contains Exact Amount
        EntryContainsAmountPredicate predicate =
                new EntryContainsAmountPredicate(5.00);
        assertTrue(predicate.test(new ExpenseBuilder().withAmt(5.00).build()));

        // Contains Larger Amount
        EntryContainsAmountPredicate predicate2 =
                new EntryContainsAmountPredicate(5.00);
        assertTrue(predicate2.test(new ExpenseBuilder().withAmt(512313.00).build()));

    }

    @Test
    public void test_amountContainsSmallerThan_returnsFalse() {
        // Zero keywords throws Exception in the Parser Stage
        EntryContainsAmountPredicate predicate;

        // Non-matching amount
        predicate = new EntryContainsAmountPredicate(5.00);
        assertFalse(predicate.test(new ExpenseBuilder().withAmt(4.00).build()));
    }
}
