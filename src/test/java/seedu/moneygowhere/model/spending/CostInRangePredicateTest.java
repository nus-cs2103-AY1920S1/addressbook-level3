package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

//@author Nanosync
public class CostInRangePredicateTest {

    @Test
    public void equals() {
        Cost min = new Cost("1.00");
        Cost max = new Cost("2.00");
        CostInRangePredicate firstPredicate = new CostInRangePredicate(min, max);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CostInRangePredicate firstPredicateCopy = new CostInRangePredicate(min, max);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);
    }

    @Test
    public void costPredicate_costInRange_returnsTrue() {
        Cost min = new Cost("1.00");
        Cost max = new Cost("3.00");

        // Cost within range
        CostInRangePredicate predicate = new CostInRangePredicate(min, max);
        assertTrue(predicate.test(new SpendingBuilder().withCost("1.00").build()));
        assertTrue(predicate.test(new SpendingBuilder().withCost("2.00").build()));
        assertTrue(predicate.test(new SpendingBuilder().withCost("3.00").build()));
    }

    @Test
    public void costPredicate_costOutOfRange_returnsFalse() {
        Cost min = new Cost("1.00");
        Cost max = new Cost("3.00");

        // Cost out of range
        CostInRangePredicate predicate = new CostInRangePredicate(min, max);
        assertFalse(predicate.test(new SpendingBuilder().withCost("4.00").build()));

        // invalid predicate
        assertThrows(RuntimeException.class, () -> new CostInRangePredicate(new Cost("2.00"),
                new Cost("1.00")));
    }
}
