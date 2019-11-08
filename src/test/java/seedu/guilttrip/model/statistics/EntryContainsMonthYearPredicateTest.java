package seedu.guilttrip.model.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.TypicalEntries.SALARY_INCOME;
import static seedu.guilttrip.testutil.TypicalEntries.TRAVEL_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.testutil.ExpenseBuilder;

public class EntryContainsMonthYearPredicateTest {

    @Test
    public void equals() {
        Date firstPredicateDate = TRAVEL_EXPENSE.getDate();
        Date secondPredicateDate = SALARY_INCOME.getDate();

        EntryContainsMonthYearPredicate firstPredicate =
                new EntryContainsMonthYearPredicate(firstPredicateDate.getDate().getMonthValue(),
                        firstPredicateDate.getDate().getYear());
        EntryContainsMonthYearPredicate secondPredicate =
                new EntryContainsMonthYearPredicate(secondPredicateDate.getDate().getMonthValue(),
                        secondPredicateDate.getDate().getYear());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntryContainsMonthYearPredicate firstPredicateCopy =
                new EntryContainsMonthYearPredicate(firstPredicateDate.getDate().getMonthValue(),
                        firstPredicateDate.getDate().getYear());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("1"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different entry -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateWithinMonthYear_returnsTrue() {
        Date firstPredicateDate = TRAVEL_EXPENSE.getDate();
        // Contains Same Day
        EntryContainsMonthYearPredicate firstPredicate =
                new EntryContainsMonthYearPredicate(firstPredicateDate.getDate().getMonthValue(),
                        firstPredicateDate.getDate().getYear());
        assertTrue(firstPredicate.test(new ExpenseBuilder().withTime("2019-08-11").build()));

        // Contains Same Month
        assertTrue(firstPredicate.test(new ExpenseBuilder().withTime("2019-08-01").build()));

        // Contains Same Month
        assertTrue(firstPredicate.test(new ExpenseBuilder().withTime("2019-08-31").build()));
    }

    @Test
    public void test_dateNotWithinMonthYear_returnsFalse() {
        Date firstPredicateDate = TRAVEL_EXPENSE.getDate();
        EntryContainsMonthYearPredicate firstPredicate =
                new EntryContainsMonthYearPredicate(firstPredicateDate.getDate().getMonthValue(),
                        firstPredicateDate.getDate().getYear());
        //diff month
        assertFalse(firstPredicate.test(new ExpenseBuilder().withTime("2019-09-11").build()));

        //diff Year
        assertFalse(firstPredicate.test(new ExpenseBuilder().withTime("2018-08-11").build()));
    }
}
