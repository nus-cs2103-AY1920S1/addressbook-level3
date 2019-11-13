package budgetbuddy.model.loan.predicates;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.function.Predicate;

import budgetbuddy.model.loan.Loan;

/**
 * A predicate to check equality between a given date and the date of a given loan.
 */
public class DateMatchPredicate implements Predicate<Loan> {
    private final LocalDate date;

    public DateMatchPredicate(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public boolean test(Loan loan) {
        return loan.getDate().equals(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateMatchPredicate
                && date.equals(((DateMatchPredicate) other).date));
    }
}
