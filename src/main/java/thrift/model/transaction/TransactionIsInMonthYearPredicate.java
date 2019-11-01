package thrift.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction}'s {@code monthYear} matches the given monthYear.
 */
public class TransactionIsInMonthYearPredicate implements Predicate<Transaction> {
    private final Calendar monthYear;

    public TransactionIsInMonthYearPredicate(Calendar monthYear) {
        this.monthYear = monthYear;
    }

    @Override
    public boolean test(Transaction transaction) {
        requireNonNull(transaction);
        assert monthYear != null;

        Calendar transactionMonthYear = Calendar.getInstance();
        transactionMonthYear.setTime(transaction.getDate().getDate());

        return monthYear.get(Calendar.MONTH) == transactionMonthYear.get(Calendar.MONTH)
                && monthYear.get(Calendar.YEAR) == transactionMonthYear.get(Calendar.YEAR) ? true : false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionIsInMonthYearPredicate) // instanceof handles nulls
                && (((TransactionIsInMonthYearPredicate) other).monthYear.get(Calendar.MONTH)
                == monthYear.get(Calendar.MONTH)
                && ((TransactionIsInMonthYearPredicate) other).monthYear.get(Calendar.YEAR)
                == monthYear.get(Calendar.YEAR));
    }
}
