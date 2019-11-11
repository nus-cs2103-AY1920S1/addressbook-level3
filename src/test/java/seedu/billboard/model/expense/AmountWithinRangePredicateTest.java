package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class AmountWithinRangePredicateTest {

    @Test
    public void equals() {
        Amount firstPredicateLowerAmtLimit = new Amount("2");
        Amount secondPredicateLowerAmtLimit = new Amount("3");
        Amount firstPredicateUpperAmtLimit = new Amount("4");
        Amount secondPredicateUpperAmtLimit = new Amount("5");

        AmountWithinRangePredicate firstPredicate =
                new AmountWithinRangePredicate(firstPredicateLowerAmtLimit, firstPredicateUpperAmtLimit);
        AmountWithinRangePredicate secondPredicate =
                new AmountWithinRangePredicate(secondPredicateLowerAmtLimit, secondPredicateUpperAmtLimit);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AmountWithinRangePredicate firstPredicateCopy =
                new AmountWithinRangePredicate(firstPredicateLowerAmtLimit, firstPredicateUpperAmtLimit);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_amountWithinRange_returnsTrue() {
        // Amount in between limits
        AmountWithinRangePredicate predicate = new AmountWithinRangePredicate(
                new Amount("2"), new Amount("4"));
        assertTrue(predicate.test(new ExpenseBuilder().withAmount("3").build()));

        // Amount equals lower limit
        predicate = new AmountWithinRangePredicate(
                new Amount("3"), new Amount("7"));
        assertTrue(predicate.test(new ExpenseBuilder().withAmount("3").build()));

        // Amount equals upper limit
        predicate = new AmountWithinRangePredicate(
                new Amount("5"), new Amount("100"));
        assertTrue(predicate.test(new ExpenseBuilder().withAmount("100").build()));

        // Amount (non whole int) in between limits
        predicate = new AmountWithinRangePredicate(
                new Amount("10"), new Amount("1000"));
        assertTrue(predicate.test(new ExpenseBuilder().withAmount("99.9").build()));

        // Amount (very large) in between limits
        predicate = new AmountWithinRangePredicate(
                new Amount("10000000000000"), new Amount("20000000000000"));
        assertTrue(predicate.test(new ExpenseBuilder().withAmount("15000000000000").build()));
    }

    @Test
    public void test_amountNotWithinRange_returnsFalse() {
        // Amount smaller than lower limit
        AmountWithinRangePredicate predicate = new AmountWithinRangePredicate(
                new Amount("2"), new Amount("4"));
        assertFalse(predicate.test(new ExpenseBuilder().withAmount("1").build()));

        // Amount larger than upper limit
        predicate = new AmountWithinRangePredicate(
                new Amount("2"), new Amount("4"));
        assertFalse(predicate.test(new ExpenseBuilder().withAmount("5").build()));
    }
}
