package budgetbuddy.model.loan.predicates;

import java.util.function.Predicate;

import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.transaction.Amount;

public class AmountMatchPredicate implements Predicate<Loan> {
    private final Amount amount;

    public AmountMatchPredicate(Amount amount) {
        this.amount = amount;
    }

    @Override
    public boolean test(Loan loan) {
        return loan.getAmount().equals(amount);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AmountMatchPredicate
                && amount.equals(((AmountMatchPredicate) other).amount));
    }
}
