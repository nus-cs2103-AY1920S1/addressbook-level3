package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.model.transaction.Budget.BUDGET_DATE_FORMAT;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import thrift.testutil.ExpenseBuilder;

public class TransactionIsInMonthYearPredicateTest {

    @Test
    public void equals() throws ParseException {
        String monthYear1 = "10/2019";
        String monthYear2 = "11/2019";
        Calendar c1 = Calendar.getInstance();
        c1.setTime(BUDGET_DATE_FORMAT.parse(monthYear1));
        Calendar c2 = Calendar.getInstance();
        c2.setTime(BUDGET_DATE_FORMAT.parse(monthYear2));
        TransactionIsInMonthYearPredicate firstPredicate = new TransactionIsInMonthYearPredicate(c1);
        TransactionIsInMonthYearPredicate secondPredicate = new TransactionIsInMonthYearPredicate(c2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TransactionIsInMonthYearPredicate firstPredicateCopy = new TransactionIsInMonthYearPredicate(c1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different transaction -> return false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_transactionIsInMonthYear_returnsTrue() throws ParseException {
        String monthYear = "10/2019";
        String transactionDate = "01/10/2019";
        Calendar c1 = Calendar.getInstance();
        c1.setTime(BUDGET_DATE_FORMAT.parse(monthYear));
        TransactionIsInMonthYearPredicate predicate = new TransactionIsInMonthYearPredicate(c1);

        Expense expense = new ExpenseBuilder().withDate(transactionDate).build();

        assertTrue(predicate.test(expense));
    }

    @Test
    public void test_transactionIsNotInMonthYear_returnsFalse() throws ParseException {
        String monthYear = "10/2019";
        String transactionDate = "01/11/2019";
        Calendar c1 = Calendar.getInstance();
        c1.setTime(BUDGET_DATE_FORMAT.parse(monthYear));
        TransactionIsInMonthYearPredicate predicate = new TransactionIsInMonthYearPredicate(c1);

        Expense expense = new ExpenseBuilder().withDate(transactionDate).build();

        assertFalse(predicate.test(expense));
    }
}
