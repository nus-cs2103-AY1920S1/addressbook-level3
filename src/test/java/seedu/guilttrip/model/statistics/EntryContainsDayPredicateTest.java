package seedu.guilttrip.model.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.TypicalEntries.SALARY_INCOME;
import static seedu.guilttrip.testutil.TypicalEntries.TRAVEL_EXPENSE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.testutil.ExpenseBuilder;

public class EntryContainsDayPredicateTest {

    @Test
    public void equals() {
        LocalDate firstPredicateDate = TRAVEL_EXPENSE.getDate().getDate();
        LocalDate secondPredicateDate = SALARY_INCOME.getDate().getDate();

        EntryContainsDayPredicate firstPredicate =
                new EntryContainsDayPredicate(firstPredicateDate.getDayOfMonth(), firstPredicateDate.getMonthValue(),
                        firstPredicateDate.getYear());
        EntryContainsDayPredicate secondPredicate =
                new EntryContainsDayPredicate(secondPredicateDate.getDayOfMonth(), secondPredicateDate.getMonthValue(),
                        secondPredicateDate.getYear());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntryContainsDayPredicate firstPredicateCopy =
                new EntryContainsDayPredicate(firstPredicateDate.getDayOfMonth(), firstPredicateDate.getMonthValue(),
                        firstPredicateDate.getYear());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("1"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different entry -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateIsEquals_returnsTrue() {
        LocalDate firstPredicateDate = TRAVEL_EXPENSE.getDate().getDate();
        EntryContainsDayPredicate firstPredicate =
                new EntryContainsDayPredicate(firstPredicateDate.getDayOfMonth(), firstPredicateDate.getMonthValue(),
                        firstPredicateDate.getYear());
        //exactly same date
        assertTrue(firstPredicate.test(new ExpenseBuilder().withTime("2019-08-11").build()));
    }

    @Test
    public void test_dateIsNotEquals_returnsFalse() {
        LocalDate firstPredicateDate = TRAVEL_EXPENSE.getDate().getDate();
        EntryContainsDayPredicate firstPredicate =
                new EntryContainsDayPredicate(firstPredicateDate.getDayOfMonth(), firstPredicateDate.getMonthValue(),
                        firstPredicateDate.getYear());

        //diff day, same month and year
        assertFalse(firstPredicate.test(new ExpenseBuilder().withTime("2018-08-12").build()));

        //diff month, same day and year
        assertFalse(firstPredicate.test(new ExpenseBuilder().withTime("2019-09-11").build()));

        //diff Year, same month and day
        assertFalse(firstPredicate.test(new ExpenseBuilder().withTime("2018-08-11").build()));
    }

}
