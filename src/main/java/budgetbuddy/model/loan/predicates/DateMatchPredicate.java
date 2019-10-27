package budgetbuddy.model.loan.predicates;

import java.util.Date;
import java.util.function.Predicate;

import budgetbuddy.model.loan.Loan;

public class DateMatchPredicate implements Predicate<Loan> {
    private final Date date;

    public DateMatchPredicate(Date date) {
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
