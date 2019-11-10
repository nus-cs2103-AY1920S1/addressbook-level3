package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

public class DateInRangePredicateTest {

    @Test
    public void equals() {
        Date start = new Date("1/1/2019");
        Date end = new Date("2/1/2019");

        DateInRangePredicate firstPredicate = new DateInRangePredicate(start, end);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateInRangePredicate firstPredicateCopy = new DateInRangePredicate(start, end);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);
    }

    @Test
    public void datePredicate_dateInRange_returnsTrue() {
        Date start = new Date("1/1/2019");
        Date end = new Date("3/1/2019");

        // Date within range
        DateInRangePredicate predicate = new DateInRangePredicate(start, end);
        assertTrue(predicate.test(new SpendingBuilder().withDate("1/1/2019").build()));
        assertTrue(predicate.test(new SpendingBuilder().withDate("2/1/2019").build()));
        assertTrue(predicate.test(new SpendingBuilder().withDate("3/1/2019").build()));
    }

    @Test
    public void datePredicate_dateOutOfRange_returnsFalse() {
        Date start = new Date("1/1/2019");
        Date end = new Date("3/1/2019");

        // Date out of range
        DateInRangePredicate predicate = new DateInRangePredicate(start, end);
        assertFalse(predicate.test(new SpendingBuilder().withDate("4/1/2019").build()));
    }
}
