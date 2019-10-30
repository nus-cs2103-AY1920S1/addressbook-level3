package budgetbuddy.model.loan.predicates;

import java.util.function.Predicate;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.loan.Loan;

/**
 * A predicate to check equality between a given description and the description of a given loan.
 */
public class DescriptionMatchPredicate implements Predicate<Loan> {
    private final Description description;

    public DescriptionMatchPredicate(Description description) {
        this.description = description;
    }

    @Override
    public boolean test(Loan loan) {
        return loan.getDescription().toString().equalsIgnoreCase(description.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DescriptionMatchPredicate
                && description.equals(((DescriptionMatchPredicate) other).description));
    }
}
