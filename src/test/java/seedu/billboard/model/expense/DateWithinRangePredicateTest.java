package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class DateWithinRangePredicateTest {

    @Test
    public void equals() {
        CreatedDateTime firstPredicateStartDate = new CreatedDateTime("1/1/2019");
        CreatedDateTime secondPredicateStartDate = new CreatedDateTime("2/2/2019");
        CreatedDateTime firstPredicateEndDate = new CreatedDateTime("1/1/2019");
        CreatedDateTime secondPredicateEndDate = new CreatedDateTime("2/2/2019");

        DateWithinRangePredicate firstPredicate =
                new DateWithinRangePredicate(firstPredicateStartDate, firstPredicateEndDate);
        DateWithinRangePredicate secondPredicate =
                new DateWithinRangePredicate(secondPredicateStartDate, secondPredicateEndDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateWithinRangePredicate firstPredicateCopy =
                new DateWithinRangePredicate(firstPredicateStartDate, firstPredicateEndDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateWithinRange_returnsTrue() {
        // Date in between start and end date
        DateWithinRangePredicate predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019"), new CreatedDateTime("1/7/2019"));
        assertTrue(predicate.test(new ExpenseBuilder().withCreatedDateTime("2/6/2019").build()));

        // Date equals start date
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019"), new CreatedDateTime("1/7/2019"));
        assertTrue(predicate.test(new ExpenseBuilder().withCreatedDateTime("1/6/2019").build()));

        // Date equals end date
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019"), new CreatedDateTime("1/7/2019"));
        assertTrue(predicate.test(new ExpenseBuilder().withCreatedDateTime("1/7/2019").build()));

        // Same date different time
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019 0800"), new CreatedDateTime("1/6/2019 1000"));
        assertTrue(predicate.test(new ExpenseBuilder().withCreatedDateTime("1/6/2019 0900").build()));
    }

    @Test
    public void test_dateIsNotWithinRange_returnsFalse() {
        // Date before start date
        DateWithinRangePredicate predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019"), new CreatedDateTime("1/7/2019"));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("30/5/2019").build()));

        // Date after end date
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019"), new CreatedDateTime("1/7/2019"));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("2/7/2019").build()));

        // Same date, time before start time
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019 0800"), new CreatedDateTime("1/6/2019 1000"));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("1/6/2019 0700").build()));

        // Same date, time after end time
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("5/6/2019 0800"), new CreatedDateTime("5/6/2019 1000"));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("5/6/2019 1100").build()));

        // Date before start date, time correct
        predicate = new DateWithinRangePredicate(
                new CreatedDateTime("1/6/2019 0800"), new CreatedDateTime("3/6/2019 1000"));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("1/5/2019 0900").build()));
    }
}
