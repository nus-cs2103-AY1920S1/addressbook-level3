package budgetbuddy.model.loan.predicates;

import java.util.function.Predicate;

import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.person.Person;

/**
 * A predicate to check equality between a given person and the person of a given loan.
 */
public class PersonMatchPredicate implements Predicate<Loan> {
    private final Person person;

    public PersonMatchPredicate(Person person) {
        this.person = person;
    }

    @Override
    public boolean test(Loan loan) {
        return loan.getPerson().getName().toString().equalsIgnoreCase(person.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PersonMatchPredicate
                && person.equals(((PersonMatchPredicate) other).person));
    }
}
